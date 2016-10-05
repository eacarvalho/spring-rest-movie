package br.com.iworks.movie.ws.v1.assembler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import br.com.iworks.movie.exceptions.ResourceNotFoundException;
import br.com.iworks.movie.model.entity.Episode;
import br.com.iworks.movie.model.entity.Season;
import br.com.iworks.movie.ws.v1.resource.EpisodeResource;
import br.com.iworks.movie.ws.v1.resource.SeasonResource;

@Component
public class SeasonResourceAssembler {

    public SeasonResource toResource(Season season) {
        if (season == null) {
            throw new ResourceNotFoundException("Season not found");
        }

        SeasonResource resource = new SeasonResource();

        resource.setTitle(season.getTitle());
        resource.setSeason(season.getNumber());
        resource.setTotalSeasons(season.getTotalSeasons());
        resource.setEpisodes(toResource(season.getEpisodes()));

        return resource;
    }

    public Season toModel(SeasonResource resource) {
        if (resource == null) {
            throw new IllegalArgumentException("Movie not found");
        }

        Season season = new Season();

        season.setTitle(resource.getTitle());
        season.setNumber(resource.getSeason());
        season.setTotalSeasons(resource.getTotalSeasons());
        season.setEpisodes(toModel(resource.getEpisodes()));

        return season;
    }

    private Episode toModel(final EpisodeResource resource) {
        Episode episode = new Episode();

        if (resource != null) {
            episode.setTitle(resource.getTitle());
            episode.setReleased(resource.getReleased());
            episode.setNumber(resource.getNumber());
            episode.setImdbRating(resource.getImdbRating());
            episode.setImdbID(resource.getImdbID());
        }

        return episode;
    }

    private EpisodeResource toResource(final Episode episode) {
        EpisodeResource resource = new EpisodeResource();

        if (episode != null) {
            resource.setTitle(episode.getTitle());
            resource.setReleased(episode.getReleased());
            resource.setNumber(episode.getNumber());
            resource.setImdbRating(episode.getImdbRating());
            resource.setImdbID(episode.getImdbID());
        }

        return resource;
    }

    private List<Episode> toModel(final List<EpisodeResource> resources) {
        List<Episode> episodes = new ArrayList<>();

        if (resources != null) {
            resources.forEach(this::toModel);
        }

        return episodes;
    }

    private List<EpisodeResource> toResource(final List<Episode> episodes) {
        List<EpisodeResource> resources = new ArrayList<>();

        if (episodes != null) {
            episodes.forEach(episode -> resources.add(toResource(episode)));
        }

        return resources;
    }
}