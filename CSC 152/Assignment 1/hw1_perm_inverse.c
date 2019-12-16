/*
    RONGGUANG OU
    2/12/2019 
*/
#include <stdio.h>
#include <stdint.h>

uint32_t perm_inverse(uint32_t x){
	uint16_t hi = (uint16_t) (x >> 16); //Extract high 16 bits from x
	uint16_t lo = (uint16_t) (x & 0xFFFF); //Extract low 16 bits from x
	lo = (lo ^ (uint16_t)(hi * hi)); //low = low XOR f(high)
	hi = hi ^ (uint16_t)(lo * lo); // high = high XOR f(low)
	x = ((uint32_t)hi << 16) | (uint32_t)lo; //Combine results to form 32bit number
	return x;
}


int main() {
    if ((0x80000000 != perm_inverse(0x80000000)) ||
        (0x00000001 != perm_inverse(0x00010000)) ||
        (0x00800800 != perm_inverse(0x00804800)) ||
        (0x12345678 != perm_inverse(0xCA7412E8))) {
        printf("No pass\n");
    } else {
        printf("Pass\n");
    }
    return 0;
}