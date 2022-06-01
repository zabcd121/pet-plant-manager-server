package domain.model;

import lombok.*;

@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Plant {
    private long pk;
    private String pltName;             // 식물 이름
    private float lightDemand;         // 광요구도
    private int humidity;            // 습도
    private int growthTp;               // 생육 온도
    private int growthSpeed;         // 성장 속도
    private int mngLevel;            // 관리 수준
    private String clCode;              // 분류 코드(ex: 잎보기 식물)
    private byte[] imgBytes;
    private int waterSpring;
    private int waterSummer;
    private int waterAutumn;
    private int waterWinter;

    public long getPk() {
        return pk;
    }

    public double calculateSimilarity(float lightDemand, int humidity, int growthTp,
                                     int growthSpeed, int mngLevel) {

        double dotProduct = 0;
        dotProduct += this.lightDemand*lightDemand;
        dotProduct += this.humidity*humidity;
        dotProduct += this.growthTp*growthTp;
        dotProduct += this.growthSpeed*growthSpeed;
        dotProduct += this.mngLevel*mngLevel;

        double lhsDistance = Math.pow(this.lightDemand, 2);
        lhsDistance += Math.pow(this.humidity, 2);
        lhsDistance += Math.pow(this.growthTp, 2);
        lhsDistance += Math.pow(this.growthSpeed, 2);
        lhsDistance += Math.pow(this.mngLevel, 2);
        lhsDistance = Math.sqrt(lhsDistance);

        double rhsDistance = Math.pow(lightDemand, 2);
        rhsDistance += Math.pow(humidity, 2);
        rhsDistance += Math.pow(growthTp, 2);
        rhsDistance += Math.pow(growthSpeed, 2);
        rhsDistance += Math.pow(mngLevel, 2);
        rhsDistance = Math.sqrt(rhsDistance);

        return dotProduct/(lhsDistance*rhsDistance);
    }
}
