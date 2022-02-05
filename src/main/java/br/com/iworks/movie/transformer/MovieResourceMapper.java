package br.com.iworks.movie.transformer;

import br.com.iworks.movie.exception.ResourceNotFoundException;
import br.com.iworks.movie.model.TypeEnum;
import br.com.iworks.movie.model.entity.Movie;
import br.com.iworks.movie.model.resource.MovieResource;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class MovieResourceMapper {

    public MovieResource toResource(Movie movie) {
        if (movie == null) {
            throw new ResourceNotFoundException("Movie not found");
        }

        MovieResource resource = new MovieResource();

        resource.setCode(movie.getCode());
        resource.setTitle(movie.getTitle());
        resource.setOriginalTitle(movie.getOriginalTitle());
        resource.setDuration(movie.getDuration());
        resource.setType(movie.getType());
        resource.setGenres(movie.getGenres());
        resource.setReleasedDate(movie.getReleasedDate());
        resource.setYear(movie.getYear());
        resource.setPlot(movie.getPlot());
        resource.setDirector(movie.getDirector());
        resource.setRating(movie.getRating());
        resource.setImdbRating(movie.getImdbRating());
        resource.setImdbID(movie.getImdbID());
        resource.setPoster(movie.getPoster());

        return resource;
    }

    public List<MovieResource> toResources(List<Movie> movies) {
        if (movies == null || CollectionUtils.isEmpty(movies)) {
            throw new ResourceNotFoundException("Movies not found");
        }

        List<MovieResource> resources = new ArrayList<>();

        movies.forEach(movie -> resources.add(toResource(movie)));

        return resources;
    }

    public Movie toModel(MovieResource resource) {
        if (resource == null) {
            throw new IllegalArgumentException("Movie not found");
        }

        Movie movie = new Movie();

        movie.setCode(resource.getCode());
        movie.setTitle(resource.getTitle());
        movie.setOriginalTitle(resource.getOriginalTitle());
        movie.setDuration(resource.getDuration());
        movie.setType(resource.getType() != null ? TypeEnum.valueOf(resource.getType().toString()) : null);
        movie.setGenres(resource.getGenres());
        movie.setReleasedDate(resource.getReleasedDate());
        movie.setYear(resource.getYear());
        movie.setPlot(resource.getPlot());
        movie.setDirector(resource.getDirector());
        movie.setRating(resource.getRating());
        movie.setImdbRating(resource.getImdbRating());
        movie.setImdbID(resource.getImdbID());
        movie.setPoster(resource.getPoster());

        return movie;
    }
}
