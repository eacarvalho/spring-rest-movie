package br.com.iworks.movie.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.iworks.movie.model.entity.Counter;
import br.com.iworks.movie.model.entity.Movie;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

@ExtendWith(MockitoExtension.class)
public class CounterServiceTest {

    @InjectMocks
    private CounterService service;

    @Mock
    private MongoOperations mongoOperations;

    @Test
    void getNextSequence_GetNextSequence_WithSuccess() {
        when(mongoOperations.findAndModify(any(Query.class), any(Update.class), any(FindAndModifyOptions.class), any()))
            .thenReturn(new Counter("id", 2L));

        Long sequence = service.getNextSequence(Movie.COLLECTION_NAME);

        verify(mongoOperations, never()).save(any(Counter.class));
        assertEquals(2L, sequence);
    }

    @Test
    void getNextSequence_CreateNextSequence_WithSuccess() {
        when(mongoOperations.findAndModify(any(Query.class), any(Update.class), any(FindAndModifyOptions.class), any()))
            .thenReturn(null);

        Long sequence = service.getNextSequence(Movie.COLLECTION_NAME);

        verify(mongoOperations, times(1)).save(any(Counter.class));
        assertEquals(1L, sequence);
    }
}