package br.com.iworks.movie.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum TypeEnum {
    MOVIE("Movie"),
    SERIE("Serie"),
    EPISODE("Episode"),
    CARTOONS("Cartoons");

    private String description;

    private TypeEnum(String description) {
        this.description = description;
    }

    @JsonCreator
    public static TypeEnum create(String description) {
        TypeEnum[] types = TypeEnum.values();

        for (TypeEnum type : types) {
            if (description.toLowerCase().contains(type.getDescription().toLowerCase())) {
                return type;
            }
        }

        return null;
    }

    @JsonValue
    public String getDescription() {
        return description;
    }
}