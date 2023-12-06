package com.api.gobooking.review;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRequest {
    private Integer rating;
    private String review_title;
    private String review_body;
    private Integer reviewer_id;
    private Integer booking_id;
}
