package br.com.iworks.movie.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.CaseFormat;

import br.com.iworks.movie.gateway.omdb.OmdbApiGateway;
import br.com.iworks.movie.gateway.omdb.resource.OmdbApiResource;
import br.com.iworks.movie.service.OmdbApiService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OmdbApiServiceImpl implements OmdbApiService {

    @Autowired
    private OmdbApiGateway omdbApiGateway;

    @Override
    public OmdbApiResource findMovie(String imdbID, String originalTitle, Integer year) {
        OmdbApiResource omdbApiResource = null;

        if (StringUtils.isNoneBlank(imdbID)) {
            omdbApiResource = omdbApiGateway.findByImdbID(imdbID);
        }

        if (isNull(omdbApiResource) && StringUtils.isNoneBlank(originalTitle) && year != null) {
            omdbApiResource = omdbApiGateway.findByTitleAndYear(this.getFormatTitle(originalTitle), year);
        }

        if (isNull(omdbApiResource) && StringUtils.isNoneBlank(originalTitle)) {
            omdbApiResource = omdbApiGateway.findByTitle(this.getFormatTitle(originalTitle));
        }

        return isNull(omdbApiResource) ? omdbApiResource : null;
    }

    private boolean isNull(final OmdbApiResource omdbApiResource) {
        return omdbApiResource == null || StringUtils.isNoneBlank(omdbApiResource.getTitle());
    }

    private String getFormatTitle(final String originalTitle) {
        return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, originalTitle).replaceAll("-", " ").trim();
    }
}