
/**********************
	Peterson Solution *
***********************/

while(true){
	/* I am ready */ 
	flag[0] = true;
	
	/* Your turn */ 
	turn = 1 
	
	/* Wait while is your turn and you are ready */ 
	while(flag[1] && turn == 1);
	
	//Critical Section
		
	/* I'm done */ 
	flag[0] =  false;
	
	//Remainder 
}

/*  HardWare Mutex Solutions (Instructions) 
	meaning statements in the function is done by hardware with no interrupts 
*/ 


/* Returns old original passed parameters and set passed parameter to TRUE */ 
bool TestAndSet(bool* target){
	
	bool old = *target;
	
	*target = true;
	
	return old;
}

/*
	First process comes in TestAndSet(&lock) returns 0 so while(0) will break and enters critical section 
	Second process calls TestAndSet(&lock) returns 1 so while(1) until lock is later becomes false.
	
	Basically the while loop breaks only if lock is false 
	See Lecture 12 , Signaling 
*/
bool lock = false; /* SHARED */ 
while(true){
	while( TestAndSet(&lock) ); /* Do Nothing */
		//Critical Section 
	lock = false; 
		//Remainder Section 
}



/* 
	You have a local key which is true , as long as you have key? you can't go critical section 
	the only way is that you exchange with a lock making your key = 0 , in order to get key = 0
	the lock must be 0 , initially a lock is 0 so the first proces get to exchange key with this lock 
	then lock becomes = 1 and so any other process swaps with their key still is 1 and so they 
	cant get into the critical section 
*/

void Swap(bool *a, bool *b){
	
	bool temp = *a;
	*a = *b;
	*b = temp;
}
bool lock = false; /* SHARED */ 
bool key
while(true){
	key = true;
	while( key == true)
		Swap(&lock,&key); /* First process swaps key becomes 0 , lock becomes 1 */
	
	//Critical Section 
	
	lock = false;
	
	//Remainder Section 
}



/* 
Returns original value of passed parameter "value" 

Set the value to new_value if value == expected 
*/
int compare_and_swap(int *value, int expected, int new_value){
	int temp = *value;
	if(*value == expected)
		*value = new_value;
	return temp;
}
bool lock = 0; /* SHARED */ 
do{
	while(compare_and_swap(&lock,0,1) != 0); /* Do nothing */
	// Critical Section 
	lock = 0;
	//Remainder Section 
while(true);







/* Semaphore */ 
wait(S){
	S--
	if(S<0){
		add to queue 
		block() 
	}
}

signal(S){
	S++ 
	if(S <= 0){
		unblock a process from queue 
	}
}




Read-Write Problem , Only no deadlock , no (2)(3) 

setup: 
semaphore mutex = 1 
semaphore wrt = 1 
readcount = 0 

Writers{
	do{
		wait(wrt)
			//Writing 
		signal(wrt)
	{while(true)
}
Readers{
	do{
		wait(mutex)
		readcount++
		if(readcount == 1) wait(wrt)
		signal(mutex)
		//reading
		wait(mutex)
		readcount-- 
		if(readcount == 0) signal(wrt)
		signal(mutex)
	while(true)
}





