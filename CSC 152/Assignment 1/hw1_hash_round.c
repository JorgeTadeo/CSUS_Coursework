/*
    RONGGUANG OU
    2/12/2019 
*/
#include <string.h>
#include <stdio.h>
#include <stdint.h>

void hash_round(uint64_t v[]){
  uint64_t v0 = v[0];
  uint64_t v1 = v[1];
  uint64_t v2 = v[2];
  uint64_t v3 = v[3];


  v0 = v0 + v1;
  v1 = (v1 << 13) | (v1 >>(64-13)); //Left rotation
  v1 = v0 ^ v1;
  v0 = (v0 << 32) | (v0 >>(64-32));
  v2 = v2 + v3;
  v3 = (v3 << 16) | (v3 >>(64-16));
  v3 = v2 ^ v3;
  v0 = v0 + v3;
  v3 = (v3 << 21) | (v3 >>(64-21));
  v3 = v3 ^ v0;
  v2 = v2 + v1;
  v1 = (v1 << 17) | (v1 >>(64-17));
  v1 = v2 ^ v1;
  v2 = (v2 << 32) | (v2 >>(64-32));

//v0-v4 is now mixed up according to the picture.

//dump back into v[]
  v[0] = v0;
  v[1] = v1;
  v[2] = v2;
  v[3] = v3;

  return;
}


int main() {
    uint64_t v[4] = { 0x0001020304050607ull, 0X08090A0B0C0D0E0Full,
                      0x0010203040506070ull, 0X8090A0B0C0D0E0F0ull };
    uint64_t r[4] = { 0x30221447E9DBCDFEull, 0x72F34DD758DB64E1ull,
                      0xB2F53677A9EC2E70ull, 0x361E2E7FD7DFCFFEull };
    hash_round(v);
    if (memcmp(v,r,32) != 0) {
        printf("No pass\n");
    } else {
        printf("Pass\n");
    }
    return 0;
}