package br.com.iworks.movie.repository;

import br.com.iworks.movie.model.entity.Movie;
import java.util.Optional;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MovieRepository extends MovieRepositoryCustom, PagingAndSortingRepository<Movie, Long> {

    Optional<Movie> findByCode(Long aLong);
}