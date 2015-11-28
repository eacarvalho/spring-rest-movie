package br.com.iworks.movie.repository;

import br.com.iworks.movie.model.entity.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

public interface MovieRepository extends MongoRepository<Movie, Long>, QueryDslPredicateExecutor<Movie> {
}
