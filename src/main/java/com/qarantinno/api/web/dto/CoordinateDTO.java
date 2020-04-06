package com.qarantinno.api.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CoordinateDTO {

    private Long id;

    @PositiveOrZero
    @NotNull
    private Double lat;

    @PositiveOrZero
    @NotNull
    private Double lng;

}
