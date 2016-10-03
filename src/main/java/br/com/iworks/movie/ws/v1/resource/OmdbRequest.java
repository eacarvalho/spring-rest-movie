package br.com.iworks.movie.ws.v1.resource;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OmdbRequest {

    private String title;
    private Integer year;
    private String imdbID;

    @Min(value = 0, message = "{validation.size}")
    @Max(value = 10, message = "{validation.size}")
    private Integer rating;
}