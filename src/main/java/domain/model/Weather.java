package domain.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Weather {
    private final int humidity;
    private final float maxTemp;
    private final float minTemp;
    private final int weatherID;

    public boolean isHugeTempRange(){
        return (maxTemp - minTemp) >= 15;
    }

    public boolean isSunny(){
        return weatherID==800;
    }

    public float getAverTemp(){
        return (maxTemp+minTemp)/2;
    }
}
