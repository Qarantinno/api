package com.qarantinno.api.web.dto;

import com.qarantinno.api.domain.Statistics;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class StatisticsDTO {

    private List<Statistics.StatisticsEntry> hours;
    private List<Statistics.StatisticsEntry> halves;

}
