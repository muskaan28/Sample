package exchangeservice;

public class Demo {
    public static void main(String[] args) {
        ExchangeService exchangeService=new ExchangeService();
        exchangeService.addRate("EUR","GBP",100);
        double result=exchangeService.convert("EUR","GBP",88);
        System.out.println(result);
    }


}
