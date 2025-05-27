package cz.cvut.fel.nss.gardeningrental.productservice.DTO;

import cz.cvut.fel.nss.gardeningrental.productservice.model.Product;
import org.springframework.cache.annotation.Cacheable;

import java.math.BigDecimal;
import java.util.List;

public class ProductDTO {
    private String name;
    private boolean active;
    private BigDecimal dailyPrice;
    private String description;
    private List<ReviewDTO> reviews;
    private int stockCount;

    private List<String> categories;

    public ProductDTO(Product product) {
        this.name = product.getName();
        this.active = product.isActive();
        this.dailyPrice = product.getDailyPrice();
        this.description = product.getDescription();
        this.reviews = product.getReviews().stream().map(review -> new ReviewDTO(review)).toList();
        this.categories = product.getCategories().stream().map(category -> category.getName()).toList();
    }

    public ProductDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public BigDecimal getDailyPrice() {
        return dailyPrice;
    }

    public void setDailyPrice(BigDecimal dailyPrice) {
        this.dailyPrice = dailyPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ReviewDTO> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewDTO> reviews) {
        this.reviews = reviews;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }
}