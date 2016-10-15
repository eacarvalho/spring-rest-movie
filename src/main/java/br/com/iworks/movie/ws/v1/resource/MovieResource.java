package br.com.iworks.movie.ws.v1.resource;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import br.com.iworks.movie.config.util.JsonDateTimeSerializer;
import br.com.iworks.movie.model.GenreEnum;
import br.com.iworks.movie.model.TypeEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieResource {

    private Long code;

    private String title;
    private String originalTitle;

    private String duration;
    private TypeEnum type;
    private List<GenreEnum> genres;

    @JsonSerialize(using = JsonDateTimeSerializer.class)
    private Date releasedDate;

    @NotNull(message = "{validation.notnull}")
    private String year;

    private String plot;
    private String director;

    @Min(value = 0, message = "{validation.size}")
    @Max(value = 5, message = "{validation.size}")
    private int rating;

    private String imdbRating;
    private String imdbID;
    private String poster;

    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    public void setType(TypeEnum type) {
        this.type = type;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    public TypeEnum getType() {
        return type;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    public List<GenreEnum> getGenres() {
        return genres;
    }
}