package hmin304.cours.print.impl;

import hmin304.cours.print.IPrinter;

import org.osgi.framework.*;

public class PrinterActivator implements BundleActivator {

     private ServiceRegistration registration;
    
    public void start(BundleContext ctx) throws Exception {
	registration = ctx.registerService(IPrinter.class.getName(),
					   new Printer(),null);
    }

    public void stop(BundleContext ctx) throws Exception {
	registration.unregister();
    }
}
