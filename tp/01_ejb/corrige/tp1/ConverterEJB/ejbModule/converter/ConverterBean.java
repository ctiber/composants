package converter;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import java.util.List;


import javax.ejb.Stateless;
import javax.net.ssl.HttpsURLConnection;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;

@Stateless
public class ConverterBean implements IConverter {

    public ConverterBean() {        
    }
    
    public double euroToOtherCurrency(double amount, String currencyCode) {    	
    	double result = -1;
        SAXBuilder sxb = new SAXBuilder();
        URL url;
		try {
			url = new URL("https://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml");
		
	        HttpsURLConnection con = (HttpsURLConnection)url.openConnection();
	        Document document = sxb.build(con.getInputStream()); 
	        Element racine = document.getRootElement();
	        Namespace ns = Namespace.getNamespace("http://www.ecb.int/vocabulary/2002-08-01/eurofxref");
	        Element elem = racine.getChild("Cube", ns);
            elem = elem.getChild("Cube",ns);
            List<Element> liste = elem.getChildren("Cube", ns);
            for(Element e : liste) {
                String currencyX = e.getAttributeValue("currency");
                if(currencyX.equals(currencyCode)) {
                    double rate = Double.parseDouble(e.getAttributeValue("rate"));
                    return amount * rate;
                }
            }
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return result;
    }
    
   
}
