package br.com.iworks.movie.ws.v1.assembler;

import br.com.iworks.movie.exceptions.ListNotFoundException;
import br.com.iworks.movie.exceptions.ResourceNotFoundException;
import br.com.iworks.movie.gateway.omdb.OmdbApiGateway;
import br.com.iworks.movie.gateway.omdb.resource.OmdbApiResource;
import br.com.iworks.movie.model.entity.Movie;
import br.com.iworks.movie.ws.v1.resource.MovieResource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class MovieResourceAssembler {

    @Autowired
    private OmdbApiGateway omdbApiGateway;

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
        resource.setDirectors(movie.getDirectors());
        resource.setRating(movie.getRating());

        OmdbApiResource omdbApiResource = omdbApiGateway.findByTitle(resource.getOriginalTitle());

        if (StringUtils.isNoneBlank(omdbApiResource.getTitle())) {
            resource.setOmdbResource(omdbApiResource);
        }

        return resource;
    }

    public List<MovieResource> toResources(List<Movie> movies) {
        if (movies == null || CollectionUtils.isEmpty(movies)) {
            throw new ListNotFoundException("Movies not found");
        }

        List<MovieResource> resources = new ArrayList<>();

        movies.forEach(movie -> resources.add(toResource(movie)));

        return resources;
    }

    public Page<MovieResource> toPage(Page<Movie> moviePage) {
        if (moviePage == null || CollectionUtils.isEmpty(moviePage.getContent())) {
            throw new ListNotFoundException("Movies not found");
        }

        List<MovieResource> resources = new ArrayList<>();

        if (moviePage.hasContent()) {
            moviePage.getContent().forEach(movie -> resources.add(toResource(movie)));
        }

        return new PageImpl<>(resources, new PageRequest(moviePage.getNumber(), moviePage.getSize(), moviePage.getSort()), moviePage.getTotalElements());
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