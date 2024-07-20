package dev.busby.catalogue.pile;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PileRepository extends MongoRepository<Pile, ObjectId> {
      List<Pile> findAllByUserId(String userId);

}