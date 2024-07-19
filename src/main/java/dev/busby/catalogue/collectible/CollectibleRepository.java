package dev.busby.catalogue.collectible;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface CollectibleRepository extends MongoRepository<Collectible, ObjectId> {
    List<Collectible> findAllById(ObjectId id);
    Optional<Collectible> findById(ObjectId id);
}
