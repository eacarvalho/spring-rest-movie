package br.com.iworks.movie.gateway.omdb.resource;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OmdbApiResource {

    @JsonProperty("Title")
    private String title;

    @JsonProperty("Year")
    private String year;

    @JsonProperty("Actors")
    private String actors;

    @JsonProperty("Director")
    private String director;

    @JsonProperty("Plot")
    private String plot;

    @JsonProperty("Poster")
    private String poster;

    @JsonProperty("Genre")
    private String genre;

    @JsonProperty("Runtime")
    private String runtime;

    @JsonProperty("imdbRating")
    private String imdbRating;

    @JsonProperty("imdbID")
    private String imdbID;

    @JsonProperty("imdbVotes")
    private String imdbVotes;

    @JsonProperty("Type")
    private String type;
}