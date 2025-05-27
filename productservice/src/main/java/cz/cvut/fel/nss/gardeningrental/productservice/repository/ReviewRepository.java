package cz.cvut.fel.nss.gardeningrental.productservice.repository;

import cz.cvut.fel.nss.gardeningrental.productservice.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findAllByProductName(@Param("productName") String productName);
    List<Review> findAllByProductId(@Param("productId") Long productId);
}
