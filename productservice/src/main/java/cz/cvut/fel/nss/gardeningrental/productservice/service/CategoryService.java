package cz.cvut.fel.nss.gardeningrental.productservice.service;

import cz.cvut.fel.nss.gardeningrental.productservice.exception.EntityNotFoundException;
import cz.cvut.fel.nss.gardeningrental.productservice.model.Category;
import cz.cvut.fel.nss.gardeningrental.productservice.model.Product;
import cz.cvut.fel.nss.gardeningrental.productservice.repository.CategoryRepository;
import cz.cvut.fel.nss.gardeningrental.productservice.repository.ProductRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    private final ProductRepository productRepository;
    private final KafkaProducer kafkaProducer;

    public CategoryService(CategoryRepository categoryRepository, ProductRepository productRepository, KafkaProducer kafkaProducer) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.kafkaProducer = kafkaProducer;
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void createNewCategory(String categoryName) {
        Category category = new Category(categoryName);
        kafkaProducer.sendMessage("New Category", categoryName, "was created");
        categoryRepository.save(category);
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void addCategoryToProduct(String productName, String categoryName) {
        Product product = productRepository.findProductByName(productName)
                .orElseThrow(() -> new EntityNotFoundException("Product with name " + productName + " not found."));

        Category category = categoryRepository.findByName(categoryName)
                .orElseThrow(() -> new EntityNotFoundException("Category with name " + categoryName + " not found."));

        product.getCategories().add(category);
        category.getProducts().add(product);
        kafkaProducer.sendMessage("Category", categoryName, "was added to product", productName);
        productRepository.save(product);
        categoryRepository.save(category);
    }

}