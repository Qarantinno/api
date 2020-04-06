package com.qarantinno.api.web.util.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.qarantinno.api.domain.Place;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.util.Arrays;

@JsonComponent
public class ModifierDeserializer extends JsonDeserializer<Place.Modifier> {

    @Override
    public Place.Modifier deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        String value = p.getText();
        return Arrays.stream(Place.Modifier.values())
                     .filter(modifier -> modifier.getDisplayName() != null && modifier.getDisplayName().equals(value))
                     .findFirst()
                     .orElse(null);
    }
}
