package br.com.iworks.movie.service.impl;

import br.com.iworks.movie.repository.MovieRepository;
import br.com.iworks.movie.model.entity.Movie;
import br.com.iworks.movie.model.entity.QMovie;
import br.com.iworks.movie.service.CounterService;
import br.com.iworks.movie.service.MovieService;
import com.mysema.query.types.expr.BooleanExpression;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository repo;

    @Autowired
    private CounterService counterService;

    @Override
    public Movie create(Movie movie) {
        movie.setCode(counterService.getNextSequence(Movie.COLLECTION_NAME));

        return repo.save(movie);
    }

    @Override
    public List<Movie> list() {
        return repo.findAll();
    }

    @Override
    public Movie read(Long code) {
        QMovie qMovie = QMovie.movie;
        BooleanExpression equalsCode = qMovie.code.eq(code);

        return repo.findOne(equalsCode);
    }
}
