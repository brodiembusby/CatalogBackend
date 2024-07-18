package dev.busby.catalogue.card;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CardService{

    @Autowired
    private CardRepository cardRepository;

    public List<Card> allCards(){
        return cardRepository.findAll();
    }
    public Optional<Card> getCard(ObjectId id){
        return cardRepository.findById(id);
    }
    public Optional<Card> getCardByName(String name) {
        return cardRepository.findByName(name);
    }


}