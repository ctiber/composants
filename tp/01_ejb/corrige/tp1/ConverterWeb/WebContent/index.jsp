<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Convertisseur de monnaie</title>
</head>
<body>
<h3>Convertisseur de monnaie à partir de l'euro</h3>
<form action="index.jsp" method="POST">
	<label for="montant">Montant à convertir : </label>
	<input type="number" id="montant" name="montant" value="100"></input>
	<label for="monnaie">Monnaie cible : </label>
	<select id="monnaie" name="monnaie">
		<option>USD</option>
		<option>GBP</option>
		<option>CHF</option>
		<option>JPY</option>
		<option>AUD</option>
		<option>CAD</option>
	</select>
	<input type="submit" value="Convertir"></input>
</form>
<jsp:useBean id="converter" class="converter.ConverterBean"></jsp:useBean>
<%

String montantRqt = request.getParameter("montant");
String monnaie = request.getParameter("monnaie");
if(montantRqt !=null && monnaie !=null) {
	double montant = Double.parseDouble(montantRqt);
	double resultat = converter.euroToOtherCurrency(montant, monnaie);
	out.println("Résultat de conversion : "+resultat+" "+monnaie);	
} 
%>
</body>
</html>