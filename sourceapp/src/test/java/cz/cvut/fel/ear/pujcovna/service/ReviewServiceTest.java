package cz.cvut.fel.ear.pujcovna.service;

import cz.cvut.fel.ear.pujcovna.exception.EntityNotFoundException;
import cz.cvut.fel.ear.pujcovna.exception.InvalidRatingException;
import cz.cvut.fel.ear.pujcovna.model.Product;
import cz.cvut.fel.ear.pujcovna.model.Review;
import cz.cvut.fel.ear.pujcovna.repository.ProductRepository;
import cz.cvut.fel.ear.pujcovna.repository.ReviewRepository;
import cz.cvut.fel.ear.pujcovna.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@Transactional
@AutoConfigureTestEntityManager
@TestPropertySource(locations = "classpath:application-test.properties")
@Profile("test")
public class ReviewServiceTest {
    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    @Spy
    private ReviewService reviewService;

    @Mock
    private ReviewRepository reviewRepository;

    private void setUpSecurityContext() {
        UserDetails userDetails = User.withUsername("testUser")
                .password("password")
                .roles("USER")
                .build();
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @BeforeEach
    public void setUp() {
        setUpSecurityContext();
    }


    @Test
    public void createThrowsForInvalidProductId() {
        String invalidProductName = "dsgfdsrwedfserd";
        when(productRepository.findProductByName(invalidProductName))
                .thenReturn(null);

        assertThrows(EntityNotFoundException.class, () -> {
            reviewService.createReview(invalidProductName, "Je to na prd", 0);
        });
    }

    @Test
    public void createThrowsForInvalidRating() {
        String productName = "myProductHihi";
        Product product = new Product();
        product.setName(productName);
        when(productRepository.findProductByName(productName))
                .thenReturn(product);

        assertThrows(InvalidRatingException.class, () -> {
            reviewService.createReview(productName, "Je to až moc dobrý", 1000);
        });
    }

    @Test
    public void createCreatesReview() {
        String productName = "theBestProduct";
        Product product = new Product();
        product.setName(productName);
        when(productRepository.findProductByName(productName))
                .thenReturn(product);

        Review review = reviewService.createReview(productName, "docela dobrý", 3);
        assertEquals(3, review.getRating());
        assertEquals("docela dobrý", review.getText());
        assertEquals(productName, review.getProduct().getName());
        assertNotNull(review.getDate());
    }
}
