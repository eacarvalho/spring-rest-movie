package br.com.iworks.movie.ws.v1.facade;

import br.com.iworks.movie.ws.v1.resource.SeasonResource;

public interface SeasonFacade {

    SeasonResource create(Long movieId, SeasonResource resource);
    SeasonResource update(Long movieId, SeasonResource resource);
}