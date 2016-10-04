package br.com.iworks.movie.ws.v1.facade.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.iworks.movie.gateway.omdb.resource.OmdbApiResource;
import br.com.iworks.movie.model.entity.Movie;
import br.com.iworks.movie.service.MovieService;
import br.com.iworks.movie.service.OmdbApiService;
import br.com.iworks.movie.ws.v1.assembler.MovieResourceAssembler;
import br.com.iworks.movie.ws.v1.assembler.OmdbResourceAssembler;
import br.com.iworks.movie.ws.v1.facade.MovieFacade;
import br.com.iworks.movie.ws.v1.resource.MovieResource;

@Component
public class MovieFacadeImpl implements MovieFacade {

    @Autowired
    private OmdbApiService omdbApiService;

    @Autowired
    private MovieService movieService;

    @Autowired
    private OmdbResourceAssembler omdbResourceAssembler;

    @Autowired
    private MovieResourceAssembler movieResourceAssembler;

    @Override
    public MovieResource create(MovieResource resource) {
        Movie movie = this.getMovie(resource);
        Movie createdMovie = movieService.create(movie);

        return movieResourceAssembler.toResource(createdMovie);
    }

    @Override
    public MovieResource update(Long code, MovieResource resource) {
        Movie movie = this.getMovie(resource);
        Movie updatedMovie = movieService.update(code, movie);

        return movieResourceAssembler.toResource(updatedMovie);
    }

    private Movie getMovie(MovieResource resource) {
        Movie movie = null;
        OmdbApiResource omdbApiResource = omdbApiService.findMovie(resource.getImdbID(), resource.getOriginalTitle(), resource.getYear());

        if (omdbApiResource != null) {
            movie = omdbResourceAssembler.toModel(omdbApiResource);

            movie.setTitle(StringUtils.isNoneBlank(resource.getTitle()) ? resource.getTitle() : omdbApiResource.getTitle());
            movie.setRating(resource.getRating());

            if (StringUtils.isNoneBlank(resource.getPlot())) {
                movie.setPlot(resource.getPlot());
            }
        } else {
            movie = movieResourceAssembler.toModel(resource);
        }

        return movie;
    }
}