package br.com.iworks.movie.gateway.omdb;

import br.com.iworks.movie.gateway.omdb.resource.OmdbApiResource;
import br.com.iworks.movie.gateway.omdb.resource.OmdbApiSeasonResource;

public interface OmdbApiGateway {

    OmdbApiResource findByTitle(String title);
    OmdbApiResource findByTitleAndYear(String title, Integer year);
    OmdbApiResource findByImdbID(String imdbID);
    OmdbApiSeasonResource findSerieByTitleAndSeason(String title, Integer season);
}