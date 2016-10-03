package br.com.iworks.movie.ws.v1.assembler;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import br.com.iworks.movie.gateway.omdb.resource.OmdbApiResource;
import br.com.iworks.movie.model.GenreEnum;
import br.com.iworks.movie.model.TypeEnum;
import br.com.iworks.movie.model.entity.Movie;

@Component
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
        movie.setDirector(resource.getDirector());

        if (StringUtils.isNoneBlank(resource.getYear())) {
            movie.setYear(Integer.parseInt(resource.getYear()));
        }

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
}