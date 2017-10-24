package hmin304.spring;

public class Helloer implements IHelloer {
    @Override
    public void sayHello(String message) {
        System.out.println(message);
    }
}
