package dev.busby.catalogue.pile;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface PileRepository extends MongoRepository<Pile, ObjectId> {
    Optional<Pile> findById(String pileId);
    Optional<Pile> findByName(String pileId);

}
