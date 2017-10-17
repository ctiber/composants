package converter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSDestinationDefinition;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@JMSDestinationDefinition(name = "jms/MailContentQueue", interfaceName = "javax.jms.Queue", resourceAdapter = "jmsra", destinationName = "MailContentQueue")
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/MailContentQueue"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class MailerMDB implements MessageListener {
    
    @EJB
    Converter converter;
    
    public MailerMDB() {
    }
    
    @Override
    public void onMessage(Message message) {
        try { 
            if (message instanceof TextMessage) {                            
                TextMessage mesg = (TextMessage) message; 
		String content = mesg.getText();
                // Récupérer le montant à convertir :
                String s = content.substring(0,content.indexOf("#"));
                
                double amount = Double.parseDouble(s);
                // Demander au bean session de faire toutes les conversions ... 
                Map<Monnaie,Double> map = converter.euroToOtherCurrencies(amount);
		Properties p = new Properties();
		p.put("mail.smtp.host", "smtp.gmail.com");		
		p.put("mail.smtp.auth", "true");
		p.put("mail.smtp.starttls.enable","true");
                javax.mail.Session session = javax.mail.Session.getInstance(p);
                javax.mail.Message msg = new MimeMessage(session);
		try { 
                    // Préparation du mail
                    msg.setFrom(new InternetAddress("chouki.tiber@gmail.com"));
                    String dest = content.substring(content.indexOf("#")+1);                    
                    msg.setRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(dest));
                    String sujet = "Conversions de monnaie";
                    msg.setSubject(sujet);
                    // Préparation du contenu du mail :
                    content = produceEmailContent(map);
                    msg.setContent(content,"text/html;charset=utf8");                                                           
                    msg.setSentDate(Calendar.getInstance().getTime());                    
                    // Préparation de l'envoi du mail
                    Transport transport = session.getTransport("smtp");
                    transport.connect("smtp.gmail.com",587,"chouki.tiber","...");                    
                    // Envoi du mail
                    transport.sendMessage(msg,msg.getAllRecipients());
                    transport.close();
                    System.out.println("Email envoyé à "+dest); // une petite vérif.
                }
                catch(MessagingException e){e.printStackTrace();}
            }
        } catch (JMSException ex) {ex.printStackTrace();}
    }    
    
    private String produceEmailContent(Map<Monnaie,Double> map) {
        String content ="<table border=\"1\"><tr><th width=\"50%\">Currency</th><th width=\"25%\">Actual Rate</th>"+
                            "<th width=\"25%\">Converted Amount</th></tr>";
                    
        for(Entry<Monnaie, Double> entry : map.entrySet()) {
            Monnaie m = entry.getKey();
            content +="<tr>"+"<td>"+m.getNom()+" (";

            List<String> pays = m.getPays();
            Iterator it = pays.iterator();
            while(it.hasNext()) {
                content+=it.next();
                if(it.hasNext()) content+=", ";
            }

            content +=") </td>"+"<td>"+m.getTaux()+"</td>"+
                    "<td>"+entry.getValue()+"</td>"+"</tr>";                        
        }                    
        content +="</table>";
        return content;
    }
}
