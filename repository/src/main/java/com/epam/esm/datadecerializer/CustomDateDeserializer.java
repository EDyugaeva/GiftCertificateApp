package com.epam.esm.datadecerializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CustomDateDeserializer extends StdDeserializer<LocalDateTime> {
    private DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

    public CustomDateDeserializer() {
        this(null);
    }

    public CustomDateDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public LocalDateTime deserialize(JsonParser jsonparser,
                                     DeserializationContext context)
            throws IOException {
        String date = jsonparser.getText();
        return LocalDateTime.parse(date, formatter);
    }
}

