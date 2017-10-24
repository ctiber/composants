package hmin304.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class Main {
    
    public static void main(String[] args) throws BeansException {
        ApplicationContext ctx = new FileSystemXmlApplicationContext("./config.xml");
        IHelloer helloer = (IHelloer) ctx.getBean("helloer");
        helloer.sayHello("Hello World !!!");
    }
    
}
