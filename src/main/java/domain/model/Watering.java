package domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Watering {
    private long wateringPK;
    private long petPlantPK;
    private long userPK;
    private LocalDate wateringDay;
}
