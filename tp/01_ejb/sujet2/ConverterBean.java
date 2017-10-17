package converter;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateful;
import javax.ejb.Remote;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;

@Stateful
@Remote(Converter.class)
public class ConverterBean implements Converter {
    private SAXBuilder sxb;
    private Document document;
    
    public ConverterBean() {
        try {
            this.sxb = new SAXBuilder();
            URL url = new URL("http://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml");
            this.document = sxb.build(url); 
        }
        catch (MalformedURLException ex) {
            Logger.getLogger(ConverterBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JDOMException | IOException ex) {
            Logger.getLogger(ConverterBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public double euroToOtherCurrency(double amount, String currencyCode) {
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
                
    }
    
    @Override
    public ArrayList<Monnaie> getAvailableCurrencies() {
        ArrayList<Monnaie> monnaies = new ArrayList<>();
        try {
            Element racine = document.getRootElement();
            Namespace ns = Namespace.getNamespace("http://www.ecb.int/vocabulary/2002-08-01/eurofxref");
            
            URL url = new URL("http://localhost/~chouki/m3203_web/list_one.xml");
            //URL url = new URL("http://www.currency-iso.org/dam/downloads/lists/list_one.xml");
            Document doc2 = sxb.build(url);
            Element racine2 = doc2.getRootElement();
            Element elem2 = racine2.getChild("CcyTbl");            
            
            Element elem = racine.getChild("Cube", ns);
            elem = elem.getChild("Cube",ns);
            for(Element e : elem.getChildren()) {
                if(e.getAttribute("currency") != null) {
                    String code = e.getAttributeValue("currency");
                    String nom;
                    
                    for(Element elem3 : elem2.getChildren()) {                        
                        if(elem3.getChild("Ccy") != null) {
                            if(elem3.getChild("Ccy").getText().equals(code)) {
                                
                                nom = elem3.getChild("CcyNm").getText();
                                float taux = Float.parseFloat(e.getAttributeValue("rate"));
                                ArrayList<String> pays = getPays(elem2,code);
                                Monnaie m = new Monnaie(pays,nom,code,taux);                                
                                monnaies.add(m);
                                break;
                            }
                        }
                    }
                    
                }
            }
            
            
        } catch (JDOMException | IOException ex) {
            System.err.println(ex);
        }
        
        return monnaies;
    }
    
    @Override
    public Map<Monnaie,Double> euroToOtherCurrencies(double amount) {        
        Map<Monnaie,Double> conversions = new HashMap<>();
        
        this.getAvailableCurrencies().stream().forEach((m) -> {            
            conversions.put(m, amount*m.getTaux());            
        });
        
        return conversions;
    }

    // Méthode qui retourne la liste de pays qui utilisent la monnaie
    // indiquée en paramètre (code)
    private ArrayList<String> getPays(Element elem2, String code) {
        ArrayList<String> pays = new ArrayList<>();
        elem2.getChildren().stream().filter((elem3) -> (elem3.getChild("Ccy") != null)).filter((elem3) -> (elem3.getChild("Ccy").getText().equals(code))).forEach((elem3) -> {
            pays.add(elem3.getChild("CtryNm").getText());
        });        
        return pays;
    }
}
