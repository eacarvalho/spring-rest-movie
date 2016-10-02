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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Date;
import java.util.Locale;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MovieServiceImplTest {

    @InjectMocks
    private MovieServiceImpl service;

    @Mock
    private MovieRepository repo;

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
        Movie mockMovie = this.getMovie();

        assertNull(mockMovie.getRegistrationDate());
        assertNull(mockMovie.getCode());
        when(counterService.getNextSequence(Movie.COLLECTION_NAME)).thenReturn(1L);
        when(repo.save(any(Movie.class))).thenReturn(mockMovie);

        Movie movie = service.create(mockMovie);

        verify(repo, times(1)).save(any(Movie.class));
        assertThat(movie.getCode(), is(1L));
        assertThat(movie.getType(), is(TypeEnum.MOVIE));
        assertThat(movie.getGenres(), is(Lists.newArrayList(GenreEnum.ACTION)));
        assertNotNull(movie.getRegistrationDate());
        assertNotNull(mockMovie.getRegistrationDate());
        assertNotNull(mockMovie.getCode());
    }

    @Test(expected = MovieException.class)
    public void errorRequiredTittleCreateMovie() {
        Movie movie = new Movie();

        movie.setOriginalTitle("Original title test");
        movie.setDuration(116);

        service.create(movie);

        verify(counterService, never()).getNextSequence(Movie.COLLECTION_NAME);
        verify(repo, never()).save(any(Movie.class));
    }

    @Test
    public void successUpdateMovie() {
        Movie mockMovie = this.getMovie();

        assertNull(mockMovie.getRegistrationDate());
        assertNull(mockMovie.getCode());
        assertNull(mockMovie.getId());

        mockMovie.setCode(1L);
        mockMovie.setId("id");
        mockMovie.setRegistrationDate(new Date());

        when(service.read(any(Long.class))).thenReturn(mockMovie);
        when(repo.save(any(Movie.class))).thenReturn(mockMovie);

        Movie movie = service.update(1L, mockMovie);

        verify(counterService, never()).getNextSequence(Movie.COLLECTION_NAME);
        verify(repo, times(1)).save(any(Movie.class));
        assertThat(movie.getCode(), is(1L));
        assertThat(movie.getType(), is(TypeEnum.MOVIE));
        assertThat(movie.getGenres(), is(Lists.newArrayList(GenreEnum.ACTION)));
        assertNotNull(movie.getRegistrationDate());
        assertNotNull(mockMovie.getRegistrationDate());
        assertNotNull(mockMovie.getCode());
    }

    @Test
    public void errorCodeNotFoundUpdateMovie() {
        Movie mockMovie = this.getMovie();

        assertNull(mockMovie.getRegistrationDate());
        assertNull(mockMovie.getCode());
        assertNull(mockMovie.getId());

        when(service.read(2L)).thenReturn(null);

        Movie movie = service.update(2L, mockMovie);

        verify(counterService, never()).getNextSequence(Movie.COLLECTION_NAME);
        verify(repo, never()).save(any(Movie.class));
        assertNull(movie);
        assertNotNull(mockMovie);
    }

    @Test(expected = MovieException.class)
    public void errorRequiredTittleUpdateMovie() {
        Movie movie = new Movie();

        movie.setOriginalTitle("Original title test");
        movie.setDuration(116);

        when(service.read(any(Long.class))).thenReturn(movie);

        service.update(1L, movie);

        verify(repo, never()).save(any(Movie.class));
    }

    @Test
    public void successDeleteMovie() {
        Movie mockMovie = this.getMovie();

        mockMovie.setCode(1L);
        mockMovie.setId("id");
        mockMovie.setRegistrationDate(new Date());

        when(service.read(any(Long.class))).thenReturn(mockMovie);

        Movie movie = service.delete(1L);

        verify(repo, times(1)).delete(any(Movie.class));
        verify(counterService, never()).getNextSequence(Movie.COLLECTION_NAME);
        assertThat(movie.getCode(), is(1L));
        assertThat(movie.getType(), is(TypeEnum.MOVIE));
        assertThat(movie.getGenres(), is(Lists.newArrayList(GenreEnum.ACTION)));
        assertNotNull(movie.getRegistrationDate());
        assertNotNull(mockMovie.getRegistrationDate());
        assertNotNull(mockMovie.getCode());
    }

    @Test
    public void errorDeleteMovie() {
        when(service.read(any(Long.class))).thenReturn(null);

        Movie movie = service.delete(1L);

        verify(repo, never()).delete(any(Movie.class));
        assertNull(movie);
    }

    @Test
    public void successListAll() {
        Movie mockMovie = this.getMovie();

        mockMovie.setCode(1L);
        mockMovie.setId("id");
        mockMovie.setRegistrationDate(new Date());

        Pageable pageable = new PageRequest(1, 1);
        Page<Movie> mockMovies = new PageImpl<>(Lists.newArrayList(mockMovie), pageable, 1L);

        when(repo.findAll(any(Pageable.class))).thenReturn(mockMovies);

        Page<Movie> movies = service.list(pageable);

        verify(repo, times(1)).findAll(any(Pageable.class));
        assertNotNull(movies);
        assertNotNull(movies.getContent());
        assertNotNull(movies.getContent().get(0).getId());
        assertThat(movies.getContent().get(0).getCode(), is(1L));
    }

    @Test
    public void successListByFilter() {
        Movie mockMovie = this.getMovie();

        mockMovie.setCode(1L);
        mockMovie.setId("id");
        mockMovie.setRegistrationDate(new Date());

        Pageable pageable = new PageRequest(1, 1);
        Page<Movie> mockMovies = new PageImpl<>(Lists.newArrayList(mockMovie), pageable, 1L);

        when(repo.list(any(Movie.class), any(Pageable.class))).thenReturn(mockMovies);

        Page<Movie> movies = service.list(mockMovie, pageable);

        verify(repo, times(1)).list(any(Movie.class), any(Pageable.class));
        assertNotNull(movies);
        assertNotNull(movies.getContent());
        assertNotNull(movies.getContent().get(0).getId());
        assertThat(movies.getContent().get(0).getCode(), is(1L));
    }

    private Movie getMovie() {
        Movie movie = new Movie();

        movie.setTitle("Tittle test");
        movie.setOriginalTitle("Original title test");
        movie.setPlot("Plot");
        movie.setDuration(116);
        movie.setType(TypeEnum.MOVIE);
        movie.setGenres(Lists.newArrayList(GenreEnum.ACTION));

        return movie;
    }
}