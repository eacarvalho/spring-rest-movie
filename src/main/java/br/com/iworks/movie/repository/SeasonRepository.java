package br.com.iworks.movie.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.iworks.movie.model.entity.Season;

public interface SeasonRepository extends CrudRepository<Season, String>, QueryDslPredicateExecutor<Season>, PagingAndSortingRepository<Season, String> {

    Page<Season> findByTitleIgnoreCase(String title, Pageable pageable);
}