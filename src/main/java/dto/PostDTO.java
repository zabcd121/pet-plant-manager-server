package dto;

import lombok.*;

import java.time.LocalDate;


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
    private LocalDate postedDate;

    @Builder.Default
    private byte[] imgBytes = new byte[0];
}
