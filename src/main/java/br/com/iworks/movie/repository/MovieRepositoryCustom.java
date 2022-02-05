package br.com.iworks.movie.repository;

import br.com.iworks.movie.model.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MovieRepositoryCustom {

    Page<Movie> list(Movie movie, Pageable pageable);
}
