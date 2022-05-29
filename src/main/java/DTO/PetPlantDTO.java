package dto;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PetPlantDTO implements Serializable {
    private long pk;
    private long plantID;
    private long userID;
    private String petName;
    private LocalDate firstMetDay;
    private byte[] petImg;
}
