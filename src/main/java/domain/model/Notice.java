package domain.model;

import lombok.*;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Notice {
    private long pk;
    private long targetAccId;
    private long targetPetId;
    private String targetPetName;
    private String content;
    private LocalDate noticedTime;

}