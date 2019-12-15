/* AUTHOR : RONGGUANG OU
CSC 137 PROJECT 1
IMPLEMENT 3-bit CPA Adder
*/

`include "fullAdder.v"

module adder_3bits(a0,b0,a1,b1,a2,b2,s0,s1,s2,cin,cout);
  input a0,b0,a1,b1,a2,b2;
  input cin;

  output cout;
  output s0,s1,s2;

  wire [1:0] intermediateC;

  Full_Adder fa0(a0,b0,cin,s0,intermediateC[0]);
  Full_Adder fa1(a1,b1,intermediateC[0],s1,intermediateC[1]);
  Full_Adder fa2(a2,b2,intermediateC[1],s2,cout);

endmodule