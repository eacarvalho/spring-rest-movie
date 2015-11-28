package br.com.iworks.movie.service.impl;

import br.com.iworks.movie.model.entity.Counter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import javax.inject.Named;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class CounterServiceImpl implements br.com.iworks.movie.service.CounterService {

    @Autowired
    @Named("nsMongoTemplate")
    private MongoOperations mongoOperations;

    @Override
    public Long getNextSequence(String collectionName) {
        Counter counter = mongoOperations.findAndModify(
                query(where("_id").is(collectionName)),
                new Update().inc("seq", 1),
                options().returnNew(true),
                Counter.class);

        if (counter == null) {
            counter = new Counter();
            counter.setId(collectionName);
            counter.setSeq(1L);
            mongoOperations.save(counter);
        }

        return counter.getSeq();
    }
}
