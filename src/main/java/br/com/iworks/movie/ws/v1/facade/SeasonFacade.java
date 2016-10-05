package br.com.iworks.movie.ws.v1.facade;

import br.com.iworks.movie.ws.v1.resource.SeasonResource;

public interface SeasonFacade {

    SeasonResource create(SeasonResource resource);
    SeasonResource update(String title, Integer seasonNo, SeasonResource resource);
}