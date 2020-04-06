package com.qarantinno.api.service;

import com.qarantinno.api.domain.Statistics;
import com.qarantinno.api.domain.criteria.StatisticsCriteria;

public interface StatisticsService {

    Statistics calculateStatistics(StatisticsCriteria criteria);

}
