package cz.cvut.fel.nss.gardeningrental.productservice.service;

import cz.cvut.fel.nss.gardeningrental.productservice.exception.EntityNotFoundException;
import cz.cvut.fel.nss.gardeningrental.productservice.exception.InvalidRatingException;
import cz.cvut.fel.nss.gardeningrental.productservice.model.Product;
import cz.cvut.fel.nss.gardeningrental.productservice.model.Review;
import cz.cvut.fel.nss.gardeningrental.productservice.repository.ProductRepository;
import cz.cvut.fel.nss.gardeningrental.productservice.repository.ReviewRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private static final int lowestRating = 0;
    private static final int highestRating = 5;
    public ReviewService(ReviewRepository reviewRepository, ProductRepository productRepository) {
        this.reviewRepository = reviewRepository;
        this.productRepository = productRepository;
    }

    public List<Review> getAllReviewsOfProduct(Long productId) {
        return reviewRepository.findAllByProductId(productId);
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_USER')")
    public Review createReview(String productName, String text, int rating) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Product product = productRepository.findProductByName(productName)
                .orElseThrow(() -> new EntityNotFoundException("Couldn't find product with name " + productName));

        if (rating < lowestRating || rating > highestRating) {
            throw new InvalidRatingException(String.format("Rating %d is not in the range of <0, 5>", rating));
        }

        Review review = new Review();
        review.setDate(LocalDate.now());
        review.setText(text);
        review.setAuthorUsername(username);
        review.setRating(rating);
        review.setProduct(product);

        reviewRepository.save(review);
        return review;
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_USER')")
    public void deleteReview(Long reviewId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("Review not found"));

        if (!review.getAuthorUsername().equals(username)) {
            throw new SecurityException("User is not authorized to delete this review");
        }

        reviewRepository.deleteById(reviewId);
    }
}
