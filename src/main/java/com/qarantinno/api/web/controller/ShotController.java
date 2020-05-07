package com.qarantinno.api.web.controller;

import com.qarantinno.api.domain.Shot;
import com.qarantinno.api.domain.execption.IllegalOperationException;
import com.qarantinno.api.service.PlaceService;
import com.qarantinno.api.web.dto.ShotDTO;
import com.qarantinno.api.web.dto.ValidationGroups;
import io.swagger.annotations.Api;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static com.qarantinno.api.domain.execption.IllegalOperationException.IllegalOperationExceptionType.ILLEGAL_SHOT_CREATE;

@Api("Shot controller documentation")
@ResponseStatus(HttpStatus.OK)
@RestController
@RequestMapping(value = "/api/v1/shots", produces = MediaType.APPLICATION_JSON_VALUE)
public class ShotController {

    private final PlaceService placeService;
    private final String clientUserToken;
    private final Mapper mapper;

    public ShotController(
            PlaceService placeService,
            @Value("${app.auth.client-user-token}") String clientUserToken,
            Mapper mapper
    ) {
        this.placeService = placeService;
        this.clientUserToken = clientUserToken;
        this.mapper = mapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ShotDTO create(@RequestBody @Validated(ValidationGroups.ExternalOnCreate.class) ShotDTO shotDTO, @RequestHeader("client-token") String clientUserToken) {
        if (!this.clientUserToken.equals(clientUserToken)) {
            throw new IllegalOperationException(ILLEGAL_SHOT_CREATE, "Cannot create shot: illegal access");
        }
        Shot shot = mapper.map(shotDTO, Shot.class);
        shot = placeService.addShot(shot);
        return mapper.map(shot, ShotDTO.class);
    }
}
