package domain.model;

import dto.WateringDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.Period;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Watering implements Comparable<Watering> {
    private long wateringPK;
    private long petPlantPK;
    private long userPK;
    private LocalDate wateringDay;

    public int getWateredDays(){
        return Period.between(wateringDay, LocalDate.now()).getDays();
    }
    @Override
    public int compareTo(Watering watering) {

        if(wateringDay.isAfter(wateringDay)) return 1;
        else if(wateringDay.isEqual(wateringDay)) return 0;
        else return -1;

    }
}
