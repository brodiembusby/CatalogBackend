package dev.busby.catalogue.pile;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface PileRepository extends MongoRepository<Pile, ObjectId> {
      List<Pile> findAllByUserId(String userId);
      Optional<Pile> findById(ObjectId id);
}
