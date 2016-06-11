package com.bacovakuhinja.service.impl;

import com.bacovakuhinja.model.Review;
import com.bacovakuhinja.repository.ReviewRepository;
import com.bacovakuhinja.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    ReviewRepository reviewRepository;

    @Override
    public Review create(Review review) {
        return reviewRepository.save(review);
    }

    @Override
    public Collection<Review> restaurantReviews(Integer id) {
        Collection<Review> reviews = new ArrayList<Review>();

        for (Review review : reviewRepository.findAll()) {
            if (review.getReservation() == id) {
                reviews.add(review);
            }
        }

        return reviews;
    }

    @Override
    public Review getReviewByReservation(Integer id, Integer userId) {
        for (Review review : reviewRepository.findAll()) {
            if (review.getReservation() == id && review.getReviewer().getGuestId() == userId)  {
                return review;
            }
        }
        return null;
    }

}
