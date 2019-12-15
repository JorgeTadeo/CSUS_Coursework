package Decorator;

public abstract class PrinterDecorator implements Printer {
	protected Printer decorated;

	public PrinterDecorator(Printer decorated) {
		this.decorated = decorated;
	}

	public void print(String text) {
		decorated.print(text);
	}
}
