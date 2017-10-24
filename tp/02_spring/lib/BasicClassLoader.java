import java.util.*;
import java.io.*;

public class BasicClassLoader extends ClassLoader {
	
	private Hashtable<String,Class<?>> classes = new Hashtable<String,Class<?>>();
	private String directory;
	private boolean resolveIt;
	
	public BasicClassLoader() {
		this.directory = System.getProperty("user.home")+System.getProperty("file.separator");
		this.resolveIt = true;
	}
	
	@Override
	protected synchronized Class<?> findClass(String className) throws ClassNotFoundException {
		Class<?> result = null;
		byte classData[];
		System.out.println(">>>>>> Load class: "+className);
		result = classes.get(className);
		if(result != null) {
			System.out.println(">>>>>> Returning cached class.");			
		}
		else {
			System.out.println(">>>>>> Not a cached class.");
			try {
				result = super.findSystemClass(className);				
				System.out.println(">>>>>> Returning system class (in CLASSPATH).");				
			}
			catch(ClassNotFoundException e) {
				System.out.println(">>>>>> Not a system class.");
			}			
			if(result == null) {				
				classData = loadClassData(className);
		        if (classData == null) {
		            throw new ClassNotFoundException();
		        }
				        
		        result = defineClass(className,classData, 0, classData.length);
		        if (result == null) {
		            throw new ClassFormatError();
		        }
	
		        if (resolveIt) {
		            resolveClass(result);
		        }
		        System.out.println(">>>>>> Returning newly loaded class.");
			}
		}
		classes.put(className, result);
        return result;
	}			
	
	protected byte[] loadClassData(String className) {
    	System.out.println(">>>>>> Fetching the implementation of "+className+" ...");
    	
    	String classPath;    	        	
    	
    	if(this.directory != null) {
    		className = className.replace('.', System.getProperty("file.separator").charAt(0)); // Replace '.' by '/' or '\'
    		classPath = directory+className+".class";
    	}
    	else classPath = className+".class";    	    	
    	
    	byte result[];
    	FileInputStream fi = null;
    	try {    		
    	    fi = new FileInputStream(classPath);
    	    result = new byte[fi.available()];
    	    fi.read(result);
    	    System.out.println(">>>>>> Fetching the implementation of the class succeeded.");
    	    return result;
    	} 
    	catch (Exception e) {
    	    return null;
    	}
    	finally {
    		try {
				fi.close();
			} catch (IOException e) {				
				e.printStackTrace();
			}
    	}
    }
		
	public Class<?> loadClassInDirectory(String className, String dir) throws ClassNotFoundException {
		if(dir != null) this.directory = dir;
        return (loadClass(className, true));
    }
	
	public String getClassesInCache() {
		String result = "";
		Enumeration<String> e = classes.keys();
		while(e.hasMoreElements()) {
			result += e.nextElement()+"\n";
		}		
		return result;
	}
}
