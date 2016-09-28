package br.com.iworks.movie.repository.impl;

import static org.springframework.data.mongodb.core.query.Criteria.where;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import br.com.iworks.movie.model.entity.Movie;
import br.com.iworks.movie.repository.MovieRepositoryCustom;

@Repository
public class MovieRepositoryImpl implements MovieRepositoryCustom {

    @Autowired
    private MongoOperations mongoOperations;

    @Override
    public List<Movie> list(Movie movie) {
        List<Movie> movies = null;

        if (movie != null) {
            List<Criteria> criteriasAnd = new ArrayList<Criteria>();

            if (StringUtils.isNotBlank(movie.getTittle())) {
                criteriasAnd.add(where("tittle").regex(Pattern.quote(movie.getTittle()), "i"));
            }

            if (StringUtils.isNotBlank(movie.getOriginalTittle())) {
                criteriasAnd.add(where("originalTittle").regex(Pattern.quote(movie.getOriginalTittle()), "i"));
            }

            if (movie.getType() != null && StringUtils.isNotBlank(movie.getType().getDescription())) {
                criteriasAnd.add(where("type").regex(Pattern.quote(movie.getType().getDescription()), "i"));
            }

            if (!CollectionUtils.isEmpty(criteriasAnd)) {
                Criteria criteria = new Criteria().andOperator(criteriasAnd.toArray(new Criteria[criteriasAnd.size()]));
                movies = mongoOperations.find(new Query(criteria), Movie.class);
            } else {
                movies = mongoOperations.findAll(Movie.class);
            }
        } else {
            movies = mongoOperations.findAll(Movie.class);
        }

        return movies;
    }
}