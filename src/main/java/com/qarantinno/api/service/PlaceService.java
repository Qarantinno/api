package com.qarantinno.api.service;

import com.qarantinno.api.domain.Place;
import com.qarantinno.api.domain.Shot;

public interface PlaceService {

    Place create(Place place);

    Place retrieveById(Long id);

    Place retrieveByNameAndTypeAndModifier(String name, Place.Type type, Place.Modifier modifier);

    boolean existsById(Long id);

    Shot addShot(Shot shot);

    Shot addShot(Long id, Shot shot);

}
