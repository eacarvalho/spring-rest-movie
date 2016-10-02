package br.com.iworks.movie.gateway.omdb;

import br.com.iworks.movie.gateway.omdb.resource.OmdbApiResource;

public interface OmdbApiGateway {
    OmdbApiResource findByTitle(String title);
}