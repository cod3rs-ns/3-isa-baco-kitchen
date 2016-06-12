package com.bacovakuhinja.repository;

import com.bacovakuhinja.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Integer> {

    Review findByReservationAndReviewer_guestId(Integer reservationId, Integer guestId);
}
