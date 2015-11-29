package br.com.iworks.movie.repository;

import br.com.iworks.movie.dto.MovieDTO;
import br.com.iworks.movie.model.entity.Movie;

import java.util.List;

public interface MovieRepositoryCustom {
    List<Movie> list(MovieDTO movieDTO);
}
