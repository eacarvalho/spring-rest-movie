package br.com.iworks.movie.service.impl;

import br.com.iworks.movie.dto.MovieDTO;
import br.com.iworks.movie.exceptions.MovieException;
import br.com.iworks.movie.repository.MovieRepository;
import br.com.iworks.movie.model.entity.Movie;
import br.com.iworks.movie.model.entity.QMovie;
import br.com.iworks.movie.service.CounterService;
import br.com.iworks.movie.service.MovieService;
import com.mysema.query.types.expr.BooleanExpression;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.Clock;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Service
@Slf4j
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository repo;

    @Autowired
    private CounterService counterService;

    @Autowired
    private MessageSource messageSource;

    @Override
    public Movie create(Movie movie) {
        try {
            movie.setCode(counterService.getNextSequence(Movie.COLLECTION_NAME));
            movie.setRegistrationDate(Calendar.getInstance().getTime());
            movie = repo.save(movie);
        } catch (Exception e) {
            throw new MovieException(messageSource.getMessage("movie.save.error",
                    new Object[]{movie.getTittle(), e.getMessage()}, LocaleContextHolder
                            .getLocale()));
        }

        return movie;
    }

    @Override
    public Movie update(Long code, Movie movie) {
        Movie movieDatabase = this.read(code);

        if (movieDatabase != null) {
            movie.setId(movieDatabase.getId());
            movie.setCode(movieDatabase.getCode());

            repo.save(movie);

            return movie;
        }

        return null;
    }

    @Override
    public List<Movie> list() {
        return repo.findAll();
    }

    @Override
    public List<Movie> list(MovieDTO movieDTO) {
        return repo.list(movieDTO);
    }

    @Override
    public Movie read(Long code) {
        QMovie qMovie = QMovie.movie;
        BooleanExpression equalsCode = qMovie.code.eq(code);

        return repo.findOne(equalsCode);
    }

    @Override
    public Movie delete(Long code) {
        Movie movie = this.read(code);

        if (movie != null) {
            repo.delete(movie);
        }

        return movie;
    }
}
