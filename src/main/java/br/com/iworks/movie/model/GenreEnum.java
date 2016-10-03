package br.com.iworks.movie.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum GenreEnum {
    ACTION("Action"),
    ADVENTURE("Adventure"),
    ANIMATION("Animation"),
    BIOGRAPHY("Biography"),
    COMEDY("Comedy"),
    CRIME("Crime"),
    DOCUMENTARY("Documentary"),
    DRAMA("Drama"),
    FAMILY("Family"),
    FANTASY("Fantasy"),
    FILM_NOIR("Film-Noir"),
    HISTORY("History"),
    HORROR("Horror"),
    MUSIC("Music"),
    MUSICAL("Musical"),
    MYSTERY("Mystery"),
    ROMANCE("Romance"),
    SCI_FI("Sci-Fi"),
    SPORT("Sport"),
    THRILLER("Thriller"),
    WAR("War"),
    WESTERN("Western");

    private String description;

    private GenreEnum(String description) {
        this.description = description;
    }

    @JsonCreator
    public static GenreEnum create(String description) {
        GenreEnum[] types = GenreEnum.values();

        for (GenreEnum type : types) {
            if (type.getDescription().equalsIgnoreCase(description)) {
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