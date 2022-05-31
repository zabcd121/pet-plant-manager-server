package dto;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WateringDTO implements Serializable {
    private long wateringPK;
    private long petPlantPK;
    private LocalDate wateringDay;
}
