package cz.cvut.fel.ear.pujcovna.service;

import cz.cvut.fel.ear.pujcovna.exception.EntityNotFoundException;
import cz.cvut.fel.ear.pujcovna.exception.NegativePriceException;
import cz.cvut.fel.ear.pujcovna.model.Category;
import cz.cvut.fel.ear.pujcovna.model.Product;
import cz.cvut.fel.ear.pujcovna.repository.CategoryRepository;
import cz.cvut.fel.ear.pujcovna.repository.ProductRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public Product getProduct(String productName) {
        return productRepository.findProductByName(productName);
    }

    public List<Product> getAllByCategory(String categoryName) {
        return productRepository.findAllByCategoryName(categoryName);
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Long createNewProduct(String name, boolean active, BigDecimal dailyPrice, String description, List<String> categoryNames, String manufacturer) {
        if (dailyPrice.compareTo(BigDecimal.ZERO) <= 0) {
            throw new NegativePriceException("Cannot create product with negative pricing");
        }

        List<Category> categories = getCategoriesByNames(categoryNames);

        Product product = new Product(name, active, dailyPrice, description, categories, manufacturer);

        for (Category category : categories) {
            category.getProducts().add(product);
            categoryRepository.save(category);
        }

        Product productEntity =  productRepository.save(product);
        return productEntity.getId();
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void updateProductDetails(String oldName, String newName, boolean active, BigDecimal dailyPrice, String description) {
        if (dailyPrice.compareTo(BigDecimal.ZERO) <= 0) {
            throw new NegativePriceException("Cannot create product with negative pricing");
        }

        Product product = productRepository.findProductByName(oldName);
        if (product != null) {
            product.setName(newName);
            product.setActive(active);
            product.setDailyPrice(dailyPrice);
            product.setDescription(description);
            productRepository.save(product);
        } else {
            throw new EntityNotFoundException("Product with name " + oldName + " not found.");
        }
    }

    private List<Category> getCategoriesByNames(List<String> categoryNames) {
        List<Category> categoryObjects = new ArrayList<>();
        for (String categoryName : categoryNames) {
            Optional<Category> category = categoryRepository.findByName(categoryName);
            if (category.isPresent()) {
                categoryObjects.add(category.get());
            } else {
                throw new EntityNotFoundException("Category with name: " + categoryName + " not found");
            }
        }
        return categoryObjects;
    }
}
