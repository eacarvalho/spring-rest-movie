package br.com.iworks.movie.model.entity;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode
@NoArgsConstructor
public class Episode {

    private String title;
    private String released;
    @NotNull(message = "{validation.notnull}")
    private String episode;
    private String imdbRating;
    private String imdbID;
}