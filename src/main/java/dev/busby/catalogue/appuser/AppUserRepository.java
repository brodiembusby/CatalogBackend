package dev.busby.catalogue.appuser;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;
@Repository
public interface AppUserRepository extends MongoRepository<AppUser, String> {

    // This will create the MongoDB query
    Optional<AppUser> findByEmail(String email);

}
