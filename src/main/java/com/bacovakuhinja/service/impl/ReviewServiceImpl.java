package com.bacovakuhinja.service.impl;

import com.bacovakuhinja.model.Review;
import com.bacovakuhinja.repository.ReservationRepository;
import com.bacovakuhinja.repository.ReviewRepository;
import com.bacovakuhinja.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    ReservationRepository reservationRepository;

    @Override
    public Review create(Review review) {
        return reviewRepository.save(review);
    }

    @Override
    public Review getReviewByReservation(Integer id, Integer userId) {
        return reviewRepository.findByReservationAndReviewer_guestId(id, userId);
    }

    @Override
    public Collection <Review> findReviewsByFood(Integer itemId) {
        return reviewRepository.findReviewsByMenuItem(itemId);
    }

    @Override
    public Collection <Review> findReviewsByRestaurant(Integer restaurantId) {
        return reviewRepository.findReviewsByRestaurant(restaurantId);
    }

    @Override
    public Collection <Review> findReviewsByWaiter(Integer userId) {
        return reviewRepository.findReviewsByWaiter(userId);
    }

}
