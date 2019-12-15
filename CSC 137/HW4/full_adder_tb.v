`timescale 1ns/100ps
`include "full_adder.v"

module full_adder_tb();

reg a, b, ci;
wire s, co;

full_adder fa1(a, b, ci, s, co);

initial begin
  $display("-----------------------");
  $display("|t(ns)| a b ci | co s | ");
  $display("-----------------------");
  a = 0; b = 0; ci = 0; 
  #1 $display("|%4d | %b %b %b  |  %b %b |", $time, a, b, ci, co, s);
  a = 0; b = 0; ci = 1; 
  #1 $display("|%4d | %b %b %b  |  %b %b |", $time, a, b, ci, co, s);
  a = 0; b = 1; ci = 0; 
  #1 $display("|%4d | %b %b %b  |  %b %b |", $time, a, b, ci, co, s);
  a = 0; b = 1; ci = 1; 
  #1 $display("|%4d | %b %b %b  |  %b %b |", $time, a, b, ci, co, s);
  a = 1; b = 0; ci = 0; 
  #1 $display("|%4d | %b %b %b  |  %b %b |", $time, a, b, ci, co, s);
  a = 1; b = 0; ci = 1; 
  #1 $display("|%4d | %b %b %b  |  %b %b |", $time, a, b, ci, co, s);
  a = 1; b = 1; ci = 0; 
  #1 $display("|%4d | %b %b %b  |  %b %b |", $time, a, b, ci, co, s);
  a = 1; b = 1; ci = 1; 
  #1 $display("|%4d | %b %b %b  |  %b %b |", $time, a, b, ci, co, s);
  $display("-----------------------");
  end
endmodule 
