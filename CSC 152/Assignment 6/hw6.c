/*
 AUTHOR : RONGGUANG OU
 CSC 152 HW 6
*/

#include <stdio.h>
#include <stdint.h>
#include <stdlib.h>
#include <stdbool.h>



long long int inverse(long long int x , long long int  p){
    long long int res = x;
    for(long long int i = 1 ; i < p ; i++){
        if((i * x)% p == 1){
            //   cout << i << endl;
            res = i;
            break;
        }
    }
    return res;
}

long long int getInrange(long long int x , long long int p){
    long long int res = x;
    while(res < 0)
        res+=p;
    //    cout << "res is "  << res << endl;
    return res;
}

bool isValid(int x , int y , int a , int b , int p ){
    long long int w = (x*x);
    w *= x;
    int64_t v = (a*x);
    int64_t left = (y *y );
    int64_t right = w + v + b;
    while(right < 0){
        right += p;
    }
    return left % p == right % p;
}

void print(long long int x , long long int y){
    printf("(%llu,%llu)\n",x,y);
}

void add(int a , int b , int p , int x1 , int y1 , int x2 , int y2 ){

    long long int Xres;
    long long int Yres;
    long long int s;
    
    if((-1*y1 + p) == y2 && x1 == x2){
        printf("0\n");
        return;
    }
    if(!isValid(x1, y1, a, b,p) || !isValid(x2, y2, a, b,p)){
      //  cout << "Error" << endl;
        printf("Error\n");
        return;
    }
    
    // Point Doubling
    if(x2 == x1  && y1 == y2){
        s = (3 * (x1 * x1) + a)*(inverse(2*y1,p));
        s = getInrange(s, p) % p;
        Xres = (s*s) - (2*x1);
        Xres = getInrange(Xres, p) % p;
        Yres = s*(x1-Xres) - y1;
        Yres = getInrange(Yres, p) % p;
    }else{
        long long int k = getInrange(x2-x1,p);
        s = (y2-y1) * inverse(k,p);
        s = getInrange(s,p) % p;
        Xres = (s*s) - (x1+x2);
        Xres = getInrange(Xres, p) % p;
        Yres = s*(x1 - Xres) - y1;
        Yres = getInrange(Yres, p) % p;
    }
    print(Xres,Yres);
    return;
    
}


//int argc , const char * argv[]
int main() {
    //Use sscanf if use argv
    int a,b,p,x1,x2,y1,y2;
    scanf( "%d", &a);
    scanf( "%d", &b);
    scanf( "%d", &p);
    scanf( "%d", &x1);
    scanf( "%d", &y1);
    scanf( "%d", &x2);
    scanf( "%d", &y2);
    
    
    add(a,b,p,x1,y1,x2,y2);
    

}



