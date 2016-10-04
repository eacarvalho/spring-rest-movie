package br.com.iworks.movie.ws.v1.assembler;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import br.com.iworks.movie.gateway.omdb.resource.OmdbApiResource;
import br.com.iworks.movie.model.GenreEnum;
import br.com.iworks.movie.model.TypeEnum;
import br.com.iworks.movie.model.entity.Movie;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class OmdbResourceAssembler {

    public Movie toModel(OmdbApiResource resource) {
        if (resource == null || StringUtils.isBlank(resource.getTitle())) {
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
        movie.setReleasedDate(getReleasedDate(resource.getReleased()));
        movie.setPoster(resource.getPoster());
        movie.setYear(this.getYear(resource.getYear()));

        if (StringUtils.isNoneBlank(resource.getGenre())) {
            String[] genres = resource.getGenre().split(",");
            List<GenreEnum> listGenre = new ArrayList<>();

            for (String genre : genres) {
                listGenre.add(GenreEnum.create(genre.trim()));
            }

            movie.setGenres(listGenre);
        }

        return movie;
    }

    private Date getReleasedDate(String releasedDate) {
        Date date = null;
        if (StringUtils.isNotBlank(releasedDate)) {
            try {
                DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.US);
                date = dateFormat.parse(releasedDate);
            } catch (ParseException e) {
                log.error("Error converting releasedDate {}", releasedDate);
            }
        }
        return date;
    }

    private Integer getYear(String resourceYear) {
        Integer year = null;
        if (StringUtils.isNoneBlank(resourceYear)) {
            try {
                year = Integer.parseInt(resourceYear);
            } catch (Exception e) {
                log.error("Error converting year {}", resourceYear);
            }
        }
        return year;
    }
}