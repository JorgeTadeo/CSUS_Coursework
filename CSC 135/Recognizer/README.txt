/*
 * @AUTHOR RONGGUANG OU
 * 
 * 
 * EBNF Grammar :
 * 
 *     program  ::=  S {statemt}
 *      statemt  ::=  assnmt | ifstmt | do | inout | progcall
 *      assnmt   ::=  ident ~ exprsn ;
 *      ifstmt   ::=  I comprsn @ {statemt} [% {statemt}] &
 *      do       ::=  D {statemt} U comprsn E
 *      inout    ::=  iosym ident {, ident } ;
 *      iosym    ::=  R | O
 *      progcall ::=  C program G
 *      comprsn  ::=  ( oprnd opratr oprnd )
 *      exprsn   ::=  factor {+ factor}
 *      factor   ::=  oprnd {* oprnd}
 *      oprnd    ::=  integer | ident | bool | ( exprsn )
 *      opratr   ::=  < | = | > | ! | ^ 
 *      ident    ::=  letter {char}
 *      char     ::=  letter | digit
 *      integer  ::=  digit {digit}
 *      letter   ::=  W | X | Y | Z
 *      digit    ::=  0 | 1
 *      bool     ::=  T | F
 *
 *   The tokens are: S I D U E R O C G W X Y Z 0 1 T F ; ~ @ % & , ( ) + * < = > ! ^
 *   Nonterminals are shown as lowercase words.
 *   The following characters are NOT tokens (they are EBNF metasymbols):   | { } [ ]
 *   Note that parentheses are TOKENS, not EBNF metasymbols in this particular grammar.
 *   
 */


IMPORTANT : End of stream token ($) is automatically added to input string. 

There are two ways to input data , Choose 1 and comment out the other option.

Option 1. As an array 
Option 2. one string at a time 

Instruction for Option 1 :
	1.Go to main() and locate trueLegalString array 
	2.Paste all your "TRUE" legal string to trueLegalString array 
	3.Go to main() and locate trueIllegalString array 
	4.Paste all your "TRUE" illegal string to trueIllegalString array 


Instruction for Option 2 : 
	1. Enter expression when prompted , DO NOT end the string with "$" 
	2. To quit type in the string "die"
