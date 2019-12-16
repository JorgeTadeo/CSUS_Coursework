/*
	RONGGUANG OU 
	ASSIGNMENT 4 Part 1
	3/25/2019
	Version 3.0 , Added Hashing. 
	PURPOSE : Spong-Based crytographic hash using P152 

  Input can be infinite long mbytes
  Goes thru P152_hash and can squeeez as many
  bytes output as needed.
*/

#include <stdio.h>
#include <stdint.h>
#include <string.h>
#include <emmintrin.h>


void psse_int(__m128i x) {     // Print an SSE register as if it holds four ints
    int *p = (int *)&x;
    for (int i=0; i<4; i++) {
        printf("%08X ", p[3-i]);
    }
    printf("\n");
}
/*
	t[0] = t[0] + t[1]
  t[3] = (t[0] ^ t[3]) >> 8 | (t[0] ^ t[3] << 24) 

  t[2] = t[2] + t[3]
  t[1] = (t[1] ^ t[2]) >> 11 | (t[1] ^ t[2] << 21) 

	t[0] = t[0] + t[1]
  t[3] = (t[0] ^ t[3]) >> 16 | (t[0] ^ t[3] << 16) 

  t[2] = t[2] + t[3]
  t[1] = (t[1] ^ t[2]) >> 31 | (t[1] ^ t[2] << 1) 

  
*/
 void mix(__m128i t[]){

  t[0] = _mm_add_epi32( t[0] ,  t[1]);
  t[3] = _mm_or_si128(_mm_srli_epi32(_mm_xor_si128(t[0],t[3]),0x08),
                      _mm_slli_epi32(_mm_xor_si128(t[0],t[3]),0x18));

  t[2] = _mm_add_epi32( t[2] ,  t[3]);
  t[1] = _mm_or_si128(_mm_srli_epi32(_mm_xor_si128(t[1],t[2]),0x0B),
                      _mm_slli_epi32(_mm_xor_si128(t[1],t[2]),0x15));

  t[0] = _mm_add_epi32( t[0] ,  t[1]);
  t[3] = _mm_or_si128(_mm_srli_epi32(_mm_xor_si128(t[0],t[3]),0x10),
                      _mm_slli_epi32(_mm_xor_si128(t[0],t[3]),0x10));

  t[2] = _mm_add_epi32( t[2] ,  t[3]);
  t[1] = _mm_or_si128(_mm_srli_epi32(_mm_xor_si128(t[1],t[2]),0x1F),
                      _mm_slli_epi32(_mm_xor_si128(t[1],t[2]),0x01));
}





/*
  Quick way to apply shuffle is : 
    Start at end of desired , MSB of OLD is 00
*/
__m128i* round2(__m128i t[]){
/*
  a = [ 0  1  2  3]
  b = [ 4  5  6  7]
  c = [ 8  9 10 11]
  d = [12 13 14 15]
*/
    mix(t);
    //When return .. apply shuffle
    t[1] = _mm_shuffle_epi32(_mm_loadu_si128((__m128i *) t+1),0x39);
    t[2] = _mm_shuffle_epi32(_mm_loadu_si128((__m128i *) t+2),0x4E);
    t[3] = _mm_shuffle_epi32(_mm_loadu_si128((__m128i *) t+3),0x93);
/*
  a = [ 0  1  2  3]
  b = [ 5  6  7  4]
  c = [10 11  8  9]
  d = [15 12 13 14]
*/
    mix(t);
    //When return shufle back 
    t[1] = _mm_shuffle_epi32(_mm_loadu_si128((__m128i *) t+1),0x93);
    t[2] = _mm_shuffle_epi32(_mm_loadu_si128((__m128i *) t+2),0x4E);
    t[3] = _mm_shuffle_epi32(_mm_loadu_si128((__m128i *) t+3),0x39);
/*
  a = [ 0  1  2  3]
  b = [ 4  5  6  7]
  c = [ 8  9 10 11]
  d = [12 13 14 15]
*/
    return t;
}

 void P152(unsigned char dst[64] , unsigned char src[64]){
  int i;
  uint32_t c[16];
  //Initial constants 
  for(i = 0 ; i < 16 ; i++){ 
    c[i] = (uint32_t)i;
  }
  //Get uint32_t c[] to SSE regs
  __m128i cc[4] = {_mm_loadu_si128 ((__m128i *)c+0),
                   _mm_loadu_si128 ((__m128i *)c+1),
                   _mm_loadu_si128 ((__m128i *)c+2),
                   _mm_loadu_si128 ((__m128i *)c+3)};

  //Mix up the constants 
  round2(round2(cc));

  uint32_t x[16];                                      
  for(i = 0 ; i < 16 ; i++){ //Read from src LE 4byte chunks , Each element in x is reading 4 element in src and combined.
    x[i] = (uint32_t)src[4*i] << 0 | (uint32_t)src[4*i+1] << 8 | (uint32_t)src[4*i+2] << 16 | (uint32_t)src[4*i+3] << 24;

  }
//Get uint32_t x[] to SSE reg 
  __m128i xx[4] = {_mm_loadu_si128 ((__m128i *)x+0),
                   _mm_loadu_si128 ((__m128i *)x+1),
                   _mm_loadu_si128 ((__m128i *)x+2),
                   _mm_loadu_si128 ((__m128i *)x+3)};


  for(i = 0 ; i < 4 ; i++){ //More Mixing
    xx[i] = _mm_xor_si128(xx[i],cc[i]);
  }
  for(i = 0 ; i< 6; i++){ //More Mixing...
    round2(xx);
  }
  //Write back to uint32_t x[]
  _mm_storeu_si128((__m128i *)x+0 , xx[0]);
  _mm_storeu_si128((__m128i *)x+1 , xx[1]);
  _mm_storeu_si128((__m128i *)x+2 , xx[2]);
  _mm_storeu_si128((__m128i *)x+3 , xx[3]);


  for(i = 0 ; i < 16 ; i++){ //Dump to dst
    //Extract byte from uint32 + assign back to dst   
    dst[4*i] = (unsigned char)(x[i] >> 0);
    dst[4*i + 1] = (unsigned char)(x[i] >> 8);
    dst[4*i + 2] = (unsigned char)(x[i] >> 16);
    dst[4*i + 3] = (unsigned char)(x[i] >> 24);
  }
}

void xor(unsigned char *dst , unsigned char *src , int num_bytes){
  for(int i = 0 ; i < num_bytes; i++)
    dst[i] ^= src[i];
}



void P152_hash(unsigned char m[] , unsigned mbytes , unsigned char res[24]){
  //SETUPS
  int p = 0;
  unsigned char block[64] = {0}; //H0 
  unsigned char input[16] = {0};
  //Process 16byte chunks 
  while((p + 16) <= mbytes){
      //populate message chunk 
      for(int i = 0 ; i < 16 ; i++)
        input[i] = m[p+i];
      //XOR(msg chunk , rate)
      xor(block,input,16);
      P152(block,block);
      p = p + 16;
  }
  //Process remain bytes 
  for(int i = 0 ; i < 16 ; i++){
    if(p < mbytes){
      input[i] = m[p++];
    }
    else if(p == mbytes){
      input[i] = 0x80;
      p++;
    }else
      input[i] = 0x00;
  }
  xor(block,input,16);
  P152(block, block);
  //Squeeze
  for(int i = 0 ; i < 16 ; i++){
    res[i] = block[i];
  }
  P152(block,block);
  for(int i = 0 ; i < 8 ; i++){
    res[16+i] = block[i];
  }
}

/* 1 means pass , 0 means fail */
int main() {
    unsigned char *in = malloc(1000000);
    unsigned char out[24];
    for (int i=0; i<1000000; i++)
        in[i] = (i * i) % 251;
 
    P152_hash(in, 0, out);
    printf("%d\n", memcmp(out,"\xE8\xF6\x53\x73\x7C\x23\xB6\x08\x18\x32\x47\xE3\x22\x26\xCB\xBE\x5E\x52\x30\xBE\x8A\x8D\x7D\x73",24)==0);
    P152_hash(in, 1, out);
    printf("%d\n", memcmp(out,"\xA1\x85\x9B\x89\xE9\x3A\x08\xD7\x1E\xBB\xC0\x28\x71\x27\x80\x52\x98\x5C\x30\x13\x3F\xB9\xBA\x4D",24)==0);
    P152_hash(in, 15, out);
    printf("%d\n", memcmp(out,"\x09\xC3\x74\x76\x04\x3D\x69\x28\x18\x20\x1D\x56\xE9\xAF\xA9\xE1\x9B\xBE\x4C\x28\xA6\xFF\x57\xC4",24)==0);
    P152_hash(in, 16, out);
    printf("%d\n", memcmp(out,"\x42\x6A\xE2\x00\x03\x03\xFA\x30\xD6\xEE\xF0\x6F\x13\x35\xF6\x4E\xA1\xB0\x4F\x1B\x5D\x88\x91\x80",24)==0);
    P152_hash(in, 17, out);
    printf("%d\n", memcmp(out,"\xB9\xAC\x6A\x12\xD9\xEB\xC8\x23\xDC\xB9\x0F\xB2\xDC\xA2\xBA\x0D\xB6\x6F\xDE\xB5\xEA\x9B\x99\xD7",24)==0);
    P152_hash(in, 63, out);
    printf("%d\n", memcmp(out,"\xAE\x63\xE2\xC5\xF2\x22\x94\x48\x41\x87\xC4\x44\xD2\xF3\x5C\xAF\xFB\x62\x65\x46\xBE\x65\x20\x46",24)==0);
    P152_hash(in, 64, out);
    printf("%d\n", memcmp(out,"\x07\xEA\x59\xF3\xB0\x54\x35\xAC\x66\xDE\xCF\xE0\x09\xCE\xFF\x5C\xC4\xBF\x7D\x4B\x05\xE8\x80\xA7",24)==0);
    P152_hash(in, 65, out);
    printf("%d\n", memcmp(out,"\x4E\x9F\xC7\xB4\x5B\xEB\xC1\x7D\xBF\xA9\x07\x35\xD8\x53\xF2\x10\xCA\x72\x6B\xE5\x36\xCA\x9B\xD0",24)==0);
    P152_hash(in, 1000000, out);
    printf("%d\n", memcmp(out,"\x59\x5B\x0B\xA1\x56\x67\x97\x7B\x07\xA2\xF6\x9E\x89\xA1\xBF\xF1\x02\x4E\xC0\xA0\x20\x57\xAF\xF6",24)==0);   
}
