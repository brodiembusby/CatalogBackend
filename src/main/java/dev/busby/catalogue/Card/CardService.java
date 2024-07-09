package dev.busby.catalogue.Card;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
// This layer contains business logic.
// It acts as an intermediary between the controller
// and the repository
@Service
public class CardService{
    @Autowired
    private CardRepository cardRepository;
  // Business Logic
    public List<Card> allCards(){
        return cardRepository.findAll();
    }
    // May return null hence Optional
    public Optional<Card> getCard(ObjectId id){
        return cardRepository.findById(id);
    }
    public Optional<Card> getCardByName(String name) {
        return cardRepository.findByName(name);
    }


}
