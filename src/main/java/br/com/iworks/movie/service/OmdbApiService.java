package br.com.iworks.movie.service;

import br.com.iworks.movie.gateway.omdb.resource.OmdbApiResource;

public interface OmdbApiService {

    OmdbApiResource findMovie(String imdbID, String originalTitle, Integer year);
}