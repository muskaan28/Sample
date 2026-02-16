package circuitbreaker;

public class Main {

    public static void main(String[] args) throws Exception {
        WebClient client=new WebClient();

        for(int i=1;i<=3;i++){
            try{
                Response response=client.execute(new Request("ServiceB",System.currentTimeMillis()));
                System.out.println(response.body);
            } catch (Exception e){
                System.out.println(e.getMessage());
            }
        }

        // Wait cooldown
        System.out.println("\nWaiting for cooldown...\n");

        Thread.sleep(5000);

        try {
                Response response=client.execute(new Request("ServiceB",System.currentTimeMillis()));
                System.out.println(response.body);
        } catch (Exception e) {
                System.out.println(e.getMessage());
        }

    }
}