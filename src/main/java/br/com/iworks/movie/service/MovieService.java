package br.com.iworks.movie.service;

import java.util.List;

import br.com.iworks.movie.model.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MovieService {

    Movie create(Movie movie);

    Movie update(Long code, Movie movie);

    Page<Movie> list(Pageable pageable);

    Page<Movie> list(Movie movie, Pageable pageable);

    Movie read(Long code);

    Movie delete(Long code);
}