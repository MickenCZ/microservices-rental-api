package cz.cvut.fel.ear.pujcovna.service;

import cz.cvut.fel.ear.pujcovna.exception.EntityNotFoundException;
import cz.cvut.fel.ear.pujcovna.model.Category;
import cz.cvut.fel.ear.pujcovna.model.Product;
import cz.cvut.fel.ear.pujcovna.repository.CategoryRepository;
import cz.cvut.fel.ear.pujcovna.repository.ProductRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public CategoryService(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void createNewCategory(String categoryName) {
        Category category = new Category(categoryName);
        categoryRepository.save(category);
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void addCategoryToProduct(String productName, String categoryName) {
        Product product = productRepository.findProductByName(productName);
        Optional<Category> category = categoryRepository.findByName(categoryName);
        if (product == null) {
            throw new EntityNotFoundException("Product with name " + productName + " not found.");
        }
        if (category.isEmpty()) {
            throw new EntityNotFoundException("Category with name " + categoryName + " not found.");
        }

        product.getCategories().add(category.get());
        category.get().getProducts().add(product);
        productRepository.save(product);
        categoryRepository.save(category.get());
    }

}
