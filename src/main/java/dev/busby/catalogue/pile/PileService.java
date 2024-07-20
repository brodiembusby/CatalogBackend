package dev.busby.catalogue.pile;
import dev.busby.catalogue.appuser.AppUser;
import dev.busby.catalogue.appuser.AppUserRepository;
import dev.busby.catalogue.card.Card;
import dev.busby.catalogue.card.CardRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PileService {

    @Autowired
    private final PileRepository pileRepository;
    @Autowired
    private final MongoTemplate mongoTemplate;
    @Autowired
    private final AppUserRepository appUserRepository;

    @Autowired
    private final CardRepository cardRepository;

    public PileService(PileRepository pileRepository, MongoTemplate mongoTemplate, AppUserRepository appUserRepository, CardRepository cardRepository) {
        this.pileRepository = pileRepository;
        this.mongoTemplate = mongoTemplate;
        this.appUserRepository = appUserRepository;
        this.cardRepository = cardRepository;
    }
    public List<Pile> getAllPilesByUserId(String userId) {
        return pileRepository.findAllByUserId(userId);
    }

    public Pile createPile(String image, String name, AppUser appUser) throws Exception {
        Pile pile = new Pile(image, name, appUser.getId());
        pileRepository.insert(pile);

        if (pileRepository.equals(name)) {
            throw new Exception("Pile Name already exists.");
        }

        mongoTemplate.update(AppUser.class)
                .matching(Criteria.where("userId").is(appUser.getId()))
                .apply(new Update().push("pilesArr").value(pile.getId()))
                .first();
        return pile;
    }
    public Card addCardToPile(String pileId, String cardId) throws Exception {
        // Validate and convert the IDs
        if (!ObjectId.isValid(pileId)) {
            throw new IllegalArgumentException("Invalid ObjectId format");
        }

        ObjectId pileObjectId = new ObjectId(pileId);

        System.out.println(cardId);
        Card card = cardRepository.findByName(cardId)
                .orElseThrow(() -> new RuntimeException("Card not found"));

        // Add card to pile's cardArr list
        mongoTemplate.update(Pile.class)
                .matching(Criteria.where("_id").is(pileObjectId))
                .apply(new Update().push("cardArr").value(card))
                .first();

        return card;
    }

    public Optional<Pile> getPile(ObjectId id){
        return pileRepository.findById(id);
    }

    public Optional<Pile> getPileByName(String name) {
        return pileRepository.findByName(name);
    }
    public Optional<ObjectId> getPileIdByUserIdAndName(String userId, String pileName) {
        Optional<Pile> pile = pileRepository.findByUserIdAndName(userId, pileName);
        return pile.map(Pile::getId);
    }
}
