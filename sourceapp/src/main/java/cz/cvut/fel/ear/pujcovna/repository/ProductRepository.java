package cz.cvut.fel.ear.pujcovna.repository;

import cz.cvut.fel.ear.pujcovna.model.Item;
import cz.cvut.fel.ear.pujcovna.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT kv.name FROM Product kv WHERE kv.id IN :productIds AND NOT EXISTS (SELECT p FROM Item p WHERE p.product = kv AND p.loan IS NULL)")
    List<String> findNamesOfProductsNotInStock(@Param("productIds") List<Long> productIds);

    @Query("SELECT kv.id FROM Product kv WHERE kv.id IN :productIds AND (kv.active = false OR NOT EXISTS (SELECT p FROM Item p WHERE p.product.id = kv.id))")
    List<Long> findInvalidProductIds(@Param("productIds") List<Long> productIds);

    @Query("SELECT p FROM Item p WHERE p.product.id = :productIds AND p.loan IS NULL")
    List<Item> findAvailableItems(@Param("productIds") Long productIds);

    @Query("SELECT p FROM Product p WHERE p.name = :productName")
    Product findProductByName(@Param("productName") String productName);

    @Query("SELECT p FROM Product p JOIN p.categories c WHERE c.name = :categoryName")
    List<Product> findAllByCategoryName(@Param("categoryName") String categoryName);
}
