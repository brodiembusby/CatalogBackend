package dev.busby.catalogue.collection;

import dev.busby.catalogue.appuser.AppUser;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "collections")
public class Collection {

    @Id
    private final ObjectId objectId;
    private final ObjectId[] collectibleArr;
    private final AppUser appUser;
    private final String name;
    private final String image;

    public Collection(String image, String name, AppUser appUser, ObjectId[] collectibleArr) {
        this.objectId = new ObjectId();
        this.image = image;
        this.name = name;
        this.appUser = appUser;
        this.collectibleArr = collectibleArr;
    }


}
