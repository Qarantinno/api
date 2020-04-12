package com.qarantinno.api.persistence.mapper;

import com.qarantinno.api.domain.Place;
import com.qarantinno.api.domain.criteria.PlaceCriteria;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface PlaceMapper {

    void create(Place place);

    Optional<Place> findById(Long id);

    Optional<Place> findByNameAndTypeAndModifier(@Param("name") String name, @Param("type") Place.Type type, @Param("modifier") Place.Modifier modifier);

    List<Place> findAllByCriteria(PlaceCriteria criteria);

    boolean existsById(Long id);

}
