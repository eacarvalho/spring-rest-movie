package br.com.iworks.movie.model;

public enum TypeEnum {
    MOVIE("Movie"),
    SERIE("Serie"),
    EPISODE("Episode"),
    CARTOONS("Cartoons");

    private final String description;

    private TypeEnum(String description) {
        this.description = description;
    }

    public static TypeEnum create(String description) {
        TypeEnum[] types = TypeEnum.values();

        for (TypeEnum type : types) {
            if (description != null && description.toLowerCase().contains(type.getDescription().toLowerCase())) {
                return type;
            }
        }

        return null;
    }

    public String getDescription() {
        return description;
    }
}