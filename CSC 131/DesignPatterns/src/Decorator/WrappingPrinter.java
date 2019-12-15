package Decorator;

import java.util.*;

public class WrappingPrinter extends PrinterDecorator {
	protected int width;

	public WrappingPrinter(Printer decorated, int width) {
		super(decorated);
		this.width = width;
	}

	public void print(String text) {
		int required, used;
		String token;
		StringTokenizer st;

		st = new StringTokenizer(text);
		used = 0;
		
		//Wrap text
		while (st.hasMoreTokens()) {
			token = st.nextToken();
			required = token.length();

			if ((required + used + 1) > width) {
				decorated.print("\n");
				decorated.print(token);
				used = required + 1;
			} else {
				if (used != 0) {
					decorated.print(" ");
					++used;
				}

				decorated.print(token);
				used += required;
			}
		}
	}
}
