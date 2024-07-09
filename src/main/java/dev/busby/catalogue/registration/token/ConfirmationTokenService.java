package dev.busby.catalogue.registration.token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final MongoTemplate mongoTemplate;

    @Autowired
    public ConfirmationTokenService(ConfirmationTokenRepository confirmationTokenRepository, MongoTemplate mongoTemplate) {
        this.confirmationTokenRepository = confirmationTokenRepository;
        this.mongoTemplate = mongoTemplate;
    }

    public Optional<ConfirmationToken> getToken(String token) {
        return confirmationTokenRepository.findByToken(token);
    }

    public void saveConfirmationToken(ConfirmationToken confirmationToken) {
        confirmationTokenRepository.save(confirmationToken);
    }

    @Transactional
    public int updateConfirmedAt(String token, LocalDateTime confirmedAt) {
        Query query = new Query(Criteria.where("token").is(token));
        Update update = new Update().set("confirmedAt", confirmedAt);
        return (int) mongoTemplate.updateFirst(query, update, ConfirmationToken.class).getModifiedCount();
    }

    public int setConfirmedAt(String token) {
        return updateConfirmedAt(token, LocalDateTime.now());
    }
}
