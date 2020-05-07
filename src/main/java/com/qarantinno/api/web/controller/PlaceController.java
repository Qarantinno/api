package com.qarantinno.api.web.controller;

import com.qarantinno.api.domain.Place;
import com.qarantinno.api.domain.Shot;
import com.qarantinno.api.domain.criteria.PlaceCriteria;
import com.qarantinno.api.domain.execption.IllegalOperationException;
import com.qarantinno.api.service.PlaceService;
import com.qarantinno.api.web.dto.PlaceDTO;
import com.qarantinno.api.web.dto.ShotDTO;
import com.qarantinno.api.web.dto.ValidationGroups;
import com.qarantinno.api.web.dto.criteria.PlaceCriteriaDTO;
import io.swagger.annotations.Api;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.groups.Default;

import java.util.List;
import java.util.stream.Collectors;

import static com.qarantinno.api.domain.execption.IllegalOperationException.IllegalOperationExceptionType.ILLEGAL_PLACE_CREATE;
import static com.qarantinno.api.domain.execption.IllegalOperationException.IllegalOperationExceptionType.ILLEGAL_PLACE_GET;
import static com.qarantinno.api.domain.execption.IllegalOperationException.IllegalOperationExceptionType.ILLEGAL_SHOT_CREATE;

@Api("Place controller documentation")
@ResponseStatus(HttpStatus.OK)
@RestController
@RequestMapping(value = "/api/v1/places", produces = MediaType.APPLICATION_JSON_VALUE)
public class PlaceController {

    private final PlaceService placeService;
    private final Mapper mapper;
    private final String clientToken;

    public PlaceController(
            PlaceService placeService,
            Mapper mapper,
            @Value("${app.auth.client-token}") String clientToken
    ) {
        this.placeService = placeService;
        this.mapper = mapper;
        this.clientToken = clientToken;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PlaceDTO create(@RequestBody @Validated PlaceDTO placeDTO, @RequestHeader("client-token") String clientId) {
        // TODO: 4/4/20 add security instead of hardcoded token
        if (!clientToken.equals(clientId)) {
            throw new IllegalOperationException(ILLEGAL_PLACE_CREATE, "Cannot create place: illegal access");
        }
        Place place = mapper.map(placeDTO, Place.class);
        place = placeService.create(place);
        return mapper.map(place, PlaceDTO.class);
    }

    @GetMapping
    public List<PlaceDTO> getAll(PlaceCriteriaDTO placeCriteriaDTO, @RequestHeader("client-token") String clientId) {
        // TODO: 4/4/20 add security instead of hardcoded token
        if (!clientToken.equals(clientId)) {
            throw new IllegalOperationException(ILLEGAL_PLACE_GET, "Cannot get place: illegal access");
        }
        PlaceCriteria criteria = mapper.map(placeCriteriaDTO, PlaceCriteria.class);
        List<Place> places = placeService.retrieveAll(criteria);
        return places.stream()
                     .map(place -> mapper.map(place, PlaceDTO.class))
                     .collect(Collectors.toList());
    }

    @PostMapping("/{id}/shots")
    @ResponseStatus(HttpStatus.CREATED)
    public ShotDTO addShot(
            @RequestBody @Validated({Default.class, ValidationGroups.InternalOnCreate.class}) ShotDTO shotDTO,
            @PathVariable("id") Long id,
            @RequestHeader("client-token") String clientId
    ) {
        // TODO: 4/4/20 add security instead of hardcoded token
        if (!clientToken.equals(clientId)) {
            throw new IllegalOperationException(ILLEGAL_SHOT_CREATE, "Cannot create shot: illegal access");
        }

        Shot shot = mapper.map(shotDTO, Shot.class);
        shot = placeService.addShot(id, shot);
        return mapper.map(shot, ShotDTO.class);
    }
}
