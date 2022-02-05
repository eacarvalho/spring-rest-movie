package br.com.iworks.movie.repository.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.data.mongodb.core.query.Criteria.where;

import br.com.iworks.movie.model.GenreEnum;
import br.com.iworks.movie.model.TypeEnum;
import br.com.iworks.movie.model.entity.Movie;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.ObjectUtils;

@ExtendWith(MockitoExtension.class)
class MovieRepositoryImplTest {

    @InjectMocks
    private MovieRepositoryImpl repository;

    @Mock
    private MongoOperations mongoOperations;

    @Test
    void successListAll() {
        Movie movie = this.getMovie();
        List<Movie> listMovie = List.of(movie);
        Pageable pageable = PageRequest.of(1, 1);
        Query query = new Query().with(pageable);

        when(mongoOperations.count(query, Movie.class)).thenReturn(1L);
        when(mongoOperations.find(query, Movie.class)).thenReturn(listMovie);

        Page<Movie> movies = repository.list(null, pageable);

        assertQuery(query, movies);
    }

    @Test
    void successListByMovie() {
        Movie movie = this.getMovie();
        List<Movie> listMovie = List.of(movie);
        Pageable pageable = PageRequest.of(1, 1);
        Query query = getQuery(movie.getTitle(), movie.getOriginalTitle(), movie.getType().getDescription(), pageable);

        when(mongoOperations.count(query, Movie.class)).thenReturn(1L);
        when(mongoOperations.find(query, Movie.class)).thenReturn(listMovie);

        Page<Movie> movies = repository.list(movie, pageable);

        assertQuery(query, movies);
    }

    @Test
    void successListByTitle() {
        Movie movie = new Movie();

        movie.setTitle("Title test");

        List<Movie> listMovie = List.of(movie);
        Pageable pageable = PageRequest.of(1, 1);
        Query query = getQuery(movie.getTitle(), null, null, pageable);

        when(mongoOperations.count(query, Movie.class)).thenReturn(1L);
        when(mongoOperations.find(query, Movie.class)).thenReturn(listMovie);

        Page<Movie> movies = repository.list(movie, pageable);

        assertQuery(query, movies);
    }

    private void assertQuery(Query query, Page<Movie> movies) {
        verify(mongoOperations, times(1)).count(query, Movie.class);
        verify(mongoOperations, times(1)).find(query, Movie.class);
        assertNotNull(movies);
        assertNotNull(movies.getContent());
        assertEquals(1, movies.getContent().size());
        assertEquals("Title test", movies.getContent().get(0).getTitle());
    }

    private Query getQuery(String title, String originalTitle, String type, Pageable pageable) {
        List<Criteria> criteriaAnd = new ArrayList<>();

        if (!ObjectUtils.isEmpty(title)) {
            criteriaAnd.add(where("title").regex(Pattern.quote(title), "i"));
        }

        if (!ObjectUtils.isEmpty(originalTitle)) {
            criteriaAnd.add(where("originalTitle").regex(Pattern.quote(originalTitle), "i"));
        }

        if (!ObjectUtils.isEmpty(type)) {
            criteriaAnd.add(where("type").regex(Pattern.quote(type), "i"));
        }

        Criteria criteria = new Criteria().andOperator(criteriaAnd.toArray(new Criteria[criteriaAnd.size()]));
        return new Query(criteria).with(pageable);
    }

    private Movie getMovie() {
        Movie movie = new Movie();

        movie.setTitle("Title test");
        movie.setOriginalTitle("Original title test");
        movie.setPlot("Plot");
        movie.setDuration("116");
        movie.setType(TypeEnum.MOVIE);
        movie.setGenres(List.of(GenreEnum.ACTION));

        return movie;
    }
}