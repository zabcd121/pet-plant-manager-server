package domain.model;

import lombok.*;

import java.time.LocalDate;

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

    private static double[] TEMP_MAP = {
            12.5, 18, 23, 28
    };

    public long getPk() {
        return pk;
    }

    public boolean checkMaxTemp(double temp){
        return temp>=TEMP_MAP[growthTp];
    }

    public boolean checkMinTemp(double temp){
        return temp<=TEMP_MAP[growthTp];
    }

    public double calculateSimilarity(float lightDemand, int humidity, int growthTp,
                                      int growthSpeed, int mngLevel, String classCode, int waterCycle) {

        double dotProduct = 0;
        dotProduct += this.lightDemand*lightDemand;
        dotProduct += this.humidity*humidity;
        dotProduct += this.growthTp*growthTp;
        dotProduct += this.growthSpeed*growthSpeed;
        dotProduct += this.mngLevel*mngLevel;
        dotProduct += getSeasonWaterCycle()*waterCycle;

        double lhsDistance = Math.pow(this.lightDemand, 2);
        lhsDistance += Math.pow(this.humidity, 2);
        lhsDistance += Math.pow(this.growthTp, 2);
        lhsDistance += Math.pow(this.growthSpeed, 2);
        lhsDistance += Math.pow(this.mngLevel, 2);
        lhsDistance += Math.pow(getSeasonWaterCycle(), 2);
        lhsDistance = Math.sqrt(lhsDistance);

        double rhsDistance = Math.pow(lightDemand, 2);
        rhsDistance += Math.pow(humidity, 2);
        rhsDistance += Math.pow(growthTp, 2);
        rhsDistance += Math.pow(growthSpeed, 2);
        rhsDistance += Math.pow(mngLevel, 2);
        rhsDistance += Math.pow(waterCycle, 2);
        rhsDistance = Math.sqrt(rhsDistance);


        double s = dotProduct/(lhsDistance*rhsDistance);
        s += getClassCodePoint(classCode);
        System.out.println("s = " + s);
        return s;
//        return dotProduct/(lhsDistance*rhsDistance);
    }

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

    private double getClassCodePoint(String classCode){
        double point = 0;
        for(int i=0; i<classCode.length(); i++){
            if(clCode.charAt(i)==classCode.charAt(i)){
                point += 0.05;
            }
        }

        return point;
    }
}