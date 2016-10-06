package br.com.iworks.movie.ws.v1.assembler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import br.com.iworks.movie.exceptions.ListNotFoundException;
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
        resource.setRating(season.getRating());
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
        season.setRating(resource.getRating());
        season.setEpisodes(toModel(resource.getEpisodes()));

        return season;
    }

    public Page<SeasonResource> toPage(Page<Season> seasonPage) {
        if (seasonPage == null || CollectionUtils.isEmpty(seasonPage.getContent())) {
            throw new ListNotFoundException("Seasons not found");
        }

        List<SeasonResource> resources = new ArrayList<>();

        if (seasonPage.hasContent()) {
            seasonPage.getContent().forEach(movie -> resources.add(toResource(movie)));
        }

        return new PageImpl<>(resources, new PageRequest(seasonPage.getNumber(), seasonPage.getSize(), seasonPage.getSort()), seasonPage.getTotalElements());
    }

    private Episode toModel(final EpisodeResource resource) {
        Episode episode = new Episode();

        if (resource != null) {
            episode.setTitle(resource.getTitle());
            episode.setReleased(resource.getReleased());
            episode.setNumber(resource.getNumber());
            episode.setImdbRating(resource.getImdbRating());
            episode.setImdbID(resource.getImdbID());
            episode.setRating(resource.getRating());
            episode.setWatched(resource.isWatched());
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
            resource.setRating(episode.getRating());
            resource.setWatched(episode.isWatched());
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