package dto;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WateringDTO implements Serializable, Comparable<WateringDTO> {
    private long wateringPK;
    private long petPlantPK;
    private long userPK;
    private LocalDate wateringDay;

    @Override
    public int compareTo(WateringDTO dto) {

        if(wateringDay.isAfter(dto.getWateringDay())) return 1;
        else if(wateringDay.isEqual(dto.getWateringDay())) return 0;
        else return -1;

    }
}
