package helloer;

import printer.IPrinter;
import printer.PrinterFactory;

public class Helloer {
    public static void main(String... args) {
	IPrinter printer = PrinterFactory.newInstance();
	printer.print("Hello World");
    }
}
