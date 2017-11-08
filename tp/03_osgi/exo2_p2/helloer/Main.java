package hmin304.cours.hello.impl;

import org.osgi.framework.*;

import hmin304.cours.print.IPrinter;
import hmin304.cours.print.PrinterFactory;

public class Main implements BundleActivator {

	public void start(BundleContext ctx) throws Exception {
	    Helloer h = new Helloer();
	    h.setPrinter(PrinterFactory.newInstance());
	    h.sayHello();
	}

	public void stop(BundleContext ctx) throws Exception {
		System.out.println("Goodbye !");
	}
}
