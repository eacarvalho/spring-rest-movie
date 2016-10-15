package br.com.iworks.movie.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.CaseFormat;

import br.com.iworks.movie.gateway.omdb.OmdbApiGateway;
import br.com.iworks.movie.gateway.omdb.resource.OmdbApiResource;
import br.com.iworks.movie.gateway.omdb.resource.OmdbApiSeasonResource;
import br.com.iworks.movie.service.OmdbApiService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OmdbApiServiceImpl implements OmdbApiService {

    @Autowired
    private OmdbApiGateway omdbApiGateway;

    @Override
    public OmdbApiResource findMovie(String imdbID, String originalTitle, String year) {
        OmdbApiResource omdbApiResource = null;

        if (StringUtils.isNotBlank(imdbID)) {
            omdbApiResource = omdbApiGateway.findByImdbID(imdbID);
        }

        if (isNull(omdbApiResource) && StringUtils.isNotBlank(originalTitle) && year != null) {
            omdbApiResource = omdbApiGateway.findByTitleAndYear(this.getFormatTitle(originalTitle), year);
        }

        if (isNull(omdbApiResource) && StringUtils.isNotBlank(originalTitle)) {
            omdbApiResource = omdbApiGateway.findByTitle(this.getFormatTitle(originalTitle));
        }

        return isNull(omdbApiResource) ? null : omdbApiResource;
    }

    @Override
    public OmdbApiSeasonResource findSeason(String title, Integer season) {
        OmdbApiSeasonResource omdbApiSeasonResource = null;

        if (StringUtils.isNoneBlank(title) && season != null) {
            omdbApiSeasonResource = omdbApiGateway.findSerieByTitleAndSeason(this.getFormatTitle(title), season);
        }

        return isNull(omdbApiSeasonResource) ? null : omdbApiSeasonResource;
    }

    private boolean isNull(final OmdbApiResource omdbApiResource) {
        return omdbApiResource == null || StringUtils.isBlank(omdbApiResource.getTitle());
    }

    private boolean isNull(final OmdbApiSeasonResource omdbApiSeasonResource) {
        return omdbApiSeasonResource == null || StringUtils.isBlank(omdbApiSeasonResource.getTitle());
    }

    private String getFormatTitle(final String originalTitle) {
        return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, originalTitle.toLowerCase()).replaceAll("-", " ").trim();
    }
}