package circuitbreaker;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class CircuitBreaker {

    enum State {
        CLOSED,
        OPEN,
        HALF_OPEN
    }

    @Getter
    private State state=State.CLOSED;

    private final int FAILURE_THRESHOLD=3;
    private final long FAILURE_WINDOW=10*1000;
    private final long COOL_DOWN=5*1000;
    private final List<Long> failureTimestamps=new ArrayList<>();
    private long openTime=0;


    public boolean allowRequest(){
        long now=System.currentTimeMillis();
        if(state==State.OPEN){
            if(now-openTime>=COOL_DOWN){
                state=State.HALF_OPEN;
                return true;
            }
            else{
                return false;
            }
        }
        return true;
    }

    public void onSuccess(){
        failureTimestamps.clear();
        state=State.CLOSED;
    }

    public void onFailure(){
        long now=System.currentTimeMillis();
        failureTimestamps.add(now);
        failureTimestamps.removeIf(t->now-t>FAILURE_WINDOW);
        if(failureTimestamps.size()>=FAILURE_THRESHOLD){
            state=State.OPEN;
            openTime=now;
        } else if(state==State.HALF_OPEN){
            state=State.OPEN;
            openTime=now;
        }
    }


}
