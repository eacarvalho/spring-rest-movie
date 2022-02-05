package br.com.iworks.movie.omdb.resource;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OmdbApiEpisodeResource {

    @JsonProperty("Title")
    private String title;
    @JsonProperty("Released")
    private String released;
    @JsonProperty("Episode")
    private Integer episode;
    @JsonProperty("imdbRating")
    private String imdbRating;
    @JsonProperty("imdbID")
    private String imdbID;
}