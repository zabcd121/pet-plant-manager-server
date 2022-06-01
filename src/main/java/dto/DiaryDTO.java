package dto;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DiaryDTO implements Serializable, Comparable<DiaryDTO>  {

    private long pk;
    private long petPlantPK;
    private long userPK;
    private String title;
    private String content;
    private LocalDate date;

    @Builder.Default
    private byte[] diaryImg = new byte[0];

    @Override
    public int compareTo(DiaryDTO dto) {
        if(date.isAfter(dto.getDate())) return 1;
        else if(date.isEqual(dto.getDate())) return 0;
        else return -1;

    }
}