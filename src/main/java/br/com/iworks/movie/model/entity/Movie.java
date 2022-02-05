package br.com.iworks.movie.model.entity;

import br.com.iworks.movie.model.GenreEnum;
import br.com.iworks.movie.model.TypeEnum;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = Movie.COLLECTION_NAME)
@Data
@EqualsAndHashCode
@NoArgsConstructor
@CompoundIndexes({
        @CompoundIndex(name = "code_movie_idx", def = "{'code': 1}", unique = true),
        @CompoundIndex(name = "title_year_movie_idx", def = "{'title': 1, 'year': 1}", unique = true, sparse = true)
})
public class Movie {

    public static final String COLLECTION_NAME = "movies";

    @Id
    private String id;

    private Long code;

    @NotNull(message = "Title must not be null")
    private String title;
    private String originalTitle;
    private String duration;

    @NotNull(message = "Type must not be null")
    private TypeEnum type;
    private List<GenreEnum> genres;
    private LocalDateTime createdDate;
    private LocalDate releasedDate;

    private String year;

    @NotNull(message = "Plot must not be null")
    private String plot;
    private String director;

    @Min(value = 0, message = "Rating must be between 0 and 5")
    @Min(value = 5, message = "Rating must be between 0 and 5")
    private int rating;
    private String imdbRating;
    private String imdbID;

    private String poster;
}