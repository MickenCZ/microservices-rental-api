package cz.cvut.fel.nss.gardeningrental.productservice.controller;

import cz.cvut.fel.nss.gardeningrental.productservice.DTO.ReviewDTO;
import cz.cvut.fel.nss.gardeningrental.productservice.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/reviews")
@RestController
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/{productId}")
    public ResponseEntity<List<ReviewDTO>> getAllReviewsOfProduct(@PathVariable Long productId) {
        List<ReviewDTO> reviews = reviewService.getAllReviewsOfProduct(productId).stream().map(review -> new ReviewDTO(review)).toList();
        return ResponseEntity.ok(reviews);
    }

    @PostMapping("/")
    public ResponseEntity<?> createReview(@Valid @RequestBody ReviewDTO reviewDTO) {
        reviewService.createReview(reviewDTO.getProductName(), reviewDTO.getText(), reviewDTO.getRating());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<?> deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
        return new ResponseEntity<>("Review deleted successfully", HttpStatus.OK);
    }
}
