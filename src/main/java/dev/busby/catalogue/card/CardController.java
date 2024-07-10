package dev.busby.catalogue.card;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
// This layer handles HTTP requests, processes them, and returns responses.
// It acts as an entry point to the application.


// IT goes Controller to service to Repository
@RestController
@RequestMapping("/api/v1/cards")
public class CardController {

    @Autowired
    private CardService cardService;

    // Using a service class and returning to the API layer
    @GetMapping
    public ResponseEntity<List<Card>> allCards(){
        return new ResponseEntity<List<Card>>(cardService.allCards(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Card>> getCard(@PathVariable String id){
        Optional<Card> card;
        if (ObjectId.isValid(id)) {
            card = cardService.getCard(new ObjectId(id));
        } else {
            card = cardService.getCardByName(id);
        }
        return new ResponseEntity<Optional<Card>>(card, HttpStatus.OK);
    }

}
