package br.com.iworks.movie.ws.v1.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.iworks.movie.gateway.omdb.resource.OmdbApiSeasonResource;
import br.com.iworks.movie.model.entity.Season;
import br.com.iworks.movie.service.OmdbApiService;
import br.com.iworks.movie.service.SeasonService;
import br.com.iworks.movie.ws.v1.assembler.OmdbSeasonResourceAssembler;
import br.com.iworks.movie.ws.v1.assembler.SeasonResourceAssembler;
import br.com.iworks.movie.ws.v1.facade.SeasonFacade;
import br.com.iworks.movie.ws.v1.resource.SeasonResource;

@Component
public class SeasonFacadeImpl implements SeasonFacade {

    @Autowired
    private OmdbApiService omdbApiService;

    @Autowired
    private SeasonService seasonService;

    @Autowired
    private OmdbSeasonResourceAssembler omdbSeasonResourceAssembler;

    @Autowired
    private SeasonResourceAssembler seasonResourceAssembler;

    @Override
    public SeasonResource create(Long movieId, SeasonResource resource) {
        Season season = this.getSeason(movieId, resource);
        Season createdSeason = seasonService.create(season);

        return seasonResourceAssembler.toResource(createdSeason);
    }

    @Override
    public SeasonResource update(Long movieId, SeasonResource resource) {
        Season season = this.getSeason(movieId, resource);
        Season updatedSeason = seasonService.update(movieId, season);

        return seasonResourceAssembler.toResource(updatedSeason);
    }

    private Season getSeason(Long movieId, SeasonResource resource) {
        Season season = null;
        OmdbApiSeasonResource omdbApiSeasonResource = omdbApiService.findSeason(resource.getTitle(), resource.getSeason());

        if (omdbApiSeasonResource != null) {
            season = omdbSeasonResourceAssembler.toModel(omdbApiSeasonResource);
        } else {
            season = seasonResourceAssembler.toModel(resource);
        }

        season.setCode(movieId);

        return season;
    }
}