package com.qarantinno.api.web.controller;

import com.qarantinno.api.domain.Statistics;
import com.qarantinno.api.domain.criteria.StatisticsCriteria;
import com.qarantinno.api.service.StatisticsService;
import com.qarantinno.api.web.dto.criteria.StatisticsCriteriaDTO;
import com.qarantinno.api.web.dto.StatisticsDTO;
import io.swagger.annotations.Api;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Api("Statistics controller documentation")
@ResponseStatus(HttpStatus.OK)
@RestController
@RequestMapping(value = "/api/v1/stats")
public class StatisticsController {

    private final StatisticsService statisticsService;
    private final Mapper mapper;

    public StatisticsController(@Qualifier("statisticsServiceV2Impl") StatisticsService statisticsService, Mapper mapper) {
        this.statisticsService = statisticsService;
        this.mapper = mapper;
    }

    @GetMapping
    public StatisticsDTO getAll(StatisticsCriteriaDTO criteriaDTO) {
        StatisticsCriteria criteria = mapper.map(criteriaDTO, StatisticsCriteria.class);
        Statistics statistics = statisticsService.calculateStatistics(criteria);
        return mapper.map(statistics, StatisticsDTO.class);
    }
}
