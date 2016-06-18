package com.bacovakuhinja.service;

import com.bacovakuhinja.model.Review;

import java.util.Collection;

public interface ReviewService {

    Review create(Review review);

    Review getReviewByReservation(Integer id, Integer userId);

    Collection <Review> findReviewsByFood(Integer itemId);

    Collection <Review> findReviewsByRestaurant(Integer restaurantId);

    Collection <Review> findReviewsByWaiter(Integer userId);
}
