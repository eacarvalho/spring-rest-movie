package br.com.iworks.movie.service.impl;

import br.com.iworks.movie.exceptions.MovieException;
import br.com.iworks.movie.model.entity.Movie;
import br.com.iworks.movie.model.entity.QMovie;
import br.com.iworks.movie.repository.MovieRepository;
import br.com.iworks.movie.service.CounterService;
import br.com.iworks.movie.service.MovieService;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Calendar;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository repo;

    @Autowired
    private CounterService counterService;

    @Autowired
    private Validator validator;

    @Autowired
    private MessageSource messageSource;

    @Override
    public Movie create(Movie movie) {
        try {
            this.validateMovie(movie);

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

            try {
                this.validateMovie(movie);

                movie.setId(movieDatabase.getId());
                movie.setCode(movieDatabase.getCode());
                repo.save(movie);

                return movie;
            } catch (Exception e) {
                throw new MovieException(messageSource.getMessage("movie.save.error",
                        new Object[]{movie.getTittle(), e.getMessage()}, LocaleContextHolder
                                .getLocale()));
            }
        }

        return null;
    }

    private void validateMovie(Movie movie) {
        Set<ConstraintViolation<Movie>> errors = validator.validate(movie);

        if (!CollectionUtils.isEmpty(errors)) {
            String error = errors.stream().map(err -> err.getPropertyPath() + " : " + err.getMessage()).collect
                    (Collectors.joining(" | "));
            throw new MovieException(error);
        }
    }

    @Override
    public Page<Movie> list(Pageable pageable) {
        return repo.findAll(pageable);
    }

    @Override
    public Page<Movie> list(Movie movie, Pageable pageable) {
        return repo.list(movie, pageable);
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