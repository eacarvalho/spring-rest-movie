package br.com.iworks.movie.ws.v1.facade.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import br.com.iworks.movie.gateway.omdb.resource.OmdbApiSeasonResource;
import br.com.iworks.movie.model.entity.Episode;
import br.com.iworks.movie.model.entity.Season;
import br.com.iworks.movie.service.OmdbApiService;
import br.com.iworks.movie.service.SeasonService;
import br.com.iworks.movie.ws.v1.assembler.OmdbSeasonResourceAssembler;
import br.com.iworks.movie.ws.v1.assembler.SeasonResourceAssembler;
import br.com.iworks.movie.ws.v1.facade.SeasonFacade;
import br.com.iworks.movie.ws.v1.resource.EpisodeResource;
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
    public SeasonResource create(String title, SeasonResource resource) {
        Season season = this.getSeason(title, resource);
        Season createdSeason = seasonService.create(season);

        return seasonResourceAssembler.toResource(createdSeason);
    }

    @Override
    public SeasonResource update(String title, Integer number, SeasonResource resource) {
        Season season = this.getSeason(title, resource);
        Season updatedSeason = seasonService.update(title, number, season);

        return seasonResourceAssembler.toResource(updatedSeason);
    }

    private Season getSeason(String title, SeasonResource resource) {
        Season season = null;
        OmdbApiSeasonResource omdbApiSeasonResource = omdbApiService.findSeason(title, resource.getNumber());

        if (omdbApiSeasonResource != null) {
            season = omdbSeasonResourceAssembler.toModel(omdbApiSeasonResource);

            season.setRating(resource.getRating());

            setRatingAndWatched(resource.getEpisodes(), season.getEpisodes());
        } else {
            season = seasonResourceAssembler.toModel(resource);
        }

        if (StringUtils.isBlank(season.getTitle())) {
            season.setTitle(title);
        }

        return season;
    }

    private void setRatingAndWatched(List<EpisodeResource> episodeResources, List<Episode> episodes) {
        if (!CollectionUtils.isEmpty(episodes)) {
            episodes.forEach(episode -> {
                EpisodeResource episodeResource = getEpisodeResource(episodeResources, episode.getNumber());

                if (episodeResource != null) {
                    episode.setRating(episodeResource.getRating());
                    episode.setWatched(episodeResource.isWatched());
                }
            });
        }
    }

    private EpisodeResource getEpisodeResource(List<EpisodeResource> episodeResources, Integer number) {
        if (!CollectionUtils.isEmpty(episodeResources)) {
            for (EpisodeResource episodeResource : episodeResources) {
                if (number.equals(episodeResource.getNumber())) {
                    return episodeResource;
                }
            }
        }
        return null;
    }
}