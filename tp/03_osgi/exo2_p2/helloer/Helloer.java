package hmin304.cours.hello.impl;

import hmin304.cours.hello.IHelloer;
import hmin304.cours.print.IPrinter;

public class Helloer implements IHelloer {

    IPrinter printer;

    public void setPrinter(IPrinter printer) {
	this.printer = printer;
    }
    
    public void sayHello() {
	printer.print("Hello World !!!");
    }
}
