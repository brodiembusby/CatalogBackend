package dev.busby.catalogue.collectible;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CollectibleRepository extends
        MongoRepository<Collectible, ObjectId> {

}