/* AUTHOR : RONGGUANG OU
CSC 137 PROJECT 1
IMPLEMENT 3-bit CPA Adder
*/

module Full_Adder(a,b,cin,s,cout);
  input a,b,cin;
  output s,cout;
  wire u,v,w;

  xor(u,a,b);
  xor(s,u,cin);

  nand(v,u,cin);
  nand(w,a,b);
  nand(cout,v,w);

endmodule //END Full_Adder