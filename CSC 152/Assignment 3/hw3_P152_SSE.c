/*
	RONGGUANG OU 
	ASSIGNMENT 3
	3/1/2019
	Version 2.0 , Internal changed to SSE
	PURPOSE : Mixes a 64Byte Input 
*/

#include <stdio.h>
#include <stdint.h>
#include <string.h>
#include <emmintrin.h>
#include <time.h>

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

//REMOVE THIS WHEN DONE 
void xor(unsigned char *dst , unsigned char *src , int num_bytes){
  for(int i = 0 ; i < num_bytes; i++)
    dst[i] ^= src[i];
}

void print(unsigned char x[], int mbytes , char* str){
    for(int i = 0 ; i < mbytes ; i++){
        printf("%s[%i]=%02X\n",str,i,x[i]);
    }
}


 int main() {
   
   clock_t begin = clock();
    unsigned char buf1[64] = {0};
    unsigned char buf2[64] = {0};
    unsigned char res[4] = {0x46, 0x6D, 0x1D, 0xEE};
    for (unsigned i=0; i<10; i++) { 
        P152(buf2,buf1);
        P152(buf1,buf2);
    }
    if (memcmp(buf1, res, 4) == 0)
        printf("pass\n");
    else
        printf("not pass\n");
  clock_t end = clock();
  double time_spent  = (double)(end-begin) / CLOCKS_PER_SEC;
  printf("Time spent :%fms",time_spent*1000);


   return 0; 
   

    
}
