package com.qarantinno.api.persistence.mapper;

import com.qarantinno.api.domain.Place;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

@Mapper
public interface PlaceMapper {

    void create(Place place);

    Optional<Place> findById(Long id);

    Optional<Place> findByNameAndTypeAndModifier(@Param("name") String name, @Param("type") Place.Type type, @Param("modifier") Place.Modifier modifier);

    boolean existsById(Long id);

}
