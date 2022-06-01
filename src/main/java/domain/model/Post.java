package domain.model;

import lombok.*;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Post {
    private long pk;
    private long petPk;
    private String title;
    private String content;
    private LocalDateTime postedTime;
    private byte[] imgBytes;
}
