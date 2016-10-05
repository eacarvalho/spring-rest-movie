package br.com.iworks.movie.ws.v1.resource;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(of = {"title", "number"})
@NoArgsConstructor
public class EpisodeResource {

    private String title;
    private String released;
    private Integer number;
    private String imdbRating;
    private String imdbID;

    @Min(value = 0, message = "{validation.size}")
    @Max(value = 5, message = "{validation.size}")
    private int rating;

    private boolean watched;
}