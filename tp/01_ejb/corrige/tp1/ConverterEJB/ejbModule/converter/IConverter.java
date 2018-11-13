package converter;

import java.util.Map;

import javax.ejb.Remote;

@Remote
public interface IConverter {
	public double euroToOtherCurrency(double amount, String currencyCode); 
	
}
