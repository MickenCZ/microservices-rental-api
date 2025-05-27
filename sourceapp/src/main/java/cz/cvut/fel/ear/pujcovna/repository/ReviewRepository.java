package cz.cvut.fel.ear.pujcovna.repository;

import cz.cvut.fel.ear.pujcovna.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findAllByProductName(@Param("productName") String productName);
    @Query("SELECT r from Review r WHERE r.product.id = :productId")
    List<Review> findAllByProductId(@Param("productId") Long productId);
}
