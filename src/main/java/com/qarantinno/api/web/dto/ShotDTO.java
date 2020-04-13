package com.qarantinno.api.web.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.qarantinno.api.domain.Shot;
import com.qarantinno.api.web.util.deserializer.ToJsonDeserializer;
import com.qarantinno.api.web.util.serializer.FromJsonSerializer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ShotDTO {

    private Long id;

    private Shot.WeekDay weekDay;

    @NotNull
    @Min(value = 0)
    @Max(value = 100)
    private Integer people;

    @NotNull
    private Shot.Source source;

    @JsonDeserialize(using = ToJsonDeserializer.class)
    @JsonSerialize(using = FromJsonSerializer.class)
    private String trackingData;

    @NotNull
    @PastOrPresent
    private OffsetDateTime shotAt;

    @NotNull(groups = ValidationGroups.ExternalOnCreate.class)
    @Valid
    private PlaceDTO place;

    @AssertTrue
    @JsonIgnore
    public boolean isWeekDayValid() {
        return weekDay == null || weekDay.isPlain();
    }

}
