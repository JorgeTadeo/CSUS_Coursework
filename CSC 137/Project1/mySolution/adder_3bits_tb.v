/* AUTHOR : RONGGUANG OU
CSC 137 PROJECT 1
IMPLEMENT 3-bit CPA Adder
*/

`timescale 1ns/100ps
`include "adder_3bits.v"

module CPA_3bit_tb();

reg a0,a1,a2;
reg b0,b1,b2;
reg cin;

wire s0,s1,s2;
wire cout;

adder_3bits myAdder(a0,b0,a1,b1,a2,b2,s0,s1,s2,cin,cout);

initial begin 
$display("=======================================================");
$display("| t(ns) | a2 a1 a0 | b2 b1 b0 | cin cout | s2 s1 s0  | ");
$display("=======================================================");
$display("=======================================================");
//Test case here 
a2 = 0; a1 = 0; a0 = 1; b2 = 0; b1 = 0; b0 = 1; cin = 1; //case 1
#1 $display("|%4d   | %b  %b  %b  | %b  %b  %b  |  %b   %b   | %b  %b  %b   |", $time, a2, a1, a0, b2, b1, b0, cin, cout, s2, s1, s0 );
a2 = 0; a1 = 1; a0 = 1; b2 = 0; b1 = 1; b0 = 0; cin = 1;  //case 2
#1 $display("|%4d   | %b  %b  %b  | %b  %b  %b  |  %b   %b   | %b  %b  %b   | ", $time, a2, a1, a0, b2, b1, b0, cin, cout, s2, s1, s0);
a2 = 0; a1 = 1; a0 = 1; b2 = 1; b1 = 0; b0 = 0; cin = 0; //case 3
#1 $display("|%4d   | %b  %b  %b  | %b  %b  %b  |  %b   %b   | %b  %b  %b   |", $time, a2, a1, a0, b2, b1, b0, cin, cout, s2, s1, s0 );
a2 = 1; a1 = 1; a0 = 1; b2 = 0; b1 = 0; b0 = 1; cin = 1;  //case 4
#1 $display("|%4d   | %b  %b  %b  | %b  %b  %b  |  %b   %b   | %b  %b  %b   | ", $time, a2, a1, a0, b2, b1, b0, cin, cout, s2, s1, s0);

end
endmodule