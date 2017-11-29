package printer;

import printer.impl.Printer;

public class PrinterFactory {
    public static IPrinter newInstance(){
	return new Printer();
    }    
}
