package dev.busby.catalogue.pile;

import dev.busby.catalogue.collectible.Collectible;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@Document(collection = "piles")
public class Pile {

    @Id
    private ObjectId id;
    private List<Collectible> collectibleArr; // Use List instead of array
    private String appUserId; // Store AppUser's id instead of the whole object
    private String name;
    private String image;

    public Pile(String image, String name, String appUserId, List<Collectible> collectibleArr) {
        this.image = image;
        this.name = name;
        this.appUserId = appUserId;
        this.collectibleArr = collectibleArr;
    }
}
