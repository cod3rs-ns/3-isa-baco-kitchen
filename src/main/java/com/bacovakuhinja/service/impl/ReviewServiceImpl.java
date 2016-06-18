package com.bacovakuhinja.service.impl;

import com.bacovakuhinja.model.Reservation;
import com.bacovakuhinja.model.Review;
import com.bacovakuhinja.repository.ReservationRepository;
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

    @Autowired
    ReservationRepository reservationRepository;

    @Override
    public Review create(Review review) {
        return reviewRepository.save(review);
    }

    @Override
    public Collection<Review> restaurantReviews(Integer id) {
        Collection<Review> reviews = new ArrayList<Review>();

        for (Review review : reviewRepository.findAll()) {
            Reservation reservation = reservationRepository.findOne(review.getReservation());
            if (reservation.getRestaurant().getRestaurantId() == id) {
                reviews.add(review);
            }
        }

        return reviews;
    }

    @Override
    public Review getReviewByReservation(Integer id, Integer userId) {
        return reviewRepository.findByReservationAndReviewer_guestId(id, userId);
    }

    @Override
    public Collection <Review> findReviewsByFood(Integer itemId) {
        return reviewRepository.findReviewsByMenuItem(itemId);
    }

}
