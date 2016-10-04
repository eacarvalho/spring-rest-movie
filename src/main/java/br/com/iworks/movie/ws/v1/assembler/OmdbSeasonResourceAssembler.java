package br.com.iworks.movie.ws.v1.assembler;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import br.com.iworks.movie.gateway.omdb.resource.OmdbApiEpisodeResource;
import br.com.iworks.movie.gateway.omdb.resource.OmdbApiSeasonResource;
import br.com.iworks.movie.model.entity.Episode;
import br.com.iworks.movie.model.entity.Season;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class OmdbSeasonResourceAssembler {

    public Season toModel(OmdbApiSeasonResource resource) {
        if (resource == null || StringUtils.isBlank(resource.getTitle())) {
            throw new IllegalArgumentException("Season not found");
        }

        Season season = new Season();

        season.setTitle(resource.getTitle());
        season.setSeasonNo(StringUtils.isNotBlank(resource.getSeason()) ? Integer.parseInt(resource.getSeason()) : null);
        season.setTotalSeasons(resource.getTotalSeasons());
        season.setEpisodes(toModel(resource.getEpisodes()));

        return season;
    }

    private Episode toModel(final OmdbApiEpisodeResource resource) {
        Episode episode = new Episode();

        if (resource != null) {
            episode.setTitle(resource.getTitle());
            episode.setReleased(resource.getReleased());
            episode.setEpisode(resource.getEpisode());
            episode.setImdbRating(resource.getImdbRating());
            episode.setImdbID(resource.getImdbID());
        }

        return episode;
    }

    private List<Episode> toModel(final List<OmdbApiEpisodeResource> resources) {
        List<Episode> episodes = new ArrayList<>();

        if (resources != null) {
            resources.forEach(episodeResource -> episodes.add(toModel(episodeResource)));
        }

        return episodes;
    }
}