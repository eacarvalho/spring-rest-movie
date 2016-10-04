package br.com.iworks.movie.service;

import br.com.iworks.movie.gateway.omdb.resource.OmdbApiResource;
import br.com.iworks.movie.gateway.omdb.resource.OmdbApiSeasonResource;

public interface OmdbApiService {

    OmdbApiResource findMovie(String imdbID, String originalTitle, Integer year);
    OmdbApiSeasonResource findSeason(String title, Integer season);
}