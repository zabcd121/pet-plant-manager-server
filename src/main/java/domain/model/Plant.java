package domain.model;

public class Plant {
    private long pk;
    private String pltName;             // 식물 이름
    private String lightDemandCode;     // 광요구도 코드
    private String hdCode;              // 습도 코드
    private int growthTp;               // 생육 온도
    private String waterCycle;          // 물주기
    private String size;                // 크기
    private String growthSpeed;         // 성장 속도
    private String smell;               // 냄새
    private String mngLevel;            // 관리 수준
    private String priceCode;           // 가격대
    private String clCode;              // 분류 코드(ex: 잎보기 식물)
}
