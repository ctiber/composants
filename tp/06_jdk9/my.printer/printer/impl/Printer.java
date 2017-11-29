package printer.impl;

import printer.IPrinter;

public class Printer implements IPrinter {
    public void print(String msg) {
	System.out.println(msg);
    }
}
