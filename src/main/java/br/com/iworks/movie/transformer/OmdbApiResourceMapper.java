package br.com.iworks.movie.transformer;

import static org.springframework.util.ObjectUtils.isEmpty;

import br.com.iworks.movie.model.GenreEnum;
import br.com.iworks.movie.model.TypeEnum;
import br.com.iworks.movie.model.entity.Movie;
import br.com.iworks.movie.omdb.resource.OmdbApiResource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OmdbApiResourceMapper {

    public Movie toModel(OmdbApiResource resource) {
        if (resource == null || isEmpty(resource.getTitle())) {
            throw new IllegalArgumentException("Movie not found");
        }

        Movie movie = new Movie();

        movie.setTitle(resource.getTitle());
        movie.setOriginalTitle(resource.getTitle());
        movie.setDuration(resource.getRuntime());
        movie.setType(TypeEnum.create(resource.getType()));
        movie.setPlot(resource.getPlot());
        movie.setImdbRating(resource.getImdbRating());
        movie.setImdbID(resource.getImdbID());
        movie.setDirector(resource.getDirector());
        movie.setReleasedDate(formatReleaseDate(resource.getReleased()));
        movie.setPoster(resource.getPoster());
        movie.setYear(resource.getYear());

        if (!isEmpty(resource.getGenre())) {
            String[] genres = resource.getGenre().split(",");
            List<GenreEnum> listGenre = new ArrayList<>();

            for (String genre : genres) {
                listGenre.add(GenreEnum.create(genre.trim()));
            }

            movie.setGenres(listGenre);
        }

        return movie;
    }

    private LocalDate formatReleaseDate(String releasedDate) {
        if (!isEmpty(releasedDate)) {
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.US);
            return LocalDate.parse(releasedDate, dateFormat);
        }

        return null;
    }
}
