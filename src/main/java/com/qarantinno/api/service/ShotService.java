package com.qarantinno.api.service;

import com.qarantinno.api.domain.Shot;
import com.qarantinno.api.domain.criteria.StatisticsCriteria;

import java.util.List;

public interface ShotService {

    Shot create(Shot shot, Long placeId);

    List<Shot> retrieveAllByCriteria(StatisticsCriteria criteria);

    Integer retrieveCountByCriteria(StatisticsCriteria criteria);

}
