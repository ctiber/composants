package converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface Converter {
    public double euroToOtherCurrency(double amount, String currencyCode); 
    public List<Monnaie> getAvailableCurrencies();
    public Map<Monnaie,Double> euroToOtherCurrencies(double amount);
}
