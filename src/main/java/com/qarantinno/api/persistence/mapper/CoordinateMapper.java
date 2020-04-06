package com.qarantinno.api.persistence.mapper;

import com.qarantinno.api.domain.Coordinate;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CoordinateMapper {

    void create(Coordinate coordinate);

}
