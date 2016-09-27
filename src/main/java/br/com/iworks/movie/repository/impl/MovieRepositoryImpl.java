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

import br.com.iworks.movie.dto.MovieDTO;
import br.com.iworks.movie.model.entity.Movie;
import br.com.iworks.movie.repository.MovieRepositoryCustom;

@Repository
public class MovieRepositoryImpl implements MovieRepositoryCustom {

    @Autowired
    private MongoOperations mongoOperations;

    @Override
    public List<Movie> list(MovieDTO movieDTO) {
        List<Movie> movies = null;

        if (movieDTO != null) {
            List<Criteria> criteriasAnd = new ArrayList<Criteria>();

            if (StringUtils.isNotBlank(movieDTO.getTittle())) {
                criteriasAnd.add(where("tittle").regex(Pattern.quote(movieDTO.getTittle()), "i"));
            }

            if (StringUtils.isNotBlank(movieDTO.getOriginalTitle())) {
                criteriasAnd.add(where("originalTittle").regex(Pattern.quote(movieDTO.getOriginalTitle()), "i"));
            }

            if (StringUtils.isNotBlank(movieDTO.getType())) {
                criteriasAnd.add(where("type").regex(Pattern.quote(movieDTO.getType()), "i"));
            }

            if (!CollectionUtils.isEmpty(criteriasAnd)) {
                Criteria criteria = new Criteria().andOperator(criteriasAnd.toArray(new Criteria[0]));
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
