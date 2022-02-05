package br.com.iworks.movie.model.resource;

import br.com.iworks.movie.model.GenreEnum;
import br.com.iworks.movie.model.TypeEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import java.time.LocalDate;
import java.util.List;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieResource {

    @JsonProperty("code")
    private Long code;

    @JsonProperty("title")
    private String title;

    @JsonProperty("originalTitle")
    private String originalTitle;

    @JsonProperty("duration")
    private String duration;

    @JsonProperty("type")
    private TypeEnum type;

    @JsonProperty("genres")
    private List<GenreEnum> genres;

    @JsonProperty("releasedDate")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate releasedDate;

    @JsonProperty("year")
    private String year;

    @JsonProperty("plot")
    private String plot;

    @JsonProperty("director")
    private String director;

    @Min(value = 0, message = "Rating must be between 0 and 5")
    @Max(value = 5, message = "Rating must be between 0 and 5")
    @JsonProperty("rating")
    private int rating;

    @JsonProperty("imdbRating")
    private String imdbRating;

    @JsonProperty("imdbID")
    private String imdbID;

    @JsonProperty("poster")
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