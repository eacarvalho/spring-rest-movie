package br.com.iworks.movie.repository.impl;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.data.mongodb.core.query.Criteria.where;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.google.common.collect.Lists;

import br.com.iworks.movie.model.GenreEnum;
import br.com.iworks.movie.model.TypeEnum;
import br.com.iworks.movie.model.entity.Movie;

@RunWith(MockitoJUnitRunner.class)
public class MovieRepositoryImplTest {

    @InjectMocks
    private MovieRepositoryImpl repository;

    @Mock
    private MongoOperations mongoOperations;

    @Test
    public void successListAll() {
        Movie movie = this.getMovie();
        List<Movie> listMovie = Lists.newArrayList(movie);
        Pageable pageable = new PageRequest(1, 1);
        Query query = new Query().with(pageable);

        when(mongoOperations.count(query, Movie.class)).thenReturn(1L);
        when(mongoOperations.find(query, Movie.class)).thenReturn(listMovie);

        Page<Movie> movies = repository.list(null, pageable);

        assertQuery(query, movies);
    }

    @Test
    public void successListByMovie() {
        Movie movie = this.getMovie();
        List<Movie> listMovie = Lists.newArrayList(movie);
        Pageable pageable = new PageRequest(1, 1);
        Query query = getQuery(movie.getTitle(), movie.getOriginalTitle(), movie.getType().getDescription(), pageable);

        when(mongoOperations.count(query, Movie.class)).thenReturn(1L);
        when(mongoOperations.find(query, Movie.class)).thenReturn(listMovie);

        Page<Movie> movies = repository.list(movie, pageable);

        assertQuery(query, movies);
    }

    @Test
    public void successListByTitle() {
        Movie movie = new Movie();

        movie.setTitle("Title test");

        List<Movie> listMovie = Lists.newArrayList(movie);
        Pageable pageable = new PageRequest(1, 1);
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
        assertThat(movies.getContent().size(), is(1));
        assertThat(movies.getContent().get(0).getTitle(), is("Title test"));
    }

    private Query getQuery(String title, String originalTitle, String type, Pageable pageable) {
        List<Criteria> criteriasAnd = new ArrayList<>();

        if (StringUtils.isNotBlank(title)) {
            criteriasAnd.add(where("title").regex(Pattern.quote(title), "i"));
        }

        if (StringUtils.isNotBlank(originalTitle)) {
            criteriasAnd.add(where("originalTitle").regex(Pattern.quote(originalTitle), "i"));
        }

        if (StringUtils.isNotBlank(type)) {
            criteriasAnd.add(where("type").regex(Pattern.quote(type), "i"));
        }

        Criteria criteria = new Criteria().andOperator(criteriasAnd.toArray(new Criteria[criteriasAnd.size()]));
        return new Query(criteria).with(pageable);
    }

    private Movie getMovie() {
        Movie movie = new Movie();

        movie.setTitle("Title test");
        movie.setOriginalTitle("Original title test");
        movie.setPlot("Plot");
        movie.setDuration("116");
        movie.setType(TypeEnum.MOVIE);
        movie.setGenres(Lists.newArrayList(GenreEnum.ACTION));

        return movie;
    }
}