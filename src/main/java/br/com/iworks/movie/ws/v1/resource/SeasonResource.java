package br.com.iworks.movie.ws.v1.resource;

import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SeasonResource {

    private String title;

    @NotNull(message = "{validation.notnull}")
    private Integer season;
    private String totalSeasons;

    @Min(value = 0, message = "{validation.size}")
    @Max(value = 5, message = "{validation.size}")
    private Integer rating;

    @JsonProperty("episodes")
    private List<EpisodeResource> episodes;
}