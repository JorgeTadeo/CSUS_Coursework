/*
    AUTHOR : RONGGUANG OU
    CSC 135 
    ASSIGNMENT 3A
*/


/* (1)-------------------------------------------------(1) */
game(893, terry, rene, 11, 2).    
game(894, marge, larry, 11, 8). 
game(721, marge, terry, 5, 11).
game(111, rene , terry , 11, 2 ).
game(222, rene , larry , 11 ,0).
game(999, a , b , 11 , 5).
game(998, a , b , 11 , 0).



win(P1,X,GameNumber) :- game(GameNumber,P1,X,11,0);game(GameNumber,X,P1,0,11).
win(P1,X,GameNumber) :- game(GameNumber,P1,X,11,1);game(GameNumber,X,P1,1,11).
win(P1,X,GameNumber) :- game(GameNumber,P1,X,11,2);game(GameNumber,X,P1,2,11).
win(P1,X,GameNumber) :- game(GameNumber,P1,X,11,3);game(GameNumber,X,P1,3,11).
win(P1,X,GameNumber) :- game(GameNumber,P1,X,11,4);game(GameNumber,X,P1,4,11).
win(P1,X,GameNumber) :- game(GameNumber,P1,X,11,5);game(GameNumber,X,P1,5,11).


pingPongMaster(P1) :- win(P1,_X,GM1) ,  win(P1,_Y,GM2) ,  GM1\=GM2.  




/* (2)-------------------------------------------------(2) */


deliver(fred, terry, emil).
deliver(fred, emil, sally).
deliver(fred, sally, cory).
deliver(fred, cory, art).
deliver(diane, steve, ali).
deliver(diane, ali, manuel).
deliver(diane, manuel, rick).


dhelper(X,_Y,T) :- X=T.
dhelper(X,Y,T) :- deliver(_M,X,Y) , dhelper(Y,_Z,T).
deliver(X,Y) :- dhelper(X,_Z,Y) , X\=Y.



/* (3)-------------------------------------------------(3) */


isEmpty([]).
count_occur([],0).
count_occur([X|Y],M):- isEmpty(X) , !,  count_occur(Y,M). 
count_occur([_X|Y],M) :- count_occur(Y,N) , M is N+1.



/* (4)-------------------------------------------------(4) */

listsMax([],[],[]).
listsMax([X1|Y1], [X2|Y2], [X1|Z]) :- X1>=X2 , listsMax(Y1,Y2,Z).
listsMax([X1|Y1], [X2|Y2], [X2|Z]) :- X1<X2 , listsMax(Y1,Y2,Z).



/* (5)-------------------------------------------------(5) */
chelper([],_N,M,M).
chelper([X|Y],OP,M,RES) :- OP = 1 , W is X+M , chelper(Y,2,W,RES).
chelper([X|Y],OP,M,RES) :- OP = 2 , W is M-X , chelper(Y,3,W,RES).
chelper([X|Y],OP,M,RES) :- OP = 3 , W is X*M , chelper(Y,1,W,RES).
cycler([X|Y],M,RES) :- chelper([X|Y],1,M,RES).



/* (6)-------------------------------------------------(6) */

digit(0). digit(1). digit(2).
digit(3). digit(4). digit(5).
digit(6). digit(7). digit(8).
digit(9).

 numGen4(I,X,M,RES) :- digit(I),digit(X),digit(M), RES is I*1000+X*100+M*10+M.
 numGen5(O,I,E,S,RES) :- digit(O),digit(I),digit(E),digit(S) , RES is O*10000+I*1000+I*100+E*10+S.
 unique(I,M,O,E,S) :- I\=M , I\=O , I\=E , I\=S , 
                             M\=O , M\=E , M\=S ,
                                    O\=E , O\=S , 
                                           E\=S . 
 
 isTrue1(RES,M,I,S) :- D1 is mod(RES,10) , D1=S,
                       D2 is mod(div(RES,10),10) , D2=I,
                       D3 is mod(div(RES,100),10) , D3=I,
                       D4 is mod(div(RES,1000),10) , D4=M,
                       D5 is mod(div(RES,10000),10) , D5=M.
                       
 isTrue2(RES,S,O,M,E,F,I,L) :- D1 is mod(RES,10) , D1=S,
                               D2 is mod(div(RES,10),10) , D2=E, 
                               D3 is mod(div(RES,100),10) , D3=L,
                               D4 is mod(div(RES,1000),10) , D4=I,
                               D5 is mod(div(RES,10000),10) , D5=F,
                               D6 is mod(div(RES,100000),10) , D6=E,
                               D7 is mod(div(RES,1000000),10) , D7=M,
                               D8 is mod(div(RES,10000000),10) , D8=O,
                               D9 is mod(div(RES,100000000),10) , D9=S.
                        
 somefiles(S,O,M,E,F,I,L) :- numGen4(I,_X,M,NUM1), numGen5(O,I,E,S,NUM2) , unique(I,M,O,E,S),
                             RES1 is NUM1*S, isTrue1(RES1,M,I,S),
                             RES2 is NUM1*NUM2 , isTrue2(RES2,S,O,M,E,F,I,L).
                             