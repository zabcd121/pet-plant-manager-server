package dto;

import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PetPlantDTO {
    private long pk;
    private long plantID;
    private long userID;
    private String petName;
    private LocalDate firstMetDay;
    private Byte[] petImg;
}
