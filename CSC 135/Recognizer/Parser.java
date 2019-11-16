//import java.io.*;
import java.util.Scanner;
/*
 * @AUTHOR RONGGUANG OU
 * @CSC 135 HW1 Recognizer 
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

public class Parser {
	
	static String inputString;
	static int index = 0;
	static int errorflag = 0;

	
	/*
	 * Util Functions
	 */
	private char token() {return inputString.charAt(index);}
	private void advancePtr() { if(index < inputString.length()-1)index++; }
	private void match(char t) {
		if(t == token())
			advancePtr();
		else { 
			System.out.println("Did you mean to put " + t + "?");
			error();
		}
	}
	private void error() {
		System.out.println("Error at position: " + index +  " token is " + token());
		
		errorflag = 1;
		advancePtr(); //Continue for checking
	}
	private void start() {
	    inputString += "$";
	    program();
	    match('$');
	    if (errorflag == 0)
	      System.out.println("legal." + "\n");
	    else
	      System.out.println("errors found." + "\n");
	    
	    index = 0;
	    errorflag = 0;
	}
	private void checkLegal(String input[]) {
		//Check all TRUE legal strings
		for(int i = 0 ; i < input.length; i++) {
			inputString = input[i];
			inputString += "$";
			program();
			match('$');
			if(errorflag == 0)
				System.out.println("Legal");
			else
				System.out.println("Illegal");
			index = 0; // Reset
			errorflag = 0; //Reset
		}
	}
	private void checkIllegal(String input[]) {
		//Check all TRUE illegal string 
		for(int i = 0 ; i < input.length; i++) {
			inputString = input[i];
			inputString += "$";
			program();
			match('$');
			if(errorflag == 0)
				System.out.println("Legal");
			else
				System.out.println("Illegal");
			index = 0; // Reset
			errorflag = 0; //Reset
		}
	}
	
	
	/*
	 * Rules
	 */
	private void program() {
		match('S');
		while(
			  token() == 'W' ||
			  token() == 'X' ||
			  token() == 'Y' ||
			  token() == 'Z' ||
			  token() == 'I' ||
			  token() == 'D' ||
			  token() == 'R' ||
			  token() == 'O' ||
			  token() == 'C' ) {
			statemt();
		}
	}
	private void statemt() {
		if(token() == 'W' || token() == 'X' || token() == 'Y' || token() == 'Z') {
			assnmt();
		}else if(token() == 'I') {
			ifstmt();
		}else if(token() == 'D') {
			doo();
		}else if(token() == 'R' ||  token() == 'O') {
			inout();
		}else// if(token() == 'C') 
			progcall();
	}
	private void assnmt() {
		ident();
		match('~');
		exprsn();
		match(';');
	}
	private void ifstmt() { 
		match('I');
		comprsn();
		match('@');
		
		while( //first(statemt)
					  token() == 'W' ||
					  token() == 'X' ||
					  token() == 'Y' ||
					  token() == 'Z' ||
					  token() == 'I' ||
					  token() == 'D' ||
					  token() == 'R' ||
					  token() == 'O' ||
					  token() == 'C'   ) {
				statemt();
		}
		if(token() == '%') {
			match('%');
			while( //first(statemt)
					  token() == 'W' ||
					  token() == 'X' ||
					  token() == 'Y' ||
					  token() == 'Z' ||
					  token() == 'I' ||
					  token() == 'D' ||
					  token() == 'R' ||
					  token() == 'O' ||
					  token() == 'C'   ) {
				statemt();
			}
		}
		match('&');	
	}
	private void doo() {
		match('D');
		while(
					  token() == 'W' ||
					  token() == 'X' ||
					  token() == 'Y' ||
					  token() == 'Z' ||
					  token() == 'I' ||
					  token() == 'D' ||
					  token() == 'R' ||
					  token() == 'O' ||
					  token() == 'C'   ) {
				statemt();
		}
		match('U');
		comprsn();
		match('E');	
	}
	private void inout() { 
		iosym();
		ident();
		while(token() ==',') {
			match(',');
			ident();
		}
		match(';');
			
	}
	private void progcall() {
		match('C');
		program();
		match('G');
	}
	private void exprsn() {
		factor();
		while(token() == '+') {
			match('+');
			factor();
		}
	}
	private void comprsn() {
		match('(');
		oprnd();
		opratr();
		oprnd();
		match(')');
	}
	private void iosym() {
		if(token() == 'R') {
			match('R');
		}
		else // if(token() == 'O') 
			match('O');
	}
	private void ident() {
		letter();
		while(
				token() == 'W' ||
				token() == 'X' ||
				token() == 'Y' ||
				token() == 'Z' ||
				token() == '0' ||
				token() == '1'   ) {
			charr();
		}
	}
	private void oprnd() {
		if(token() == '0' || token() == '1') {
			integer();
		}else if(token() == 'W' || token() == 'X' || token() == 'Y' || token() == 'Z') {
			ident();
		}else if(token() == 'T' || token() == 'F') {
			bool();
		}else if(token() == '('){ 
			match('(');
			exprsn();
			match(')');
		}else {
			error(); 
		}
	}
	private void opratr() {
		if(token() == '<') {
			match('<');
		}else if(token() == '=') {
			match('=');
		}else if(token() == '>') {
			match('>');
		}else if(token() == '!') {
			match('!');	
		}else// if(token() == '^') {
			match('^');
	}
	private void factor() {
		oprnd();
		while(token() == '*') {
			match('*');
			oprnd();
		}
	}
	private void integer() {
		do {
			digit();
		}while(token() == '0' || token() == '1');
	}
	private void bool() {
		if(token() == 'T') {
			match('T');
		}else {
			match('F');
		}
	}
	private void letter() {
		if(token() == 'W') {
			match('W');
		}else if(token() == 'X') {
			match('X');
		}else if(token() == 'Y') {
			match('Y');
		}else {
			match('Z');
		}
	}
	private void charr() {
		if(token() == '0' || token() == '1') {
			digit();
		}else {
			letter();
		}
	}
	private void digit() {
		if(token() == '0') {
			match('0');
		}else {
			match('1');
		}
	}
	
	/*
	 * 	Sample Legal String : 
	 * 
	 * SW~1;
	 */
	public static void main(String[] args) {
		/*
		 * NOTE : All input string does not need to be terminated by $
		 * 		"$" is added by program.
		 * 
		 * PLEASE READ the readme.txt before using this recognizer 
		 */
		Parser rec = new Parser();
		
		// OPTION 1  
		
		String trueLegalString[] =  {
			//Insert true legal string here
		};
		String trueIllegalString[] = {
			//Insert true illegal string here
		};
		
		rec.checkLegal(trueLegalString);
		rec.checkIllegal(trueIllegalString);
		
		// OPTION 2
		
	    Scanner input = new Scanner(System.in);
	    String end = "die";
		do{
		    System.out.print("\n" + "enter an expression: ");
		    inputString = input.nextLine();
		    if(inputString.equals(end)) break;
		    rec.start();
		}while(true);
		input.close();
		System.out.println("Program Terminated by \"die\" command");
	}
}
