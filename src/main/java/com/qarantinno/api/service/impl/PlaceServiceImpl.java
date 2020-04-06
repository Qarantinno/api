package com.qarantinno.api.service.impl;

import com.qarantinno.api.domain.Coordinate;
import com.qarantinno.api.domain.Place;
import com.qarantinno.api.domain.Shot;
import com.qarantinno.api.domain.execption.ResourceNotFoundException;
import com.qarantinno.api.persistence.mapper.PlaceMapper;
import com.qarantinno.api.service.CoordinateService;
import com.qarantinno.api.service.PlaceService;
import com.qarantinno.api.service.ShotService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.qarantinno.api.domain.execption.ResourceNotFoundException.ResourceNotFoundExceptionType.PLACE_NOT_FOUND;

@Service
public class PlaceServiceImpl implements PlaceService {

    private static final String ERR_MSG_PLACE_NOT_EXISTS_BY_ID = "Place with id '%d' does not exist";
    private static final String ERR_MSG_PLACE_NOT_EXISTS_BY_NAME_TYPE_MODIFIER = "Place with name '%s', type '%s' and modifier '%s' does not exist";

    private final PlaceMapper placeMapper;
    private final CoordinateService coordinateService;
    private final ShotService shotService;

    public PlaceServiceImpl(PlaceMapper placeMapper, CoordinateService coordinateService, ShotService shotService) {
        this.placeMapper = placeMapper;
        this.coordinateService = coordinateService;
        this.shotService = shotService;
    }

    @Override
    @Transactional
    public Place create(Place place) {
        Coordinate coordinate = coordinateService.create(place.getCoordinate());
        place.setCoordinate(coordinate);
        placeMapper.create(place);
        return place;
    }

    @Override
    @Transactional(readOnly = true)
    public Place retrieveById(Long id) {
        return placeMapper.findById(id)
                          .orElseThrow(() -> new ResourceNotFoundException(PLACE_NOT_FOUND, String.format(ERR_MSG_PLACE_NOT_EXISTS_BY_ID, id)));
    }

    @Override
    @Transactional(readOnly = true)
    public Place retrieveByNameAndTypeAndModifier(String name, Place.Type type, Place.Modifier modifier) {
        return placeMapper.findByNameAndTypeAndModifier(name, type, modifier)
                          .orElseThrow(() -> new ResourceNotFoundException(PLACE_NOT_FOUND, String.format(ERR_MSG_PLACE_NOT_EXISTS_BY_NAME_TYPE_MODIFIER, name, type, modifier)));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return placeMapper.existsById(id);
    }

    @Override
    @Transactional
    public Shot addShot(Shot shot) {
        Place place = retrieveByNameAndTypeAndModifier(Shot.getUNNAMED(), shot.getPlace().getType(), shot.getPlace().getModifier());
        shot = shotService.create(shot, place.getId());
        return shot;
    }

    @Override
    @Transactional
    public Shot addShot(Long id, Shot shot) {
        boolean exists = existsById(id);
        if (!exists) {
            throw new ResourceNotFoundException(PLACE_NOT_FOUND, String.format(ERR_MSG_PLACE_NOT_EXISTS_BY_ID, id));
        }
        shot = shotService.create(shot, id);
        return shot;
    }
}
