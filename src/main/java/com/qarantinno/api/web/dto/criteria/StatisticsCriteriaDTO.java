package com.qarantinno.api.web.dto.criteria;

import com.qarantinno.api.domain.Place;
import com.qarantinno.api.domain.Shot;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class StatisticsCriteriaDTO {

    private Place.Type place;
    private Place.Modifier placeModifier;
    private Shot.WeekDay weekDay;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime moment;

}
