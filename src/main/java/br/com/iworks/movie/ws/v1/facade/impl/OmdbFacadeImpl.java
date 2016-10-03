package br.com.iworks.movie.ws.v1.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.iworks.movie.gateway.omdb.OmdbApiGateway;
import br.com.iworks.movie.gateway.omdb.resource.OmdbApiResource;
import br.com.iworks.movie.model.entity.Movie;
import br.com.iworks.movie.service.MovieService;
import br.com.iworks.movie.ws.v1.assembler.MovieResourceAssembler;
import br.com.iworks.movie.ws.v1.assembler.OmdbResourceAssembler;
import br.com.iworks.movie.ws.v1.facade.OmdbFacade;
import br.com.iworks.movie.ws.v1.resource.MovieResource;
import br.com.iworks.movie.ws.v1.resource.OmdbRequest;

@Component
public class OmdbFacadeImpl implements OmdbFacade {

    @Autowired
    private OmdbApiGateway omdbApiGateway;

    @Autowired
    private MovieService movieService;

    @Autowired
    private OmdbResourceAssembler omdbResourceAssembler;

    @Autowired
    private MovieResourceAssembler movieResourceAssembler;

    @Override
    public MovieResource createMovieByImdb(OmdbRequest omdbRequest) {
        OmdbApiResource omdbApiResource = omdbApiGateway.findByTitle(omdbRequest.getTitle());
        Movie movie = movieService.create(omdbResourceAssembler.toModel(omdbApiResource));
        movie.setRating(omdbRequest.getRating());
        return movieResourceAssembler.toResource(movie);
    }
}