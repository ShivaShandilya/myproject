package com.indigo.controller;

import com.indigo.dto.ReviewDto;
import com.indigo.entity.Property;
import com.indigo.entity.PropertyUser;
import com.indigo.entity.Review;
import com.indigo.repository.PropertyRepository;
import com.indigo.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {
@Autowired
    private ReviewRepository reviewRepository;

   @Autowired
private PropertyRepository propertyRepository;


    @PostMapping("/addReview/{propertyId}")
    public ResponseEntity<String> addReview(
            @PathVariable long propertyId,
            @RequestBody Review review,
            @AuthenticationPrincipal PropertyUser user){
      //  Review r = reviewRepository.findReviewByUserIdandPropertyId(propertyId, user.getId());

      


        Optional<Property> opProperty = propertyRepository.findById(propertyId);
        Property property = opProperty.get();
        Review r = reviewRepository.findReviewByUser(property, user);
        if(r!=null){
            return new ResponseEntity<>("you havw already added a review for this property",HttpStatus.BAD_REQUEST);
        }

        review.setProperty(property);
        review.setPropertyUser(user);
        reviewRepository.save(review);

return new ResponseEntity<>("Review addded successfully", HttpStatus.CREATED);
    }

}
