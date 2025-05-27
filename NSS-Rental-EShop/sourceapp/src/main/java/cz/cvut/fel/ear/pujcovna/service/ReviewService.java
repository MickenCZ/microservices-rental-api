package cz.cvut.fel.ear.pujcovna.service;

import cz.cvut.fel.ear.pujcovna.exception.EntityNotFoundException;
import cz.cvut.fel.ear.pujcovna.exception.InvalidRatingException;
import cz.cvut.fel.ear.pujcovna.model.Product;
import cz.cvut.fel.ear.pujcovna.model.Review;
import cz.cvut.fel.ear.pujcovna.model.User;
import cz.cvut.fel.ear.pujcovna.repository.ProductRepository;
import cz.cvut.fel.ear.pujcovna.repository.ReviewRepository;
import cz.cvut.fel.ear.pujcovna.repository.UserRepository;
import cz.cvut.fel.ear.pujcovna.service.security.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private static final int lowestRating = 0;
    private static final int highestRating = 5;
    public ReviewService(ReviewRepository reviewRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public List<Review> getAllReviewsOfProduct(String productName) {
        return reviewRepository.findAllByProductName(productName);
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_USER')")
    public Review createReview(String productName, String text, int rating) {
        User user = userRepository.findByUsername(SecurityUtils.getLoggedInUsername());

        Product product = productRepository.findProductByName(productName);

        if (product == null) { // Cannot find product to review
            throw new EntityNotFoundException("Couldn't find product with id " + productName);
        }

        if (rating < lowestRating || rating > highestRating) {
            throw new InvalidRatingException(String.format("Rating %d is not in the range of <0, 5>", rating));
        }

        Review review = new Review();
        review.setDate(LocalDate.now());
        review.setText(text);
        review.setCustomer(user);
        review.setRating(rating);
        review.setProduct(product);

        reviewRepository.save(review);
        return review;
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_USER')")
    public void deleteReview(Long reviewId) {
        User user = userRepository.findByUsername(SecurityUtils.getLoggedInUsername());

        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new EntityNotFoundException("Review not found"));
        if (!review.getCustomer().getUsername().equals(user.getUsername())) {
            throw new SecurityException("User is not authorized to delete this review");
        }

        reviewRepository.deleteById(reviewId);
    }
}




