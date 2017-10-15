package client;

import converter.Converter;
import java.util.Scanner;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class Client {
    public static void main(String[] args) throws NamingException {
        Converter converter = (Converter) InitialContext.doLookup("java:global/2017_2018_TP1/2017_2018_TP1-ejb/ConverterBean");
        System.out.print("Entrer le montant à convertir : ");
        Scanner sc = new Scanner(System.in);
        float montant = sc.nextFloat();
        System.out.println("L'équivalent en dollar est : "+converter.euroToOtherCurrency(montant, "USD"));
    }    
}
