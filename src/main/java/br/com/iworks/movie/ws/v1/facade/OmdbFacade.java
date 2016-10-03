package br.com.iworks.movie.ws.v1.facade;

import br.com.iworks.movie.ws.v1.resource.MovieResource;
import br.com.iworks.movie.ws.v1.resource.OmdbRequest;

public interface OmdbFacade {
    MovieResource createMovieByImdb(OmdbRequest omdbRequest);
}