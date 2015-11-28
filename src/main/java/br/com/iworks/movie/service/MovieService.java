package br.com.iworks.movie.service;

import br.com.iworks.movie.model.entity.Movie;

import java.util.List;

public interface MovieService {

    Movie create(Movie movie);

    List<Movie> list();

    Movie read(Long code);
}