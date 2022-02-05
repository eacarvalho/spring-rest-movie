package br.com.iworks.movie.service;

import static org.springframework.util.ObjectUtils.isEmpty;

import br.com.iworks.movie.omdb.OmdbApiGateway;
import br.com.iworks.movie.omdb.resource.OmdbApiResource;
import br.com.iworks.movie.omdb.resource.OmdbApiSeasonResource;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class OmdbService {

    private final OmdbApiGateway omdbApiGateway;

    public OmdbApiResource findMovie(String imdbID, String originalTitle, String year) {
        OmdbApiResource omdbApiResource = null;

        if (!isEmpty(imdbID)) {
            omdbApiResource = omdbApiGateway.findByImdbID(imdbID);
        }

        if (isNull(omdbApiResource) && !isEmpty(originalTitle) && year != null) {
            omdbApiResource = omdbApiGateway.findByTitleAndYear(this.getFormatTitle(originalTitle), year);
        }

        if (isNull(omdbApiResource) && !isEmpty(originalTitle)) {
            omdbApiResource = omdbApiGateway.findByTitle(this.getFormatTitle(originalTitle));
        }

        return isNull(omdbApiResource) ? null : omdbApiResource;
    }

    public OmdbApiSeasonResource findSeason(String title, Integer season) {
        OmdbApiSeasonResource omdbApiSeasonResource = null;

        if (!isEmpty(title) && season != null) {
            omdbApiSeasonResource = omdbApiGateway.findSerieByTitleAndSeason(this.getFormatTitle(title), season);
        }

        return isNull(omdbApiSeasonResource) ? null : omdbApiSeasonResource;
    }

    private boolean isNull(final OmdbApiResource omdbApiResource) {
        return omdbApiResource == null || isEmpty(omdbApiResource.getTitle());
    }

    private boolean isNull(final OmdbApiSeasonResource omdbApiSeasonResource) {
        return omdbApiSeasonResource == null || isEmpty(omdbApiSeasonResource.getTitle());
    }

    private String getFormatTitle(final String originalTitle) {
        return originalTitle.toLowerCase().replaceAll("-", " ").trim();
    }
}
