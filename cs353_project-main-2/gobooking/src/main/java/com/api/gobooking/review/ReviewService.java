package com.api.gobooking.review;

import com.api.gobooking.http.TimeData;
import com.api.gobooking.http.TimeDataDouble;
import com.api.gobooking.property.Property;
import com.api.gobooking.property.PropertyService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final PropertyService propertyService;
    public boolean reviewExists(Integer id) {
        return reviewRepository.findById(id).isPresent();
    }

    public List<Review> getReviews(){
        return reviewRepository.findAll();
    }

    public Review getReview(Integer id){
        Optional<Review> optionalReview = reviewRepository.findById(id);

        if (optionalReview.isEmpty()){
            throw new IllegalStateException(String.format("getReview: Review with id (%s) does not exist", id));
        }

        return optionalReview.get();
    }

    public boolean addReview(ReviewRequest reviewRequest){
        boolean success = false;
        Review review = new Review(reviewRequest);
        reviewRepository.save(review);
        success = true;
        return success;
    }

    public boolean deleteReview(Integer id){
        boolean success = false;
        Optional<Review> optionalReview = reviewRepository.findById(id);

        if (optionalReview.isEmpty()){
            throw new IllegalStateException(String.format("deleteReview: Review with id (%s) does not exist", id));
        }

        reviewRepository.deleteById(id);

        success = true;
        return success;
    }

    public boolean updateReview(Integer id, Integer rating, String review_title, String review_body){
        boolean success = false;
        Optional<Review> optionalReview = reviewRepository.findById(id);

        if (optionalReview.isEmpty()){
            throw new IllegalStateException(String.format("updateReview: Review with id (%s) does not exist", id));
        }

        Review review = optionalReview.get();

        if (rating != null){
            review.setRating(rating);
        }
        if (review_title != null){
            review.setReview_title(review_title);
        }
        if (review_body != null){
            review.setReview_body(review_body);
        }
        review.setReview_date(Timestamp.from(Instant.now()));

        reviewRepository.updateReview(review);

        success = true;
        return success;
    }

    public boolean incrementLikes(Integer id, Integer userId){
        boolean success = false;
        Optional<Review> optionalReview = reviewRepository.findById(id);

        if (optionalReview.isEmpty()){
            throw new IllegalStateException(String.format("incrementLikes: Review with id (%s) does not exist", id));
        }

        Review review = optionalReview.get();

        int mode = 1;
        reviewRepository.updateLikes(id, userId, review.getLikes() + 1, mode);

        success = true;
        return success;
    }

    public BigDecimal getReviewForProperty(Integer propertyId) {
        propertyService.getProperty(propertyId);

        return reviewRepository.getReviewFromProperty(propertyId);
    }

    // SortMode is 0 for sort by rating, and 1 for sort by likes
    public List<Review> getReviewsByProperty(Integer propertyId, Integer sortMode) {
        propertyService.getProperty(propertyId);

        return reviewRepository.getReviewsByProperty(propertyId, sortMode);
    }

    public boolean decrementLikes(Integer id, Integer userId){
        boolean success = false;
        Optional<Review> optionalReview = reviewRepository.findById(id);

        if (optionalReview.isEmpty()){
            throw new IllegalStateException(String.format("incrementLikes: Review with id (%s) does not exist", id));
        }

        Review review = optionalReview.get();

        int mode = 2;
        reviewRepository.updateLikes(id, userId, review.getLikes() - 1, mode);

        success = true;
        return success;
    }

    public List<TimeDataDouble> reviewAverageYear(Integer mode) {
        return reviewRepository.reviewAverageYear(mode);
    }

    public Boolean isLiked(Integer reviewId, Integer userId) {
        return reviewRepository.isLiked(reviewId, userId);
    }

    public Review getReviewByBooking(Integer bookingId) {
        return reviewRepository.getReviewByBooking(bookingId);
    }
}
