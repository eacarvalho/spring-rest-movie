package br.com.iworks.movie.ws.v1.facade;

import br.com.iworks.movie.ws.v1.resource.MovieResource;

public interface MovieFacade {

    MovieResource create(MovieResource resource);
    MovieResource update(Long code, MovieResource resource);
}