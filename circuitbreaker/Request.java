package circuitbreaker;

public class Request {
    String serviceName;
    long timestamp;

    public Request(String serviceName, long timestamp) {
        this.serviceName = serviceName;
        this.timestamp = timestamp;
    }
}