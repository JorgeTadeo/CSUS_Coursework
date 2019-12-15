module full_adder_wires(
  input a,b,ci,
  output s, co, w1, w2, w3);

// wire w1, w2, w3;
//
// The "wire" declaration above was commented out because
// these wires were moved to the "output" portlist. This 
// technique is useful to make w1, w2, w3 visible for 
// debuggin at the testbench level 
//
 
xor   x1(w1, a, b);
xor   x2(s, w1, ci);
nand  n1(w2, w1, ci);
nand  n2(w3, a, b);
nand  n3(co, w2, w3);

endmodule
