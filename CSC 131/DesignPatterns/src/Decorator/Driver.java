package Decorator;

public class Driver {
	public static void main(String[] args) {
		Printer printer;
		String text;

		text = "This is the text that we will use " + "to demonstrate the capabilities "
				+ "of different Printer objects.";

		printer = new ConsolePrinter();
		printer.print(text);

		System.out.print("\n\n");

		printer = new UppercasePrinter(new ConsolePrinter());
		printer.print(text);

		System.out.print("\n\n");

		printer = new WrappingPrinter(new ConsolePrinter(), 20);
		printer.print(text);

		System.out.print("\n\n");

		printer = new WrappingPrinter(new UppercasePrinter(new ConsolePrinter()), 20);
		printer.print(text);
	}

}
