package br.com.iworks.movie.repository;

import java.util.List;

import br.com.iworks.movie.model.entity.Movie;

public interface MovieRepositoryCustom {

    List<Movie> list(Movie movie);
}