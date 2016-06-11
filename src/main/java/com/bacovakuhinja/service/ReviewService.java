package com.bacovakuhinja.service;

import com.bacovakuhinja.model.Review;

import java.util.Collection;

public interface ReviewService {

    Review create(Review review);

    Collection<Review> restaurantReviews(Integer id);

    Review getReviewByReservation(Integer id, Integer userId);

}
