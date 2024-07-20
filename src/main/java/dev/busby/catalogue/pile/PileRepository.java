package dev.busby.catalogue.pile;

import dev.busby.catalogue.card.Card;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PileRepository extends MongoRepository<Pile, ObjectId> {
      List<Pile> findAllByUserId(String userId);
      Optional<Pile> findByName(String name);
      Optional<Pile> findByUserIdAndName(String userId, String name);

}