package com.interview.oriontekchallenge.model;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class EstatusConverter implements AttributeConverter<Estatus, String> {
    @Override
    public String convertToDatabaseColumn(Estatus attribute) {
        return attribute == null ? null : attribute.getChar();
    }

    @Override
    public Estatus convertToEntityAttribute(String dbData) {
        return (dbData == null) ? null : Stream.of(Estatus.values())
                .filter(s -> s.getChar().equals(dbData))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
