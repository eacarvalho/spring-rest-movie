package br.com.iworks.movie.model.entity;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(of = {"title", "number"})
@NoArgsConstructor
public class Episode {

    private String title;
    private String released;
    @NotNull(message = "{validation.notnull}")
    private Integer number;
    private String imdbRating;
    private String imdbID;

    @Min(value = 0, message = "{validation.size}")
    @Max(value = 5, message = "{validation.size}")
    private int rating;

    private boolean watched;
}