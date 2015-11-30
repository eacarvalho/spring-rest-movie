package br.com.iworks.movie.service.impl;

import br.com.iworks.movie.exceptions.MovieException;
import br.com.iworks.movie.model.GenreEnum;
import br.com.iworks.movie.model.TypeEnum;
import br.com.iworks.movie.model.entity.Movie;
import br.com.iworks.movie.repository.MovieRepository;
import br.com.iworks.movie.service.CounterService;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.MessageSource;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Locale;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MovieServiceImplTest {

    @InjectMocks
    private MovieServiceImpl service;

    @Mock
    private MovieRepository repository;

    @Mock
    private CounterService counterService;

    @Mock
    private Validator validator;

    @Mock
    private MessageSource messageSource;

    @Before
    public void setup() {
        when(this.validator.validate(any())).thenAnswer(args -> {
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator valid = factory.getValidator();
            return valid.validate(args.getArguments()[0]);
        });

        when(this.messageSource.getMessage(any(String.class), any(Object[].class), any(Locale.class))).thenReturn(null);
    }

    @Test
    public void successCreateMovie() {
        Movie movie = this.getMovie();

        when(this.counterService.getNextSequence(Movie.COLLECTION_NAME)).thenReturn(1L);

        this.service.create(movie);

        verify(this.repository, times(1)).save(any(Movie.class));
    }

    @Test(expected = MovieException.class)
    public void errorRequiredTittleCreateMovie() {
        Movie movie = new Movie();

        movie.setOriginalTittle("Original tittle test");
        movie.setDuration(116);

        this.service.create(movie);

        verify(this.counterService, never()).getNextSequence(Movie.COLLECTION_NAME);
        verify(this.repository, never()).save(any(Movie.class));
    }

    @Test
    public void successUpdateMovie() {
        Movie movie = this.getMovie();

        when(this.service.read(any(Long.class))).thenReturn(movie);

        this.service.update(1L, movie);

        verify(this.repository, times(1)).save(any(Movie.class));
    }

    @Test(expected = MovieException.class)
    public void errorRequiredTittleUpdateMovie() {
        Movie movie = new Movie();

        movie.setOriginalTittle("Original tittle test");
        movie.setDuration(116);

        when(this.service.read(any(Long.class))).thenReturn(movie);

        this.service.update(1L, movie);

        verify(this.repository, never()).save(any(Movie.class));
    }

    @Test
    public void successDeleteMovie() {
        Movie movie = this.getMovie();

        when(this.service.read(any(Long.class))).thenReturn(movie);

        this.service.delete(1L);

        verify(this.repository, times(1)).delete(any(Movie.class));
    }

    @Test
    public void errorDeleteMovie() {
        when(this.service.read(any(Long.class))).thenReturn(null);

        this.service.delete(1L);

        verify(this.repository, never()).delete(any(Movie.class));
    }

    private Movie getMovie() {
        Movie movie = new Movie();

        movie.setTittle("Tittle test");
        movie.setOriginalTittle("Original tittle test");
        movie.setPlot("Plot");
        movie.setDuration(116);
        movie.setType(TypeEnum.MOVIE);
        movie.setGenres(Lists.newArrayList(GenreEnum.ACTION));

        return movie;
    }
}