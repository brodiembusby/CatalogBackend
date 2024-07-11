package dev.busby.catalogue.card;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
//This layer represents the data structure.
// It contains entity classes that are mapped to database collections.
// The physical data
@Getter
@Setter
@Data
@Document(collection = "cards")
public class Card {
    @Id
    private ObjectId objectId;
    private String name;
    private String image;
    private String born;

    public Card() {}

    public Card(ObjectId objectId, String name, String image, String born){
        this.objectId = objectId;
        this.name = name;
        this.image = image;
        this.born = born;
    }

}
