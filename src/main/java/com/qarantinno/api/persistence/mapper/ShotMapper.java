package com.qarantinno.api.persistence.mapper;

import com.qarantinno.api.domain.Shot;
import com.qarantinno.api.domain.criteria.StatisticsCriteria;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ShotMapper {

    void create(@Param("shot")Shot shot, @Param("placeId") Long placeId);

    List<Shot> findAllByCriteria(StatisticsCriteria criteria);

    Integer findCountByCriteria(StatisticsCriteria criteria);

}
