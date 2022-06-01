package dto;

import lombok.*;

import java.time.LocalDate;
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DiaryDTO {

    private long pk;
    private long petPlantPK;
    private long userPK;
    private String title;
    private String content;
    private LocalDate date;

    @Builder.Default
    private byte[] diaryImg = new byte[0];
}
