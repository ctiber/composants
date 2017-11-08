package hmin304.cours.hello.impl;

import org.osgi.framework.*;

import hmin304.cours.print.IPrinter;

public class Main implements BundleActivator {

	public void start(BundleContext ctx) throws Exception {
	    Helloer h = new Helloer();
	    ServiceReference ref = ctx.getServiceReference(IPrinter.class.getName());
	    IPrinter p = (IPrinter)ctx.getService(ref);
	    
	    h.setPrinter(p);
	    h.sayHello();
	}

	public void stop(BundleContext ctx) throws Exception {
		System.out.println("Goodbye !");
	}
}
