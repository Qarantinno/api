package com.qarantinno.api.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Statistics {

    private List<StatisticsEntry> hours;
    private List<StatisticsEntry> halves;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StatisticsEntry implements Comparable<StatisticsEntry> {
        private String name;
        private BigDecimal value;

        @Override
        public int compareTo(StatisticsEntry that) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            LocalTime thisTime = LocalTime.from(formatter.parse(name));
            LocalTime thatTime = LocalTime.from(formatter.parse(that.name));
            if (thisTime.isAfter(thatTime)) {
                return 1;
            } else if (thisTime.isBefore(thatTime)) {
                return -1;
            }
            return 0;
        }
    }
}
