package br.com.iworks.movie.service;

import java.util.List;

import br.com.iworks.movie.model.entity.Movie;

public interface MovieService {

    Movie create(Movie movie);

    Movie update(Long code, Movie movie);

    List<Movie> list();

    List<Movie> list(Movie movie);

    Movie read(Long code);

    Movie delete(Long code);
}