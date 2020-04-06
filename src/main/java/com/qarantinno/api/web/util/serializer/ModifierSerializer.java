package com.qarantinno.api.web.util.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.qarantinno.api.domain.Place;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

@JsonComponent
public class ModifierSerializer extends JsonSerializer<Place.Modifier> {

    @Override
    public void serialize(Place.Modifier modifier, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeObject(modifier.getDisplayName());
    }
}
