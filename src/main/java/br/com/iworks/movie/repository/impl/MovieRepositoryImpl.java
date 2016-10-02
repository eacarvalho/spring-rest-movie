package br.com.iworks.movie.repository.impl;

import br.com.iworks.movie.model.entity.Movie;
import br.com.iworks.movie.repository.MovieRepositoryCustom;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Repository
public class MovieRepositoryImpl implements MovieRepositoryCustom {

    @Autowired
    private MongoOperations mongoOperations;

    @Override
    public Page<Movie> list(Movie movie, Pageable pageable) {
        Query query = null;
        List<Criteria> criteriasAnd = new ArrayList<>();

        if (movie != null) {
            if (StringUtils.isNotBlank(movie.getTitle())) {
                criteriasAnd.add(where("title").regex(Pattern.quote(movie.getTitle()), "i"));
            }

            if (StringUtils.isNotBlank(movie.getOriginalTitle())) {
                criteriasAnd.add(where("originalTitle").regex(Pattern.quote(movie.getOriginalTitle()), "i"));
            }

            if (movie.getType() != null && StringUtils.isNotBlank(movie.getType().getDescription())) {
                criteriasAnd.add(where("type").regex(Pattern.quote(movie.getType().getDescription()), "i"));
            }
        }

        if (!CollectionUtils.isEmpty(criteriasAnd)) {
            Criteria criteria = new Criteria().andOperator(criteriasAnd.toArray(new Criteria[criteriasAnd.size()]));

            query = new Query(criteria).with(pageable);
        } else {
            query = new Query().with(pageable);
        }

        long total = mongoOperations.count(query, Movie.class);
        List<Movie> listMovie = mongoOperations.find(query, Movie.class);

        return new PageImpl<>(listMovie, pageable, total);
    }
}