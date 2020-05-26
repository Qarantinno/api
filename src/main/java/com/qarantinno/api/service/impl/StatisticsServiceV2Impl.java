package com.qarantinno.api.service.impl;

import com.qarantinno.api.domain.Shot;
import com.qarantinno.api.domain.Statistics;
import com.qarantinno.api.domain.criteria.StatisticsCriteria;
import com.qarantinno.api.service.ShotService;
import com.qarantinno.api.service.StatisticsService;
import com.qarantinno.api.service.utils.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatisticsServiceV2Impl implements StatisticsService {

    private final ShotService shotService;
    private static final List<LocalTime> timeSlots = collectTimeSlots(30);

    @Override
    @Transactional(readOnly = true)
    public Statistics calculateStatistics(StatisticsCriteria criteria) {
        Statistics statistics = emptyStatistics();

        // collects statistics by hours
        statistics.getHours().forEach(hourEntry -> {
            LocalTime fromTime = LocalTime.parse(hourEntry.getName());
            LocalTime toTime = fromTime.plusMinutes(60);
            BigDecimal value = retrieveStatisticsInRange(criteria, fromTime, toTime);
            hourEntry.setValue(value);
        });

        // collect statistics by half hours
        statistics.getHalves().forEach(hourEntry -> {
            LocalTime fromTime = LocalTime.parse(hourEntry.getName());
            LocalTime toTime = fromTime.plusMinutes(30);
            BigDecimal value = retrieveStatisticsInRange(criteria, fromTime, toTime);
            hourEntry.setValue(value);
        });

        return statistics;
    }

    /**
     * Retrieves shots in range and collect statistics using an algorithm
     * @param criteria to search
     * @param fromTime from range
     * @param toTime to range
     * @return calculated statistics value
     */
    private BigDecimal retrieveStatisticsInRange(StatisticsCriteria criteria, LocalTime fromTime, LocalTime toTime) {
        criteria.setTimeFrom(fromTime);
        criteria.setTimeTo(toTime);

        adjustStatisticsCriteria(criteria);

        List<Shot> shots = shotService.retrieveAllByCriteria(criteria);
        List<Integer> peopleCount = shots.stream()
                                         .map(Shot::getPeople)
                                         .collect(Collectors.toList());
        double value = Algorithm.median(peopleCount, Algorithm.Precondition.NIGHT_TIME);
        return new BigDecimal(value);
    }

    /**
     * Creates an empty statistics object with pre-filled times and empty values
     * @return created statistics
     */
    private static Statistics emptyStatistics() {
        List<Statistics.StatisticsEntry> hourEntries = statisticsEntriesByMinuteValue(0);
        List<Statistics.StatisticsEntry> halfEntries = statisticsEntriesByMinuteValue(0, 30);

        return new Statistics(hourEntries, halfEntries);
    }

    private static List<Statistics.StatisticsEntry> statisticsEntriesByMinuteValue(int... minutes) {
        return timeSlots.stream()
                        .filter(timeSlot -> Arrays.stream(minutes).anyMatch(minute -> timeSlot.getMinute() == minute))
                        .map(timeSlot -> new Statistics.StatisticsEntry(timeSlot.toString()))
                        .collect(Collectors.toList());
    }

    /**
     * Collects time slots from 00:00 to 23:30 included with range
     * @return collected slots
     */
    private static List<LocalTime> collectTimeSlots(int rangeInMinutes) {
        List<LocalTime> result = new ArrayList<>();

        final LocalTime startEndTime = LocalTime.parse("00:00");
        LocalTime iterateTime = null;

        while (iterateTime == null || iterateTime.isAfter(startEndTime)) {
            if (iterateTime == null) {
                iterateTime = LocalTime.from(startEndTime);
            }

            result.add(LocalTime.from(iterateTime));

            iterateTime = iterateTime.plusMinutes(rangeInMinutes);
        }

        return result;
    }

    /**
     * Adjusts search criteria by result count: if result count < 5 - minimize criteria area
     * @param criteria criteria to adjust
     */
    private void adjustStatisticsCriteria(StatisticsCriteria criteria) {
        int minResultCount = 5;
        for (Consumer<StatisticsCriteria> statisticsCriteriaAction : ADJUST_STATISTICS_ACTIONS) {
            int shotsCount = shotService.retrieveCountByCriteria(criteria);
            if (shotsCount < minResultCount) {
                statisticsCriteriaAction.accept(criteria);
            } else {
                break;
            }
        }
    }

    private final List<Consumer<StatisticsCriteria>> ADJUST_STATISTICS_ACTIONS = new ArrayList<>() {
        {
            add(criteria -> {
                if (criteria.getWeekDay() == null && criteria.getMoment() != null) {
                    criteria.setWeekDay(Shot.WeekDay.getFrom(criteria.getMoment().getDayOfWeek()));
                }
            });
            add(criteria -> {
                criteria.setWeekDay(null);
                criteria.setMoment(null);
            });
        }
    };
}
