package br.com.iworks.movie.omdb;

import br.com.iworks.movie.omdb.resource.OmdbApiResource;
import br.com.iworks.movie.omdb.resource.OmdbApiSeasonResource;

public interface OmdbApiGateway {

    OmdbApiResource findByTitle(String title);

    OmdbApiResource findByTitleAndYear(String title, String year);

    OmdbApiResource findByImdbID(String imdbID);

    OmdbApiSeasonResource findSerieByTitleAndSeason(String title, Integer season);
}