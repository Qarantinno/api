package com.qarantinno.api.domain.criteria;

import com.qarantinno.api.domain.Place;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PlaceCriteria {

    private String name;
    private Place.Type type;
    private Place.Modifier modifier;
    private String address;
    private Double lat;
    private Double lng;

}
