package circuitbreaker;

public class Response {
    int statusCode;
    String body;

    public Response(int statusCode, String body) {
        this.statusCode = statusCode;
        this.body = body;
    }
}