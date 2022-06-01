package dto;

import lombok.*;

import java.io.Serializable;

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
}