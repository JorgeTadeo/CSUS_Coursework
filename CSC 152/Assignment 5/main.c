#include <memory.h>
#include <stdint.h>
#include <stdlib.h>
#include "library.h"


uint32_t poly31(unsigned char m[], unsigned mbytes, uint32_t k);
void P152(unsigned char dst[64], unsigned char src[64]);

static void xor(unsigned char *dst, unsigned char *a, unsigned char *b, unsigned num_bytes)
{
	for (unsigned i = 0; i < num_bytes; ++i)
	{
		dst[i] = a[i] ^ b[i];
	}
}

//K can be any length up to 63 bytes, X is 64 bytes. (i.e. K is the key, and X is the plaintext)
//Y is the destination for the ciphertext.
static void P152_BC(int kbytes, unsigned char k[kbytes], unsigned char x[64], unsigned char y[64])
{
	//paddedK = K || 10*	//pad to make K 512 bits (64 bytes) (|| is concatenate)
	unsigned char paddedK[64] = { 0 };
	memcpy(paddedK, k, (unsigned long)kbytes);
	paddedK[kbytes] = 0x80;

	//return P152(X xor paddedK) xor paddedK
	unsigned char buff[64];
	xor(buff, x, paddedK, 64);
	P152(buff, buff);
	xor(y, buff, paddedK, 64);
}

static void incrementBigEndianCounter(unsigned char counter[52])
{
	//Basic idea: Add 1 to the end of the counter. If it becomes 0, that means it overflowed. Which means the next byte to the left needs to be incremented. If that byte becomes 0, then that means it overflowed...ad infinitum.
	int i = 51;
	int overflow = 0; //boolean value, 0 = false, 1 = true;
	do
	{
		overflow = 0;
		++counter[i];

		if (counter[i] == 0)
		{
			i--;
			overflow = 1;
		}
	} while (overflow == 1 && i >= 0);
}

static void P152_BC_CTR(int kbytes, unsigned char k[kbytes], unsigned char* plaintext, unsigned char* ciphertext, int plaintext_bytes, unsigned char nonce[12])
{
	//Follows the AES-CTR GCM construction...except:
	//Input is NONCE || CTR into P152 BC, XOR output with PT. Return that PT. Do this as many times as necessary.

	//The first 12 bytes are the nonce, the next 48 bytes are the counter.
	unsigned char combinedNonceCounter[64] = { 0 };
	memcpy(combinedNonceCounter, nonce, 12);
	combinedNonceCounter[63] = 0x02; //Starting counter at 2.

	unsigned char* p_plaintext = plaintext;
	unsigned char* p_ciphertext = ciphertext;
	//Loop through all complete blocks of the plaintext.
	int i;
	for (i = 0; i + 64 < plaintext_bytes; i += 64, p_plaintext += 64, p_ciphertext += 64)
	{
		//P152(p_ciphertext, combinedNonceCounter);
		P152_BC(kbytes, k, combinedNonceCounter, p_ciphertext);
		xor(p_ciphertext, p_ciphertext, p_plaintext, 64);
		incrementBigEndianCounter(combinedNonceCounter + 12);
	}
	//Then, if there's an incomplete 64-byte chunk of plaintext, handle it at the end, and XOR only the number of plaintext bytes remaining into the output of P152 and only use the appropriate number of bytes of that XOR as the ciphertext.
	if (i < plaintext_bytes)
	{
		unsigned char buf[64];
		P152_BC(kbytes, k, combinedNonceCounter, buf);
		xor(p_ciphertext, buf, p_plaintext, (unsigned int)(plaintext_bytes - i));
	}
}

void pcm_encrypt(int kbytes, unsigned char k[kbytes], // key, up to 63 bytes allowed, 16 or 32 recommended
	unsigned char n[12], // nonce, must not repeat with same key
	int abytes, unsigned char a[abytes], // associated data - is authenticated too
	int pbytes, unsigned char p[pbytes], // plaintext to be encrypted
	unsigned char c[pbytes], // destination for ciphertext
	unsigned char t[16]) // destination for authentication tag
{
	//1) kpoly = P152_BC(K, num2strBE(0, 512))
	unsigned char zeros[64] = { 0 };
	unsigned char Kpoly[64];
	P152_BC(kbytes, k, zeros, Kpoly);
	
	//2) WCfinal = p152_BC(K, N || num2strBE(1, 416))
	unsigned char concatenated_nonce_bigendian_one[64] = { 0 };
	concatenated_nonce_bigendian_one[63] = 0x01; //The big endian one is actually 416 bits, or 52 bytes. But we're concatenanting, so just make the first 12 bytes the nonce, and the rest is 52 bytes for 1 in big endian. (CTR starting at 1)
	memcpy(concatenated_nonce_bigendian_one, n, 12);
	unsigned char WCfinal[64];
	P152_BC(kbytes, k, concatenated_nonce_bigendian_one, WCfinal);

	//3) C = P152_BC_CTR(K, N, P)			//NOTE: Begin ctr with N || num2strBE(2, 416)
	//NOTE: the C here is the ciphertext.
	P152_BC_CTR(kbytes, k, p, c, pbytes, n);

	//4) X = A || C || 0* || num2strLE(bitlen(A), 32) || num2strLE(bitlen(P), 32)
	//NOTE: num2strLE(..., 32) means store the bitlen as a uint32_t
	unsigned x_size_bytes = (unsigned)(abytes + pbytes + 4 + 4);
	unsigned num_incomplete_bytes = 0; //Number of bytes needed to bring a and c up to a length of a multiple of 4 bytes.
	if (((abytes + pbytes) % 4) != 0)
	{
		num_incomplete_bytes = 4 - ((abytes + pbytes) % 4);
		x_size_bytes += num_incomplete_bytes; //0* the length to a multiple of 4 bytes.
	}
	unsigned char* x = malloc(x_size_bytes);
	for (unsigned i = 0; i < x_size_bytes; ++i)
	{
		x[i] = 0; //Initialize everything to zero...(this is necessary for the 0* pad)
	}
	{
		//Copy everything into x.
		unsigned char* p_x = x;
		memcpy(p_x, a, (unsigned long)abytes);
		p_x += abytes;
		memcpy(p_x, c, (unsigned long)pbytes);
		p_x += (unsigned int)(pbytes) + num_incomplete_bytes;
		(*(uint32_t*)(p_x)) = (uint32_t)abytes * 8; //Store the bitlength of the associated data, A;
		p_x += 4;
		(*(uint32_t*)(p_x)) = (uint32_t)pbytes * 8; //Store the bitlength of the plaintext, P;
	}

	//5)
	//			for i in { 0, 1, 2, 3 }
	//			Ki = str2numLE(Kpoly[i*32...i*32+31)]) >> 2 //This means take the first 32 bits of Kpoly, rightshift them 2 bits, and then use that as your key for Poly31.
	//			Yi = num2strLE(Poly31(X, Ki), 32)
	uint32_t* p_Kpoly = (uint32_t*)Kpoly;
	for (unsigned i = 0; i < 4; ++i)
	{
		uint32_t Ki = p_Kpoly[i] >> 2;
		//y[i] = poly31(x, x_size_bytes, Ki); //Y can actually just be stored directly into T.

		//6.a) First part of step 6: T = (Y0 || Y1 || Y2 || Y3)
		((uint32_t*)t)[i] = poly31(x, x_size_bytes, Ki);
	}

	//6.b) Second part of step 6: T = (Y0 || Y1 || Y2 || Y3) xor WCfinal[0...127] <-- the XOR part.
	xor(t, t, WCfinal, 16); //WCfinal is 64 bytes, but only using the first 127 bits (first 16 bytes).

	//7) Finally, return (C, T)

	free(x); //And don't forget to clean up after yourself.
}





void pcm_encrypt(int kbytes, unsigned char k[kbytes],  // key, up to 63 bytes allowed, 16 or 32 recommended
                 unsigned char n[12],                  // nonce, must not repeat with same key
                 int abytes, unsigned char a[abytes],  // associated data - is authenticated too 
                 int pbytes, unsigned char p[pbytes],  // plaintext to be encrypted
                 unsigned char c[pbytes],              // destination for ciphertext
                 unsigned char t[16]);                 // destination for authentication tag

int main () {
    unsigned char tag[16];
    unsigned char *in = malloc(1000000);
    unsigned char *out = malloc(1000000);
    for (int i=0; i<1000000; i++)
        in[i] = i * (i + 1) % 251;

    int len = 0;
    pcm_encrypt(16, in, in, len, in, len, in, out, tag);
    printf("%d\n",!memcmp(tag,"\xD6\xDA\x2A\xB6\xB1\x45\x62\x5E\x08\xCD\xA5\xF9\xB6\x65\x21\x8D",16));

    len = 1;
    pcm_encrypt(16, in, in, len, in, len, in, out, tag);
    pcm_encrypt(32, in, in, len, out, 0, out, tag, tag);
    printf("%d\n",!memcmp(tag,"\x6F\xC8\xBD\x97\x00\xEB\x3E\x17\x6D\x4E\xA1\x98\x28\x49\xCA\x7D",16));

    len = 64;
    pcm_encrypt(16, in, in, len, in, len, in, out, tag);
    pcm_encrypt(32, in, in, len, out, 0, out, tag, tag);
    printf("%d\n",!memcmp(tag,"\x74\x73\x62\x88\x74\xD5\xE6\x35\xF8\x9E\xC9\xBA\xD3\x66\x0D\x52",16));

    len = 65;
    pcm_encrypt(16, in, in, len, in, len, in, out, tag);
    pcm_encrypt(32, in, in, len, out, 0, out, tag, tag);
    printf("%d\n",!memcmp(tag,"\x7E\x0A\x9F\xC5\xF1\x56\x96\x5A\xD8\xF5\xFA\xBF\x6F\x58\x8E\x3D",16));

    len = 1000000;
    pcm_encrypt(16, in, in, len, in, len, in, out, tag);
    pcm_encrypt(32, in, in, len, out, 0, out, tag, tag);
    printf("%d\n",!memcmp(tag,"\xBE\x99\x79\x8B\x2A\xEC\x03\x1B\x6D\x1F\x67\x9A\xFB\x29\x63\x59",16));
}
