package com.bacovakuhinja.service;

import com.bacovakuhinja.model.Review;

import java.util.Collection;

public interface ReviewService {

    public Review create(Review review);

    public Collection<Review> restaurantReviews(Integer id);

}
