package br.com.iworks.movie.service;

import br.com.iworks.movie.dto.MovieDTO;
import br.com.iworks.movie.model.entity.Movie;

import java.util.List;

public interface MovieService {

    Movie create(Movie movie);

    Movie update(Long code, Movie movie);

    List<Movie> list();

    List<Movie> list(MovieDTO movieDTO);

    Movie read(Long code);

    Movie delete(Long code);
}