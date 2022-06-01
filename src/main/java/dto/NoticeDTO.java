package dto;

import lombok.*;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NoticeDTO {
    long pk;
    long targetAccId;
    long targetPetId;
    String content;
    LocalDate noticedTime;

}
