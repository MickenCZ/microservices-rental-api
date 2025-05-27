package cz.cvut.fel.ear.pujcovna.controller;

import cz.cvut.fel.ear.pujcovna.dto.ReviewDTO;
import cz.cvut.fel.ear.pujcovna.exception.EntityNotFoundException;
import cz.cvut.fel.ear.pujcovna.model.Review;
import cz.cvut.fel.ear.pujcovna.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/reviews")
@RestController
public class ReviewController {
    private ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/{productName}")
    public ResponseEntity<List<ReviewDTO>> getAllReviewsOfProduct(@PathVariable String productName) {
        List<ReviewDTO> reviews = reviewService.getAllReviewsOfProduct(productName).stream().map(review -> new ReviewDTO(review)).toList();
        return ResponseEntity.ok(reviews);
    }

    @PostMapping("/")
    public ResponseEntity<?> createReview(@Valid @RequestBody ReviewDTO reviewDTO) {
        reviewService.createReview(reviewDTO.getProductName(), reviewDTO.getText(), reviewDTO.getRating());

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.LOCATION, "/products/" + reviewDTO.getProductName());

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<?> deleteReview(@PathVariable Long reviewId) {
        try {
            reviewService.deleteReview(reviewId);
            return new ResponseEntity<>("Review deleted successfully", HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Review not found", HttpStatus.NOT_FOUND);
        }
    }


}
