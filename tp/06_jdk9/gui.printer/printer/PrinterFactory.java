package printer;

import printer.impl.GraphicalPrinter;

public class PrinterFactory {
    public static IPrinter newInstance(){
	return new GraphicalPrinter();
    }    
}
