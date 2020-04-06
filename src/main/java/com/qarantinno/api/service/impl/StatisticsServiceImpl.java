package com.qarantinno.api.service.impl;

import com.qarantinno.api.domain.Shot;
import com.qarantinno.api.domain.Statistics;
import com.qarantinno.api.domain.criteria.StatisticsCriteria;
import com.qarantinno.api.service.ShotService;
import com.qarantinno.api.service.StatisticsService;
import com.qarantinno.api.service.utils.Algorithm;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    private final ShotService shotService;

    public StatisticsServiceImpl(ShotService shotService) {
        this.shotService = shotService;
    }

    @Override
    @Transactional(readOnly = true)
    public Statistics calculateStatistics(StatisticsCriteria criteria) {
        adjustStatisticsCriteria(criteria);
        List<Shot> shots = shotService.retrieveAllByCriteria(criteria);
        return collectStatistics(shots);
    }

    private static Statistics collectStatistics(List<Shot> shots) {
        Map<LocalTime, List<Shot>> timedShots = collectToTimeMap(shots);
        Map<LocalTime, Double> processedShots = timedShots.entrySet().stream()
                                                          .map(timedShotEntry -> {
                                                              double algorithmValue = Algorithm.median(collectShotAt(timedShotEntry.getValue()));
                                                              return new AbstractMap.SimpleEntry<>(timedShotEntry.getKey(), algorithmValue);
                                                          }).collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue));
        List<Statistics.StatisticsEntry> hourStatisticsEntry = processedShots.entrySet().stream()
                                                                             .filter(processedEntry -> processedEntry.getKey().getMinute() == 0)
                                                                             .map(processedEntry -> new Statistics.StatisticsEntry(processedEntry.getKey().toString(), BigDecimal.valueOf(processedEntry.getValue())))
                                                                             .collect(Collectors.toList());
        List<Statistics.StatisticsEntry> halfHourStatisticsEntry = processedShots.entrySet().stream()
                                                                                 .map(processedEntry -> new Statistics.StatisticsEntry(processedEntry.getKey().toString(), BigDecimal.valueOf(processedEntry.getValue())))
                                                                                 .collect(Collectors.toList());
        Collections.sort(hourStatisticsEntry);
        Collections.sort(halfHourStatisticsEntry);
        return new Statistics(hourStatisticsEntry, halfHourStatisticsEntry);
    }

    private static List<Integer> collectShotAt(List<Shot> shots) {
        return shots.stream()
                    .map(Shot::getPeople)
                    .collect(Collectors.toList());
    }

    private static Map<LocalTime, List<Shot>> collectToTimeMap(List<Shot> shots) {
        int range = 30;
        final LocalTime startEndTime = LocalTime.parse("00:00");
        LocalTime iterateTime = null;
        Map<LocalTime, List<Shot>> timeLinedShots = new HashMap<>();
        while (iterateTime == null || iterateTime.isAfter(startEndTime)) {
            if (iterateTime == null) {
                iterateTime = LocalTime.from(startEndTime);
            }

            timeLinedShots.putIfAbsent(iterateTime, new ArrayList<>());

            for (Shot shot : shots) {
                if (isTimeInRange(range, iterateTime, shot.getShotAt().toLocalTime())) {
                    timeLinedShots.get(iterateTime).add(shot);
                }
            }

            iterateTime = iterateTime.plusMinutes(range);
        }
        return timeLinedShots;
    }

    private static boolean isTimeInRange(int range, LocalTime previousTime, LocalTime time) {
        LocalTime nextTime = previousTime.plusMinutes(range);
        return (time.equals(previousTime) || time.isAfter(previousTime)) && time.isBefore(nextTime);
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
}
