package dto;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlantDTO implements Serializable {
    private long pk;
    private String pltName;             // 식물 이름
    private float lightDemand;         // 광요구도
    private int humidity;            // 습도
    private int growthTp;               // 생육 온도
    private int growthSpeed;         // 성장 속도
    private int mngLevel;            // 관리 수준
    private String clCode;              // 분류 코드(ex: 잎보기 식물)
    private int waterSpring;
    private int waterSummer;
    private int waterAutumn;
    private int waterWinter;

    @Builder.Default
    private byte[] imgBytes = new byte[0];

    public int getSeasonWaterCycle(){
        int curMonth = LocalDate.now().getMonth().getValue();

        switch(curMonth){
            case 12:
            case 1:
            case 2:
                return waterWinter;
            case 3:
            case 4:
            case 5:
                return waterSpring;
            case 6:
            case 7:
            case 8:
                return waterSummer;
            case 9:
            case 10:
            case 11:
                return waterAutumn;
        }

        return 0;

    }
}