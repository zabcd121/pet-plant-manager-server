package dto;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NoticeDTO implements Serializable {
    private long pk;
    private long targetAccId;
    private long targetPetId;
    private String targetPetName;
    private String content;
    private LocalDate noticedTime;

}
