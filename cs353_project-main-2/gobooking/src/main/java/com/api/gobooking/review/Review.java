package com.api.gobooking.review;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;
import lombok.*;
import java.sql.Timestamp;
import java.time.Instant;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "review")
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME)
@PrimaryKeyJoinColumn(name = "review_id")
public class Review {
    @Id
    private Integer review_id;
    private Integer reviewer_id;
    private Integer booking_id;
    private Integer rating;
    private String review_title;
    private String review_body;
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp review_date;
    private Integer likes;

    public Review(Integer reviewer_id, Integer booking_id, Integer rating, String review_title, String review_body, Timestamp review_date, Integer likes) {
        this.reviewer_id = reviewer_id;
        this.booking_id = booking_id;
        this.rating = rating;
        this.review_title = review_title;
        this.review_body = review_body;
        this.review_date = review_date;
        this.likes = likes;
    }

    public Review(ReviewRequest reviewRequest) {
        this.reviewer_id = reviewRequest.getReviewer_id();
        this.booking_id = reviewRequest.getBooking_id();
        this.rating = reviewRequest.getRating();
        this.review_title = reviewRequest.getReview_title();
        this.review_body = reviewRequest.getReview_body();
        this.review_date = Timestamp.from(Instant.now());
        this.likes = 0;
    }
}
