package hmin304.cours.print;

import hmin304.cours.print.impl.Printer;

public class PrinterFactory {
    public static IPrinter newInstance() {
	return new Printer();
    }
}
