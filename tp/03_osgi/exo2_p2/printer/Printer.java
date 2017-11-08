package hmin304.cours.print.impl;

import hmin304.cours.print.IPrinter;

public class Printer implements IPrinter {
    public void print(String message) {
	System.out.println(message);
    }
}
