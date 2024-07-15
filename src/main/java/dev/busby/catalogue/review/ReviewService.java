package dev.busby.catalogue.review;

import dev.busby.catalogue.collectible.Collectible;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public Review createReview(String reviewBody, String name) {
        Review review = reviewRepository.insert(new Review(reviewBody));

        // Find reviewIds in MongoDB then add a new field to it ours is an array
        mongoTemplate.update(Collectible.class)
                .matching(Criteria.where("name").is(name))
                .apply(new Update().push("reviewIds").value(review.getId()));

        return review;
    }

    public Optional<Review> getReview(ObjectId id){
        return reviewRepository.findById(id);
    }
}