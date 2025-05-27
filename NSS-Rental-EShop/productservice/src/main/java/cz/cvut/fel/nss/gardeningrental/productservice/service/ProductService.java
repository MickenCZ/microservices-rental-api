package cz.cvut.fel.nss.gardeningrental.productservice.service;

import cz.cvut.fel.nss.gardeningrental.productservice.exception.EntityNotFoundException;
import cz.cvut.fel.nss.gardeningrental.productservice.exception.NegativePriceException;
import cz.cvut.fel.nss.gardeningrental.productservice.model.Category;
import cz.cvut.fel.nss.gardeningrental.productservice.model.Product;
import cz.cvut.fel.nss.gardeningrental.productservice.repository.CategoryRepository;
import cz.cvut.fel.nss.gardeningrental.productservice.repository.ProductRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.scheduling.annotation.Scheduled;
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
    private final KafkaProducer kafkaProducer;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository, KafkaProducer kafkaProducer) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.kafkaProducer = kafkaProducer;
    }

    @Cacheable(value = "product", key = "#productId")
    public Product getProduct(Long productId) {
        Optional<Product> product = productRepository.findProductById(productId);
        if (product.isPresent()) {
           return product.get();
        } else {
            throw new EntityNotFoundException("Couldn't find product id: " + productId);
        }
    }


    @Cacheable(value = "productsByCategory", key = "#categoryName")
    public List<Product> getAllByCategory(String categoryName) {
        return productRepository.findAllByCategoryName(categoryName);
    }

    @Transactional
    @CacheEvict(value = "productsByCategory", allEntries = true)
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

        kafkaProducer.sendMessage("Product", name, "created");

        Product productEntity =  productRepository.save(product);
        return productEntity.getId();
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "productsByCategory", allEntries = true),
            @CacheEvict(value = "product", key = "#oldName"),
            @CacheEvict(value = "product", key = "#newName")
    })
    public Product updateProductDetails(String oldName, String newName, boolean active, BigDecimal dailyPrice, String description) {
        if (dailyPrice.compareTo(BigDecimal.ZERO) <= 0) {
            throw new NegativePriceException("Cannot create product with negative pricing");
        }

        Product product = productRepository.findProductByName(oldName)
                .orElseThrow(() -> new EntityNotFoundException("Product with name " + oldName + " not found."));

        product.setName(newName);
        product.setActive(active);
        product.setDailyPrice(dailyPrice);
        product.setDescription(description);

        productRepository.save(product);
        return product;
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

    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return productRepository.existsById(id);
    }

    @CacheEvict(allEntries = true, cacheNames = { "product", "productsByCategory" })
    //@Scheduled(fixedDelay = 600000)
    // For testing/demonstration purposes 30 seconds
    @Scheduled(fixedDelay = 30000)
    public void cacheEvict() {
    }

}
