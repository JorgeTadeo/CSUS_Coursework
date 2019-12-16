/*
    Author: RONGGUANG OU
    2/12/2019 
*/
#include <stdio.h>
#include <stdint.h>

uint32_t sum(unsigned char data[] , unsigned int data_bytes){
  unsigned int numTimes = data_bytes/4; //Check how many multiples of 4 bytes
  uint32_t currentSum = 0;
  if(numTimes > 0){
    uint32_t x , index = 0;
    for(unsigned int i = 0 ; i < (numTimes); i++){
     //Shift the 4 numbers and combine
      x  = ((uint32_t)data[index] << 24) | ((uint32_t)data[index+1] << 16) | ((uint32_t)data[index+2] << 8) | ((uint32_t)data[index+3] << 0);
      //Add to current sum
      currentSum += x;
      //next loop starts at next multiple of 4 so increment index by 4
      index += 4;
    }
  }
  return currentSum;
}


int main() {
    unsigned char buf[12] = {0,1,2,3,4,5,6,7,8,9,10,11};
    if ((sum(buf,0) != 0) ||
        (sum(buf,3) != 0) ||
        (sum(buf,4) != 0x0010203) ||
        (sum(buf,7) != 0x0010203) ||
        (sum(buf,8) != 0x406080A) ||
        (sum(buf,11) != 0x406080A) ||
        (sum(buf,12) != 0xC0F1215) ||
        (sum(buf,15) != 0xC0F1215)) {
        printf("No pass\n");
    } else {
        printf("Pass\n");
    }
    return 0;
}