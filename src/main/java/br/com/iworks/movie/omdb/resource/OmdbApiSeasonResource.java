package br.com.iworks.movie.omdb.resource;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OmdbApiSeasonResource {

    @JsonProperty("Title")
    private String title;
    @JsonProperty("Season")
    private String season;
    @JsonProperty("totalSeasons")
    private String totalSeasons;
    @JsonProperty("Episodes")
    private List<OmdbApiEpisodeResource> episodes;
}