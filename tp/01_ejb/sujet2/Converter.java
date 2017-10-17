package converter;

import java.util.ArrayList;
import java.util.Map;

public interface Converter {
    public double euroToOtherCurrency(double amount, String currencyCode); 
    public ArrayList<Monnaie> getAvailableCurrencies();
    public Map<Monnaie,Double> euroToOtherCurrencies(double amount);
}
