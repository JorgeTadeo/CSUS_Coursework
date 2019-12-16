/*
	RONGGUANG OU 
	ASSIGNMENT 4 Part 2
	3/27/2019
	PURPOSE : Implement Horner's Rule 
*/

#include <stdio.h>
#include <stdint.h>
#include <string.h>
#include <math.h>
#include <inttypes.h>

 
 
uint32_t poly31(unsigned char m[], unsigned mbytes, uint32_t k){
 
    unsigned int p = 0;
    uint64_t res = k;
    uint64_t  x_i = 0;
    
   //Process 2 byte set 
    while(p + 2 <= mbytes){
      x_i = (uint64_t)m[p] << 0 | (uint64_t)m[p+1] << 8;
      // printf("%08X\n",x_i);
        res += x_i;
      while(res >= 0x7FFFFFFF)
        res = (res >> 31) + (0x7FFFFFFF & res);
      res *= k;
      while(res >= 0x7FFFFFFF)
        res = (res >> 31) + (0x7FFFFFFF & res);
    
      p = p + 2;
    }
   //Process remain bytes and pad 
   if(p + 1 == mbytes) //Pad 1 byte 
      x_i = (uint64_t)m[p] | (uint64_t)0x80 << 8;
   else // Pad 2 byte 
      x_i = (uint64_t)0x80 << 0 | (uint64_t)0x00 << 8;
  
 
    res += x_i;
    while(res >= 0x7FFFFFFF)
      res = (res >> 31) + (0x7FFFFFFF & res);
    res *= k;
    while(res >= 0x7FFFFFFF)
      res = (res >> 31) + (0x7FFFFFFF & res);
    

    return res ;
}
      //res = high*b + low 
      //2^31 -1  is 0x7FFF FFFF
    //  printf("my_number: %#016" PRIx64 "\n", res);
int main () 
{


    uint32_t k = 0x07654321u;
    unsigned char m[] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16};
    printf("%d\n", poly31(m, 0, k)==0xab33e3c);
    printf("%d\n", poly31(m, 3, k)==0x27058bb4);
    printf("%d\n", poly31(m, 4, k)==0x2a0a55e5);
    printf("%d\n", poly31(m, 15, k)==0x5564d344);
    printf("%d\n", poly31(m, 16, k)==0x16af9d21);


}

