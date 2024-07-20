package dev.busby.catalogue.card;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Data
@Document(collection = "cards")
public class Card {
    @Id
    private ObjectId objectId;
    private String name;
    private String image;
    private String description;
    public Card() {}

    public Card(ObjectId objectId, String name, String image, String description){
        this.objectId = objectId;
        this.name = name;
        this.image = image;
        this.description = description;
    }

}