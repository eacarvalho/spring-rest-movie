package br.com.iworks.movie.service.impl;

import br.com.iworks.movie.model.entity.Counter;
import br.com.iworks.movie.model.entity.Movie;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CounterServiceImplTest {

    @InjectMocks
    private CounterServiceImpl service;

    @Mock
    private MongoOperations mongoOperations;

    @Test
    public void successGetNextSequence() {

        Counter counter = new Counter("id", 2L);

        when(mongoOperations.findAndModify(
                any(Query.class),
                any(Update.class),
                any(FindAndModifyOptions.class),
                anyObject())).thenReturn(counter);

        Long seq = service.getNextSequence(Movie.COLLECTION_NAME);

        verify(mongoOperations, never()).save(any(Counter.class));
        assertThat(seq, is(2L));
    }

    @Test
    public void successCreateNextSequence() {
        when(mongoOperations.findAndModify(
                any(Query.class),
                any(Update.class),
                any(FindAndModifyOptions.class),
                anyObject())).thenReturn(null);

        Long seq = service.getNextSequence(Movie.COLLECTION_NAME);

        verify(mongoOperations, times(1)).save(any(Counter.class));
        assertThat(seq, is(1L));
    }
}