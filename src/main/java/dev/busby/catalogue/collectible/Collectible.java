package dev.busby.catalogue.collectible;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
//This layer represents the data structure.
// It contains entity classes that are mapped to database piles.
// The physical data
@Getter
@Setter
@Data
@Document(collection = "collectibles")
public class Collectible {

    @Id
    private ObjectId id;
    private ObjectId pileId;
    private String name;
    private String image;
    private String description;

//    // Default Collectible
//    public Collectible(String name, String image, String description){
//        this.name = name;
//        this.image = image;
//        this.description = description;
//    }
    // Collectible Tied to a collection
    public Collectible(ObjectId pileId, String name, String image, String description){
        this.pileId = pileId;
        this.name = name;
        this.image = image;
        this.description = description;
    }

}
