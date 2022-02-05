package br.com.iworks.movie.repository.impl;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.util.ObjectUtils.isEmpty;

import br.com.iworks.movie.model.entity.Movie;
import br.com.iworks.movie.repository.MovieRepositoryCustom;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

@AllArgsConstructor
@Repository
public class MovieRepositoryImpl implements MovieRepositoryCustom {

    private final MongoOperations mongoOperations;

    @Override
    public Page<Movie> list(Movie movie, Pageable pageable) {
        Query query = null;
        List<Criteria> criteriaAnd = new ArrayList<>();

        if (movie != null) {
            if (!isEmpty(movie.getTitle())) {
                criteriaAnd.add(where("title").regex(Pattern.quote(movie.getTitle()), "i"));
            }

            if (!isEmpty(movie.getOriginalTitle())) {
                criteriaAnd.add(where("originalTitle").regex(Pattern.quote(movie.getOriginalTitle()), "i"));
            }

            if (movie.getType() != null && !isEmpty(movie.getType().getDescription())) {
                criteriaAnd.add(where("type").regex(Pattern.quote(movie.getType().getDescription()), "i"));
            }
        }

        if (!CollectionUtils.isEmpty(criteriaAnd)) {
            Criteria criteria = new Criteria().andOperator(criteriaAnd.toArray(new Criteria[0]));

            query = new Query(criteria).with(pageable);
        } else {
            query = new Query().with(pageable);
        }

        long total = mongoOperations.count(query, Movie.class);
        List<Movie> listMovie = mongoOperations.find(query, Movie.class);

        return new PageImpl<>(listMovie, pageable, total);
    }
}