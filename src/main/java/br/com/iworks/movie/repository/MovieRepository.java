package br.com.iworks.movie.repository;

import br.com.iworks.movie.model.entity.Movie;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MovieRepository extends CrudRepository<Movie, Long>, QueryDslPredicateExecutor<Movie>, MovieRepositoryCustom, PagingAndSortingRepository<Movie, Long> {
}