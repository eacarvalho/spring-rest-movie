package br.com.iworks.movie.ws.v1.resource;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode
@NoArgsConstructor
public class EpisodeResource {

    private String title;
    private String released;
    private String episode;
    private String imdbRating;
    private String imdbID;
}