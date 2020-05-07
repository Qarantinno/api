package com.qarantinno.api.web.controller;

import com.qarantinno.api.domain.Statistics;
import com.qarantinno.api.domain.criteria.StatisticsCriteria;
import com.qarantinno.api.domain.execption.IllegalOperationException;
import com.qarantinno.api.service.StatisticsService;
import com.qarantinno.api.web.dto.criteria.StatisticsCriteriaDTO;
import com.qarantinno.api.web.dto.StatisticsDTO;
import io.swagger.annotations.Api;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static com.qarantinno.api.domain.execption.IllegalOperationException.IllegalOperationExceptionType.ILLEGAL_STATISTICS_GET;

@Api("Statistics controller documentation")
@ResponseStatus(HttpStatus.OK)
@RestController
@RequestMapping(value = "/api/v1/stats")
public class StatisticsController {

    private final StatisticsService statisticsService;
    private final String clientUserToken;
    private final Mapper mapper;

    public StatisticsController(
            @Qualifier("statisticsServiceV2Impl") StatisticsService statisticsService,
            @Value("${app.auth.client-user-token}") String clientUserToken,
            Mapper mapper
    ) {
        this.statisticsService = statisticsService;
        this.clientUserToken = clientUserToken;
        this.mapper = mapper;
    }

    @GetMapping
    public StatisticsDTO getAll(StatisticsCriteriaDTO criteriaDTO, @RequestHeader("client-user-token") String clientUserToken) {
        if (!this.clientUserToken.equals(clientUserToken)) {
            throw new IllegalOperationException(ILLEGAL_STATISTICS_GET, "Cannot get statistics: illegal access");
        }
        StatisticsCriteria criteria = mapper.map(criteriaDTO, StatisticsCriteria.class);
        Statistics statistics = statisticsService.calculateStatistics(criteria);
        return mapper.map(statistics, StatisticsDTO.class);
    }
}
