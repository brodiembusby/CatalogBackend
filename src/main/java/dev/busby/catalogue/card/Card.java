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
    private String Name;
    private String Image;
    private String Born;
    public Card() {}

    public Card(ObjectId objectid, String Name, String Image, String Born){
        this.objectId = objectid;
        this.Name = Name;
        this.Image = Image;
        this.Born = Born;
    }

}
