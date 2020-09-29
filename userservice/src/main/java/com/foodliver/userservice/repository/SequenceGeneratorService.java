package com.foodliver.userservice.repository;

import com.foodliver.userservice.model.DatabaseSequence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static org.springframework.data.mongodb.core.FindAndReplaceOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class SequenceGeneratorService {

    private ReactiveMongoOperations mongoOperations;

    @Autowired
    public SequenceGeneratorService(ReactiveMongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    public Mono<Long> generateSequence(String seqName) {
        return mongoOperations.findAndModify(query(where("_id").is(seqName)),
                new Update().inc("seq" ,1), DatabaseSequence.class)
                .map(sequence ->
                        sequence.getSeq());
    }

}
