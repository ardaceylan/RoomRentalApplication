package com.api.gobooking.review;

import com.api.gobooking.http.TimeData;
import com.api.gobooking.http.TimeDataDouble;
import com.api.gobooking.user.appuser.AppUser;
import com.api.gobooking.user.appuser.AppUserRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping(path = "gobooking/review")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping
    public List<Review> getReviews(){
        return reviewService.getReviews();
    }


    @GetMapping(path = "by_booking/{booking_id}")
    public Review getReviewByBooking(@PathVariable("booking_id") Integer bookingId){
        return reviewService.getReviewByBooking(bookingId);
    }

    // SortMode is 0 for sort by rating
    @GetMapping(path = "by_property_sort_rating/{property_id}")
    public List<Review> getReviewsByPropertySortByRating(@PathVariable("property_id") Integer property_id){
        return reviewService.getReviewsByProperty(property_id, 0);
    }

    // SortMode is 1 for sort by likes
    @GetMapping(path = "by_property_sort_likes/{property_id}")
    public List<Review> getReviewsByPropertySortByLikes(@PathVariable("property_id") Integer property_id){
        return reviewService.getReviewsByProperty(property_id, 1);
    }

    @GetMapping(path = "by_property_sort_date/{property_id}")
    public List<Review> getReviewsByPropertySortByDate(@PathVariable("property_id") Integer property_id){
        return reviewService.getReviewsByProperty(property_id, 2);
    }

    @GetMapping(path = "{review_id}")
    public Review getReview(@PathVariable("review_id") Integer review_id){
        return reviewService.getReview(review_id);
    }

    @GetMapping(path = "for_property/{property_id}")
    public BigDecimal getReviewForProperty(@PathVariable("property_id") Integer property_id){
        return reviewService.getReviewForProperty(property_id);
    }

    @PostMapping
    public void addNewReview(@RequestBody ReviewRequest reviewRequest){
        reviewService.addReview(reviewRequest);
    }

    @DeleteMapping(path = "{review_id}")
    public void deleteReview(@PathVariable("review_id") Integer review_id){
        reviewService.deleteReview(review_id);
    }

    @PutMapping(path = "{review_id}")
    public void updateReview( @PathVariable("review_id") Integer review_id,
                               @RequestParam(required = false) Integer rating,
                               @RequestParam(required = false) String review_title,
                               @RequestParam(required = false) String review_body
    )
    {
        reviewService.updateReview(review_id, rating, review_title, review_body);
    }

    @PutMapping(path = "update_likes/{review_id}/{user_id}")
    public void updateLikes(@PathVariable("review_id") Integer review_id, @PathVariable("user_id") Integer userId){
        reviewService.incrementLikes(review_id, userId);
    }

    @PutMapping(path = "decrement_likes/{review_id}/{user_id}")
    public void decrementLikes(@PathVariable("review_id") Integer review_id, @PathVariable("user_id") Integer userId){
        reviewService.decrementLikes(review_id, userId);
    }

    @GetMapping(path = "review_average={mode}")
    public List<TimeDataDouble> reviewAverageYear(@PathVariable("mode") Integer mode){
        return reviewService.reviewAverageYear(mode);
    }

    @GetMapping(path ="isLiked/{review_id}/{user_id}")
    public Boolean isLiked(@PathVariable("review_id") Integer reviewId, @PathVariable("user_id") Integer userId){
        return reviewService.isLiked(reviewId, userId);
    }
}
