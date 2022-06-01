package dto;

import lombok.*;

import java.io.File;
import java.time.LocalDate;
import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostDTO {

    private long pk;
    private long petPk;
    private String title;
    private String content;
    private LocalDate postedTime;

    @Builder.Default
    private byte[] imgBytes = new byte[0];
}
