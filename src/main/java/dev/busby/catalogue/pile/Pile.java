package dev.busby.catalogue.pile;

import dev.busby.catalogue.card.Card;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Document(collection = "piles")
public class Pile {

    @Id
    private ObjectId id;
    private List<Card> cardArr;
    private String userId;
    private String name;
    private String image;

    public Pile(String image, String name, String userId) {
        this.image = image;
        this.name = name;
        this.userId = userId;
        this.cardArr = new ArrayList<>();  // Initialize the card list here
    }
}
