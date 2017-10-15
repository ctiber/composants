package converter;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;

@Stateless
@LocalBean
public class ConverterBean implements Converter {

    @Override
    public double euroToOtherCurrency(double amount, String currencyCode) {
        SAXBuilder sxb = new SAXBuilder();
        URL url;
        try {
            url = new URL("http://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml");
            Document document = sxb.build(url); 
            Element racine = document.getRootElement();
            Namespace ns = Namespace.getNamespace("http://www.ecb.int/vocabulary/2002-08-01/eurofxref");
            Element elem = racine.getChild("Cube", ns);
            elem = elem.getChild("Cube",ns);
            for(Element e : elem.getChildren()) {                        
                if(e.getAttributeValue("currency").equals(currencyCode)) {
                    float rate = Float.parseFloat(e.getAttributeValue("rate"));
                    return amount * rate;
                }
            }
            throw new RuntimeException ("Unknown Currency Code");
                        
        } catch (MalformedURLException ex) {
            Logger.getLogger(ConverterBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JDOMException | IOException ex) {
            Logger.getLogger(ConverterBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

}
