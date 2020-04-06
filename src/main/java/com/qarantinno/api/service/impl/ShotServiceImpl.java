package com.qarantinno.api.service.impl;

import com.qarantinno.api.domain.Shot;
import com.qarantinno.api.domain.criteria.StatisticsCriteria;
import com.qarantinno.api.persistence.mapper.ShotMapper;
import com.qarantinno.api.service.ShotService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ShotServiceImpl implements ShotService {

    private final ShotMapper shotMapper;

    public ShotServiceImpl(ShotMapper shotMapper) {
        this.shotMapper = shotMapper;
    }

    @Override
    @Transactional
    public Shot create(Shot shot, Long placeId) {
        if (shot.getWeekDay() == null && shot.getShotAt() != null) {
            Shot.WeekDay weekDay = Shot.WeekDay.getFrom(shot.getShotAt().getDayOfWeek());
            shot.setWeekDay(weekDay);
        }
        shotMapper.create(shot, placeId);
        return shot;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Shot> retrieveAllByCriteria(StatisticsCriteria criteria) {
        return shotMapper.findAllByCriteria(criteria);
    }

    @Override
    @Transactional(readOnly = true)
    public Integer retrieveCountByCriteria(StatisticsCriteria criteria) {
        return shotMapper.findCountByCriteria(criteria);
    }
}
