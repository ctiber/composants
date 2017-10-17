<%@page import="javax.jms.MessageProducer"%>
<%@page import="javax.jms.TextMessage"%>
<%@page import="javax.jms.Session"%>
<%@page import="javax.jms.Connection"%>
<%@page import="javax.jms.QueueConnectionFactory"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="javax.naming.Context"%>
<%@page import="java.util.ArrayList"%>
<%@page import="converter.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Convertisseur de monnaie</title>
    </head>
    <body>
        <jsp:useBean class="converter.ConverterBean" id="beanConv"/>
        <h3>Convertir un montant exprimé en  euros dans d'autres monnaies</h3>
        <form action="index.jsp" method="post">
            <div style="margin-bottom: 20px;">
                <label for="montant">Montant à convertir : </label>
                <input type="number" id="montant" name="montant" value="100"/> euros
            </div>
            <div  style="margin-bottom: 20px;">
                <label for="monnaie">Monnaie cible : </label>
                <select id="monnaie" name="monnaie">
                    <%
                        ArrayList<Monnaie> monnaies = beanConv.getAvailableCurrencies();
                        for(Monnaie m : monnaies) {
                    %>
                            <option value="USD"><%= m.getCode()%></option>
                    
                    <%  } %>
                </select>
            </div>
            <div  style="margin-bottom: 20px;">
                <label for="email">Votre adresse email : </label>
                <input type="text" id="email" name="email"/>
                <input type="submit" name="convert" value="Convertir" style="margin-left: 50px;"/>
            </div>
        </form>
        
        <%
            String amount = request.getParameter("montant"); // ...
            if((amount!=null)&&(amount.length()!=0)) {                
                String currency = request.getParameter("monnaie");
                double resultat = beanConv.euroToOtherCurrency(Double.parseDouble(amount),currency);             
                out.println("<b>Résultat de la conversion : </b>"+resultat);
                String email = request.getParameter("email");
                if(email != null && email.length() != 0) {
                    Context jndiContext = new InitialContext();
                    javax.jms.ConnectionFactory connectionFactory = (QueueConnectionFactory)jndiContext.lookup("jms/MailContentQueueFactory");
                    Connection connection = connectionFactory.createConnection();
                    Session sessionQ = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
                    TextMessage message = sessionQ.createTextMessage();
                    message.setText(amount+"#"+email);
                    javax.jms.Queue queue = (javax.jms.Queue) jndiContext.lookup("jms/MailContentQueue");
                    MessageProducer messageProducer=sessionQ.createProducer(queue);
                    messageProducer.send(message);                    
                }
            }
        %>
    </body>
</html>
