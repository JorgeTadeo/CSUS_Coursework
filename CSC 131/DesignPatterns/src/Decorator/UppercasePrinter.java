package Decorator;

public class UppercasePrinter extends PrinterDecorator {
	public UppercasePrinter(Printer decorated) {
		super(decorated);
	}

	public void print(String text) {
		decorated.print(text.toUpperCase());
	}
}
