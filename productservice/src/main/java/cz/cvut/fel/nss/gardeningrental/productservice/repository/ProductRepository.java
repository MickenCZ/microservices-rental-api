package cz.cvut.fel.nss.gardeningrental.productservice.repository;

import cz.cvut.fel.nss.gardeningrental.productservice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findProductByName(@Param("productName") String productName);

    Optional<Product> findProductById(@Param("productId") Long productId);

    @Query("SELECT p FROM Product p JOIN p.categories c WHERE c.name = :categoryName")
    List<Product> findAllByCategoryName(@Param("categoryName") String categoryName);
}
