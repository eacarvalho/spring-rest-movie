package br.com.iworks.movie.gateway.omdb;

import br.com.iworks.movie.gateway.omdb.resource.OmdbApiResource;

public interface OmdbApiGateway {

    OmdbApiResource findByTitle(String title);
    OmdbApiResource findByTitleAndYear(String title, Integer year);
    OmdbApiResource findByImdbID(String imdbID);
}