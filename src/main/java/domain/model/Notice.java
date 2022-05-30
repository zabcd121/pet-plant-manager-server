package domain.model;

import lombok.*;

import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Notice {
    long targetAccId;

    long targetPetId;
    String message;
    Date noticedTime;




}