package circuitbreaker;

import java.util.HashMap;
import java.util.Map;

class WebClient {

    private final Map<String, CircuitBreaker> circuitBreakers = new HashMap<>();

    private final Map<String, Integer> serviceFailures = new HashMap<>();

    private final Map<String,Response> cache=new HashMap<>();

    public Response execute(Request request){
        CircuitBreaker cb= circuitBreakers.computeIfAbsent(request.serviceName,k->new CircuitBreaker());

        if(!cb.allowRequest()){
            if(cache.containsKey(request.serviceName)){
                return cache.get(request.serviceName);
            }
            throw new IllegalStateException("Service call failure " + request.serviceName);
        }
        try{
            Response response=callService(request);
            if(response.statusCode<500){
                cb.onSuccess();
                cache.put(request.serviceName,response);
            } else{
                cb.onFailure();
            }
            return response;
        } catch (Exception e){
            cb.onFailure();
            throw new IllegalStateException("Service call failure " + request.serviceName);
        }
    }

    private Response callService(Request request){
        int count=serviceFailures.getOrDefault(request.serviceName,0);
        if(count<3){
            serviceFailures.put(request.serviceName,count+1);
            throw new IllegalStateException("Service call failure " + request.serviceName);
        }

        return new Response(200,"Success");
    }
}