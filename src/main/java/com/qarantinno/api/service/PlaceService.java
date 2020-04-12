package com.qarantinno.api.service;

import com.qarantinno.api.domain.Place;
import com.qarantinno.api.domain.Shot;
import com.qarantinno.api.domain.criteria.PlaceCriteria;

import java.util.List;

public interface PlaceService {

    Place create(Place place);

    Place retrieveById(Long id);

    Place retrieveByNameAndTypeAndModifier(String name, Place.Type type, Place.Modifier modifier);

    List<Place> retrieveAll(PlaceCriteria criteria);

    boolean existsById(Long id);

    Shot addShot(Shot shot);

    Shot addShot(Long id, Shot shot);

}
