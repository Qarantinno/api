package com.qarantinno.api.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Coordinate {

    private Long id;
    private Double lat;
    private Double lng;

}
