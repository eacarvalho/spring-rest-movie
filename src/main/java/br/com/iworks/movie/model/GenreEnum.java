package br.com.iworks.movie.model;

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

    private final String description;

    private GenreEnum(String description) {
        this.description = description;
    }

    public static GenreEnum create(String description) {
        GenreEnum[] types = GenreEnum.values();

        for (GenreEnum type : types) {
            if (type.getDescription().equalsIgnoreCase(description)) {
                return type;
            }
        }

        return null;
    }

    public String getDescription() {
        return description;
    }
}