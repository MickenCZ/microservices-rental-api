package cz.cvut.fel.ear.pujcovna.repository;

import cz.cvut.fel.ear.pujcovna.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(@Param("categoryName") String categoryName);
}
