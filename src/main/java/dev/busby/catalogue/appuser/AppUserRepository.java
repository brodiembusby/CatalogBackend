package dev.busby.catalogue.appuser;

import org.springframework.data.mongodb.repository.MongoRepository;


import java.util.Optional;
// When it was JPA it used to be Long instead of String
public interface AppUserRepository extends MongoRepository<AppUser, String> {

    // This will create the MongoDB query
    Optional<AppUser> findByEmail(String email);

}
