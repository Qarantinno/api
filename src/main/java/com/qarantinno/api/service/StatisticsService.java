package com.qarantinno.api.service;

import com.qarantinno.api.domain.Statistics;
import com.qarantinno.api.domain.criteria.StatisticsCriteria;

public interface StatisticsService {

    /**
     * Calculates statistics by search criteria
     * @param criteria to calculate
     * @return calculated statistics
     */
    Statistics calculateStatistics(StatisticsCriteria criteria);

}
