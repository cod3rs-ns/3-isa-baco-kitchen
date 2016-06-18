package com.bacovakuhinja.repository;

import com.bacovakuhinja.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface ReviewRepository extends JpaRepository<Review, Integer> {

    Review findByReservationAndReviewer_guestId(Integer reservationId, Integer guestId);


    @Query("SELECT rv from Review rv, ClientOrder co, OrderItem oi WHERE (rv.reservation = co.reservation.reservationId) AND (oi.order.orderId = co.orderId) AND (oi.menuItem.menuItemId = ?1)")
    Collection<Review> findReviewsByMenuItem(Integer id);

    @Query("SELECT rv from Review rv, Reservation rs WHERE (rv.reservation = rs.reservationId) AND (rs.restaurant.restaurantId = ?1)")
    Collection<Review> findReviewsByRestaurant(Integer id);

    @Query("SELECT rv from Review rv WHERE rv.reservation = ?1")
    Collection<Review> findReviewsByWaiter(Integer id);
}
