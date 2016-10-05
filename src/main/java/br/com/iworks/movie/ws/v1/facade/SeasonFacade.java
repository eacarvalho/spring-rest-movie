package br.com.iworks.movie.ws.v1.facade;

import br.com.iworks.movie.ws.v1.resource.SeasonResource;

public interface SeasonFacade {

    SeasonResource create(String title, SeasonResource resource);
    SeasonResource update(String title, Integer number, SeasonResource resource);
}