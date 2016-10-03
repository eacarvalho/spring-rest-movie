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
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieResource {

    private Long code;

    @NotNull(message = "{validation.notnull}")
    private String title;

    @NotNull(message = "{validation.notnull}")
    private String originalTitle;

    private String duration;

    @NotNull(message = "{validation.notnull}")
    private TypeEnum type;
    private List<GenreEnum> genres;

    @JsonSerialize(using = JsonDateTimeSerializer.class)
    private Date releasedDate;

    @Min(value = 1800, message = "{validation.size}")
    @Max(value = 9999, message = "{validation.size}")
    private Integer year;

    @NotNull(message = "{validation.notnull}")
    private String plot;
    private String director;

    @Min(value = 0, message = "{validation.size}")
    @Max(value = 10, message = "{validation.size}")
    private int rating;

    private String imdbRating;
    private String imdbID;

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