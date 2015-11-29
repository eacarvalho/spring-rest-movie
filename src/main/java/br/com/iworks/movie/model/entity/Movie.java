package br.com.iworks.movie.model.entity;

import br.com.iworks.movie.infra.util.JsonDateSerializer;
import br.com.iworks.movie.model.GenreEnum;
import br.com.iworks.movie.model.TypeEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Document(collection = Movie.COLLECTION_NAME)
@Data
@EqualsAndHashCode
@NoArgsConstructor
public class Movie {

    public static final String COLLECTION_NAME = "movies";

    @Id
    private String id;

    private Long code;

    @NotNull(message = "{validation.notnull}")
    private String tittle;
    private String originalTitle;
    private Integer duration;

    @NotNull(message = "{validation.notnull}")
    private TypeEnum type;
    private List<GenreEnum> genres;
    private Date date;

    @NotNull(message = "{validation.notnull}")
    private String plot;
    private String directors;
    private int rating;

    @JsonSerialize(using = JsonDateSerializer.class)
    public Date getDate() {
        return date;
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