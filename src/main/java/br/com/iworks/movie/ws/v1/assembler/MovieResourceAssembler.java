package br.com.iworks.movie.ws.v1.assembler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import br.com.iworks.movie.model.entity.Movie;
import br.com.iworks.movie.ws.v1.resource.MovieResource;

@Component
public class MovieResourceAssembler {

    public MovieResource toResource(Movie movie) {
        if (movie == null) {
            return null;
        }

        MovieResource resource = new MovieResource();

        resource.setCode(movie.getCode());
        resource.setTittle(movie.getTittle());
        resource.setOriginalTittle(movie.getOriginalTittle());
        resource.setDuration(movie.getDuration());
        resource.setType(movie.getType());
        resource.setGenres(movie.getGenres());
        resource.setReleasedDate(movie.getReleasedDate());
        resource.setYear(movie.getYear());
        resource.setPlot(movie.getPlot());
        resource.setDirectors(movie.getDirectors());
        resource.setRating(movie.getRating());

        return resource;
    }

    public List<MovieResource> toResources(List<Movie> movies) {
        if (movies == null) {
            return null;
        }

        List<MovieResource> resources = new ArrayList<>();

        movies.forEach(movie -> {
            resources.add(toResource(movie));
        });

        return resources;
    }

    public Movie toModel(MovieResource resource) {
        if (resource == null) {
            return null;
        }

        Movie movie = new Movie();

        movie.setCode(resource.getCode());
        movie.setTittle(resource.getTittle());
        movie.setOriginalTittle(resource.getOriginalTittle());
        movie.setDuration(resource.getDuration());
        movie.setType(resource.getType());
        movie.setGenres(resource.getGenres());
        movie.setReleasedDate(resource.getReleasedDate());
        movie.setYear(resource.getYear());
        movie.setPlot(resource.getPlot());
        movie.setDirectors(resource.getDirectors());
        movie.setRating(resource.getRating());

        return movie;
    }
}