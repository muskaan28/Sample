package exchangeservice;

import java.util.HashMap;
import java.util.Map;

public class ExchangeService {

    private final Map<String, Map<String, Double>> rates;

    public ExchangeService() {
        this.rates = new HashMap<>();
    }

    public void addRate(String from, String to, double rate) {
        rates.putIfAbsent(from, new HashMap<>());
        rates.get(from).put(to, rate);
    }

    public double convert(String from, String to, double amount) {
        if(!rates.containsKey(from)) return -1;
        Map<String,Double> toMap=rates.get(from);
        if(!toMap.containsKey(to)) return -1;
        return amount*toMap.get(to);
    }
}