package dto;

import lombok.*;

import java.io.File;
import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostDTO {

    private long pk;
    private String petPk;
    private String title;
    private String content;
    private Date postedTime;
    private File photo;
}
