package domain.model;

import lombok.*;

import java.time.LocalDate;

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
    private LocalDate postedDate;
    private byte[] imgBytes;
}
