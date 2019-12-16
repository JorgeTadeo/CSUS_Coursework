/*
	RONGGUANG OU 
	ASSIGNMENT 2
	2/15/2019
	Version 1.0
	PURPOSE : Mixes a 64Byte Input 
*/

#include <stdio.h>
#include <stdint.h>
#include <string.h>
#include <time.h>


/*
	Input a-d is bytes to mix 
*/
void mix(uint32_t t[], uint32_t a , uint32_t b , uint32_t c , uint32_t d ){

  t[a] = t[a] + t[b];
  t[d] = ( (t[a] ^ t[d]) >> 8 | (t[a] ^ t[d]) << 24 ); // 32-18

  t[c] = t[c] + t[d];
  t[b] = ( (t[b] ^ t[c]) >> 11 | (t[b] ^ t[c]) << 21); //32-11
  
  t[a] = t[a] + t[b];
  t[d] = ( (t[a] ^ t[d]) >> 16 | (t[a] ^ t[d]) << 16); //32-16

  t[c] = t[c] + t[d];
  t[b] = ( (t[b] ^ t[c]) >> 31 | (t[b] ^ t[c]) << 1); //32-31

}

//Takes a uint32_t array as input and perform mix and returns the input 
uint32_t* round2(uint32_t t[]){
    mix(t,0,4,8,12);
    mix(t,1,5,9,13);
    mix(t,2,6,10,14);
    mix(t,3,7,11,15);
    mix(t,0,5,10,15);
    mix(t,1,6,11,12);
    mix(t,2,7,8,13);
    mix(t,3,4,9,14);

    return t;
}


void P152(unsigned char dst[64] , unsigned char src[64]){
  //Input is 64 Byte char array.
  //Each 4 byte is a uint32
  uint32_t c[16];
  int i;
  //Initial constants 
  for(i = 0 ; i < 16 ; i++){
    c[i] = (uint32_t)i;
  }

//Mix up the constants 
  round2(round2(c));
  
   uint32_t x[16];                                      
  for(i = 0 ; i < 16 ; i++){ //Read from src LE 4byte chunks , Each element in x is reading 4 element in src and combined.
    x[i] = (uint32_t)src[4*i] << 0 | (uint32_t)src[4*i+1] << 8 | (uint32_t)src[4*i+2] << 16 | (uint32_t)src[4*i+3] << 24;

  }


  for(i = 0 ; i < 16 ; i++){ //Mixing..
    x[i] = x[i] ^ c[i];
  }
  for(i = 0 ; i< 6; i++){ //More Mixing...
    round2(x);
  }
  for(i = 0 ; i < 16 ; i++){ //Dump to dst
    //Extract byte from uint32 + assign back to dst
    
        dst[4*i] = (unsigned char)(x[i] >> 0);
    dst[4*i + 1] = (unsigned char)(x[i] >> 8);
    dst[4*i + 2] = (unsigned char)(x[i] >> 16);
    dst[4*i + 3] = (unsigned char)(x[i] >> 24);
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
