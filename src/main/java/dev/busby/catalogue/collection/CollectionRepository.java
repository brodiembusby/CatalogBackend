package dev.busby.catalogue.collection;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CollectionRepository extends MongoRepository<Collection, ObjectId> {
    Optional<Collection> findByName(String name);
}
