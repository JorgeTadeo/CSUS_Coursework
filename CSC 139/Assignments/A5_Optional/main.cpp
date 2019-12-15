#include <iostream>
#include <fstream>
#include <queue>
#include <climits>
#include <iomanip>
#include <bits/stdc++.h> 
#include <functional>
using namespace std; 

class Process{
	public: 
			int burstTime;
			int pid;
			int arrivalTime;
			int priorty;
			bool alive;
			bool arrived;
			int waited;
			
			/* Constructor */
			Process(int pid , int arrivalTime , int burstTime , int priority){
				this->pid = pid;
				this->burstTime = burstTime;
				this->arrivalTime = arrivalTime;
				this->priorty = priority;
				alive = true;
				arrived = false;
				waited = 0;
			}
			Process(){
				this->pid = -1;
			}

			Process(const Process& p2){
				this->pid = p2.pid;
				this->burstTime = p2.burstTime;
				this->arrivalTime = p2.arrivalTime;
				this->priorty = p2.priorty;
				this->alive = p2.alive;
				this->arrived = p2.arrived;
				this->waited = p2.waited;
			}

			void print(){
				cout << pid << "," << arrivalTime << "," << burstTime << "," << priorty << endl;
			}
};

void roundRobin(Process* processArray, int numProcesses, int timeQuantum , ofstream& outFile){
		queue<Process> process_queue;
		int timer = timeQuantum;
		int CPU = 0;
		int done = 0;
		int waitTime = 0;
		Process currentProcess;
		outFile << "RR" << " " << numProcesses << endl;
		/* From  time = 0 to inf */ 
		for(int i = 0 ; i < INT_MAX ; i++ ){
			/*Add all arrival process if any  */
			for(int j = 0 ; j < numProcesses; j++){
				/* If process arrvial time match current time which is i */
				if(processArray[j].arrivalTime == i && processArray[j].arrived == false){
					/* Add to queue */ 
					processArray[j].arrived = true;
					process_queue.push(processArray[j]);
				}
			}
			/* Kick if process done or timer ends */
			if( currentProcess.burstTime == 0 || timer == timeQuantum){
				/* Reset timer */
				timer = 0;	
				/* If Process not done , add back to queue */
				if(currentProcess.burstTime != 0 && currentProcess.pid > 0){
					process_queue.push(currentProcess);
				}
				/* Add next process to CPU */
				if(process_queue.front().pid == 0){
					outFile << "AVG Waiting Time : " << fixed << setprecision(2) << waitTime / (double)numProcesses;
					cout << "AVG Waiting Time : " << fixed << setprecision(2) << waitTime / (double)numProcesses;
					return;
				}
				currentProcess = process_queue.front();
				process_queue.pop();
				/* Print out new process */ 
				cout << "Time : " << i << "  Process : " << currentProcess.pid << endl;
				outFile <<  i << "  " << currentProcess.pid << endl;
			}
			/* Update */
			currentProcess.burstTime = currentProcess.burstTime - 1;
			waitTime += process_queue.size();
			timer++;
		}
}




struct compare{
	bool operator()(const Process& lhs , const Process& rhs){
		return lhs.burstTime > rhs.burstTime;
	}
};

void SJF(Process* processArray, int numProcesses, ofstream& outFile){
		outFile << "SJF" << " " << numProcesses <<  endl;
		queue<Process> process_queue;
		priority_queue<Process , vector<Process> , compare > pq;	
		Process* currentProcess = NULL;
		int waitTime = 0;
		/* From  time = 0 to inf */ 
		for(int i = 0 ; i < INT_MAX ; i++ ){			
			/* Add any incoming process */ 
			for(int j = 0 ; j < numProcesses ; j++){
				if(processArray[j].arrivalTime == i){
					pq.push(processArray[j]);
				}
			}
			if(currentProcess == NULL || currentProcess->burstTime == 0){
				if(pq.empty() == false){
					Process next = pq.top();
					currentProcess = &next;
					pq.pop();
					outFile << i << " " << next.pid << endl;
					cout << i << " " << next.pid << endl;
				}else{
					outFile << "AVG Waiting Time: " << fixed << setprecision(2) << (double)waitTime/numProcesses << endl;
					cout << "AVG Waiting Time: " << fixed << setprecision(2) << (double)waitTime/numProcesses << endl;
					return;
				}
			}			
			/* Update */
			currentProcess->burstTime = currentProcess->burstTime - 1;	
			waitTime += pq.size();

		}
		return ;
}

struct compare2{
	bool operator()(const Process& lhs , const Process& rhs){
		return lhs.priorty > rhs.priorty;
	}
};

void PR_noPREMP(Process* processArray, int numProcesses , ofstream& outFile){
		outFile << "PR_noPREMP" << " " << numProcesses << endl;
		queue<Process> process_queue;
		priority_queue<Process , vector<Process> , compare2 > pq;	
		Process* currentProcess = NULL;
		int waitTime = 0;
		/* From  time = 0 to inf */ 
		for(int i = 0 ; i < INT_MAX ; i++ ){			
			/* Add any incoming process */ 
			for(int j = 0 ; j < numProcesses ; j++){
				if(processArray[j].arrivalTime == i){
					pq.push(processArray[j]);
				}
			}
			if(currentProcess == NULL || currentProcess->burstTime == 0){
				if(pq.empty() == false){
					Process next = pq.top();
					currentProcess = &next;
					pq.pop();
					outFile << i << " " << next.pid << endl;
					cout << i << " " << next.pid << endl;
				}else{
					outFile << "AVG Waiting Time: " << fixed << setprecision(2) << (double)waitTime/numProcesses << endl;
					cout << "AVG Waiting Time: " << fixed << setprecision(2) << (double)waitTime/numProcesses << endl;
					return;
				}
			}		
			/* Update */
			currentProcess->burstTime = currentProcess->burstTime - 1;	
			waitTime += pq.size();
		}
		return ;
}

void PR_withPREMP(Process* processArray, int numProcesses , ofstream& outFile){
		outFile << "PR_withPREMP" << " " << numProcesses << endl;
		queue<Process> process_queue;
		priority_queue<Process , vector<Process> , compare2 > pq;	
		Process* currentProcess = NULL;
		int waitTime = 0;
		/* From  time = 0 to inf */ 
		for(int i = 0 ; i < INT_MAX ; i++ ){
			bool found = false; 
			int index = -1;
			int min = -1;
			if(currentProcess != NULL){
				min = currentProcess->priorty;			
			}
			// /* Add any incoming process */ 
			for(int j = 0 ; j < numProcesses ; j++){
				if(processArray[j].arrivalTime == i && currentProcess != NULL){
					if(processArray[j].priorty < min){
						index = j;
						found = true;
					}
				}
			}
			for(int j = 0 ; j < numProcesses ; j++){
				if(found == true && j == index) continue;	
				if(processArray[j].arrivalTime == i){
					pq.push(processArray[j]);
				}
			}
			/* better one comes in */
			if(currentProcess != NULL && found == true){
				/* kick off current by adding to queue */
				Process next = processArray[index];
				if(currentProcess->burstTime != 0){
					pq.push(*currentProcess);
				}
				currentProcess = &next;
				cout << i << " " << next.pid << endl;
				outFile << i << " " << next.pid << endl;
			}
			/* First one   OR  current finish */
			if(currentProcess == NULL || currentProcess->burstTime == 0 ){
				if(pq.empty() == false){
					Process next = pq.top();
					currentProcess = &next;
					pq.pop();
					cout << i << " " << next.pid << endl;
					outFile << i << " " << next.pid << endl;
				}else{
					cout << "AVG Waiting Time: " << fixed << setprecision(2) << (double)waitTime/numProcesses << endl;
					outFile << "AVG Waiting Time: " << fixed << setprecision(2) << (double)waitTime/numProcesses << endl;
					return;
				}
			}			
			/* Update */
			currentProcess->burstTime = currentProcess->burstTime - 1;	
			waitTime += pq.size();

		}
		return ;
}


int main() {
	string algorithm = "";
	int timeQuantum;
	int numProcesses;
	string fileName = "input.txt";
	string outputFileName = "output.txt";
	fstream inFile(fileName.c_str(), ios::in);
	ofstream outFile(outputFileName.c_str());
	if(inFile){ 

		inFile >> algorithm;

		if(algorithm == "RR"){
			inFile >> timeQuantum;
			inFile >> numProcesses;
		}else if(algorithm == "SJF" || "PR_noPREMP" || "PR_withPREMP"){
			inFile >> numProcesses;
		}

		Process processArray[numProcesses];
		int pid;
		int burstTime;
		int arrivalTime;
		int priority;
		
		/* Collect process information */
		for(int i = 0 ; i < numProcesses; i++){
			inFile >> pid;
			inFile >> arrivalTime;
			inFile >> burstTime;
			inFile >> priority;
			Process newProcess(pid,arrivalTime,burstTime,priority);
			processArray[i] = newProcess;
			
		}
		/* Optional: print information collected  */ 
		for(int i = 0 ; i < numProcesses;i++){
			processArray[i].print();
		}

		inFile.close();
		cout << endl << endl;

		if(algorithm == "RR"){
			roundRobin(processArray , numProcesses , timeQuantum , outFile);
		}else if(algorithm == "SJF"){
			SJF(processArray, numProcesses , outFile);
		}else if(algorithm == "PR_noPREMP"){
			PR_noPREMP(processArray, numProcesses, outFile);
		}else if(algorithm == "PR_withPREMP"){
			PR_withPREMP(processArray, numProcesses, outFile);
		}
	
	}else{
		cout << "Error opening file" << endl;
	}

}

