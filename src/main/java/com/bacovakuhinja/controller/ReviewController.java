package com.bacovakuhinja.controller;

import com.bacovakuhinja.annotations.Authorization;
import com.bacovakuhinja.model.Guest;
import com.bacovakuhinja.model.Review;
import com.bacovakuhinja.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.util.Collection;

@RestController
public class ReviewController {

    @Autowired
    ReviewService reviewService;

    @RequestMapping(
            value    = "/api/reviews/{id}",
            method   = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Collection<Review>> getReviews(@PathVariable Integer id) {
        Collection<Review> reviews = reviewService.restaurantReviews(id);

        return new ResponseEntity<Collection<Review>>(reviews, HttpStatus.OK);
    }

    @Authorization(value = "guest")
    @RequestMapping(
            value    = "/api/reviews",
            method   = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Review> addReview(final HttpServletRequest request, @RequestBody Review review) {
        Guest user = (Guest) request.getAttribute("loggedUser");

        review.setReviewer(user);
        Review created = reviewService.create(review);

        return new ResponseEntity<Review>(created, HttpStatus.CREATED);
    }

}
