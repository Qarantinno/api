package com.qarantinno.api.web.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.qarantinno.api.domain.Place;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlaceDTO {

    private Long id;

    @NotEmpty(groups = ValidationGroups.InternalOnCreate.class)
    private String name;

    @NotNull
    private Place.Type type;

    @NotNull
    private Place.Modifier modifier;

    @NotEmpty(groups = ValidationGroups.InternalOnCreate.class)
    private String address;

    @Valid
    @NotNull(groups = ValidationGroups.InternalOnCreate.class)
    private CoordinateDTO coordinate;

    @AssertTrue
    @JsonIgnore
    public boolean isTypeValid() {
        return type.isPlain();
    }

    @AssertTrue
    @JsonIgnore
    public boolean isModifierValid() {
        return modifier.isPlain();
    }

}
