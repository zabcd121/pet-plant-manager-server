package dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlantDTO {
    private long pk;
    private String pltName;             // 식물 이름
    private float lightDemand;         // 광요구도
    private int humidity;            // 습도
    private int growthTp;               // 생육 온도
    //    private int sprWaterCycle;       // 봄 물주기
//    private int sumWaterCycle;       // 여름 물주기
//    private int fallWaterCycle;      // 가을 물주기
//    private int winWaterCycle;       // 겨울물주기
    private int growthSpeed;         // 성장 속도
    private int mngLevel;            // 관리 수준
    private float clCode;              // 분류 코드(ex: 잎보기 식물)

}
