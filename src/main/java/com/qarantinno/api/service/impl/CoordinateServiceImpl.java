package com.qarantinno.api.service.impl;

import com.qarantinno.api.domain.Coordinate;
import com.qarantinno.api.persistence.mapper.CoordinateMapper;
import com.qarantinno.api.service.CoordinateService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CoordinateServiceImpl implements CoordinateService {

    private final CoordinateMapper coordinateMapper;

    public CoordinateServiceImpl(CoordinateMapper coordinateMapper) {
        this.coordinateMapper = coordinateMapper;
    }

    @Override
    @Transactional
    public Coordinate create(Coordinate coordinate) {
        coordinateMapper.create(coordinate);
        return coordinate;
    }
}
