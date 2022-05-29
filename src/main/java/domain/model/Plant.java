package domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Plant {
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
    private byte[] imgBytes;

    public long getPk() {
        return pk;
    }

    public double calculateSimilarity(float lightDemand, int humidity, int growthTp,
                                     int growthSpeed, int mngLevel, float clCode) {

        double dotProduct = 0;
        dotProduct += this.lightDemand*lightDemand;
        dotProduct += this.humidity*humidity;
        dotProduct += this.growthTp*growthTp;
        dotProduct += this.growthSpeed*growthSpeed;
        dotProduct += this.mngLevel*mngLevel;
        dotProduct += this.clCode*clCode;

        double lhsDistance = Math.pow(this.lightDemand, 2);
        lhsDistance += Math.pow(this.humidity, 2);
        lhsDistance += Math.pow(this.growthTp, 2);
        lhsDistance += Math.pow(this.growthSpeed, 2);
        lhsDistance += Math.pow(this.mngLevel, 2);
        lhsDistance += Math.pow(this.clCode, 2);
        lhsDistance = Math.sqrt(lhsDistance);

        double rhsDistance = Math.pow(lightDemand, 2);
        rhsDistance += Math.pow(humidity, 2);
        rhsDistance += Math.pow(growthTp, 2);
        rhsDistance += Math.pow(growthSpeed, 2);
        rhsDistance += Math.pow(mngLevel, 2);
        rhsDistance += Math.pow(clCode, 2);
        rhsDistance = Math.sqrt(rhsDistance);

        return dotProduct/(lhsDistance*rhsDistance);
    }
}
