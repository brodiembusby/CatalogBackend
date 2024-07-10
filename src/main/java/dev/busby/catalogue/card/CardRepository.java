package dev.busby.catalogue.card;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// Talks to the physical database This layer interacts with the database. It contains
// methods for performing CRUD (Create, Read, Update, Delete) operations
@Repository
public interface CardRepository extends MongoRepository<Card, ObjectId> {

    Optional<Card> findByName(String name);
}
