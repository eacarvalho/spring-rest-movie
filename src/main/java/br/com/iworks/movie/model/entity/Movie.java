package br.com.iworks.movie.model.entity;

import br.com.iworks.movie.infra.util.JsonDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.Date;

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
    private String title;
    private String originalTitle;
    private Integer duration;
    private String type;
    private String category;
    private Date date;

    @NotNull(message = "{validation.notnull}")
    private String plot;
    private String directors;
    private int rating;

    @JsonSerialize(using = JsonDateSerializer.class)
    public Date getDate() {
        return date;
    }
}
