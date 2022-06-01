package domain.model;

import lombok.*;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Diary {
    private long pk;
    private long petPlantPK;
    private long userPK;
    private String title;
    private String content;
    private LocalDate date;
    private byte[] diaryImg;
}
