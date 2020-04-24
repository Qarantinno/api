package com.qarantinno.api.domain.criteria;

import com.qarantinno.api.domain.Place;
import com.qarantinno.api.domain.Shot;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.AssertFalse;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
public class StatisticsCriteria {

    private Place.Type place;
    private Place.Modifier placeModifier;
    private Shot.WeekDay weekDay;
    private LocalDateTime moment;

    private LocalTime timeFrom;
    private LocalTime timeTo;

    @AssertFalse
    public boolean isMomentValid() {
        return moment != null && weekDay != null;
    }

}
