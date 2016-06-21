package com.bacovakuhinja.controller;

import com.bacovakuhinja.annotations.Authorization;
import com.bacovakuhinja.model.Guest;
import com.bacovakuhinja.model.Review;
import com.bacovakuhinja.service.ReviewService;
import com.bacovakuhinja.utility.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

@RestController
public class ReviewController {

    @Autowired
    ReviewService reviewService;

    @RequestMapping(
            value = "/api/reviews/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity <Collection <Review>> findReviewsByRestaurant(@PathVariable Integer id) {
        Collection <Review> reviews = reviewService.findReviewsByRestaurant(id);
        return new ResponseEntity <Collection <Review>>(reviews, HttpStatus.OK);
    }

    @RequestMapping(
            value = "/api/reviews/report/{menu_item_id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity <Collection <Review>> findReviewsByMenuItem(@PathVariable("menu_item_id") Integer itemId) {
        Collection <Review> reviews = reviewService.findReviewsByMenuItem(itemId);
        return new ResponseEntity <Collection <Review>>(reviews, HttpStatus.OK);
    }

    @RequestMapping(
            value = "/api/reviews/waiter/report/{waiter_id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity <Collection <Review>> findReviewsByWaiter(@PathVariable("waiter_id") Integer waiterId) {
        Collection <Review> reviews = reviewService.findReviewsByWaiter(waiterId);
        return new ResponseEntity <Collection <Review>>(reviews, HttpStatus.OK);
    }

    @RequestMapping(
            value = "/api/reviews/rg/{restaurant_id}/{guest_id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity <Collection <Review>> findReviewsByWaiter(@PathVariable("restaurant_id") Integer restaurantId, @PathVariable("guest_id") Integer guestId) {
        Collection <Review> reviews = reviewService.findReviewsByRestaurantAndGuest(restaurantId, guestId);
        return new ResponseEntity <Collection <Review>>(reviews, HttpStatus.OK);
    }

    @Authorization(role = Constants.UserRoles.GUEST)
    @RequestMapping(
            value = "/api/reviews/reservation/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )

    public ResponseEntity<Review> getReview(final HttpServletRequest request, @PathVariable Integer id) {
        Guest user = (Guest) request.getAttribute(Constants.Authorization.LOGGED_USER);
        Review review = reviewService.getReviewByReservation(id, user.getUserId());
        return new ResponseEntity <Review>(review, HttpStatus.OK);
    }

    @Authorization(role = Constants.UserRoles.GUEST)
    @RequestMapping(
            value = "/api/reviews",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )

    public ResponseEntity<Review> addReview(final HttpServletRequest request, @RequestBody Review review) {
        Guest user = (Guest) request.getAttribute(Constants.Authorization.LOGGED_USER);
        review.setReviewer(user);
        Review created = reviewService.create(review);
        return new ResponseEntity <Review>(created, HttpStatus.CREATED);
    }
}
