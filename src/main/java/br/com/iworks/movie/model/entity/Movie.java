package br.com.iworks.movie.model.entity;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import br.com.iworks.movie.config.util.JsonDateTimeSerializer;
import br.com.iworks.movie.model.GenreEnum;
import br.com.iworks.movie.model.TypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Document(collection = Movie.COLLECTION_NAME)
@Data
@EqualsAndHashCode
@NoArgsConstructor
@CompoundIndexes({
        @CompoundIndex(name = "code_movie_idx", def = "{'code': 1}", unique = true),
        @CompoundIndex(name = "title_year_movie_idx", def = "{'title': 1, 'year': 1}", unique = true, sparse = true)
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class Movie {

    public static final String COLLECTION_NAME = "movies";

    @Id
    @JsonIgnore
    private String id;

    private Long code;

    @NotNull(message = "{validation.notnull}")
    private String title;
    private String originalTitle;

    private String duration;

    @NotNull(message = "{validation.notnull}")
    private TypeEnum type;
    private List<GenreEnum> genres;
    private Date registrationDate;
    private Date releasedDate;

    @Max(value = 9999, message = "{validation.size}")
    @Min(value = 1800, message = "{validation.size}")
    private Integer year;

    @NotNull(message = "{validation.notnull}")
    private String plot;
    private String director;
    private int rating;
    private String imdbRating;
    private String imdbID;

    @JsonSerialize(using = JsonDateTimeSerializer.class)
    public Date getReleasedDate() {
        return releasedDate;
    }

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