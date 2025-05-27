package cz.cvut.fel.ear.pujcovna.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Product {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private boolean active;

    public Product() {
    }

    public Product(String name, boolean active, BigDecimal dailyPrice, String description, List<Category> categories, String manufacturer) {
        this.name = name;
        this.active = active;
        this.dailyPrice = dailyPrice;
        this.description = description;
        this.categories = categories;
        this.manufacturer = manufacturer;
        this.reviews = new ArrayList<>();
        this.items = new ArrayList<>();
    }

    @Column(nullable = false)
    private BigDecimal dailyPrice;

    @Column(nullable = false)
    private String manufacturer;
    private String description;

    @OneToMany(mappedBy = "product")
    private List<Review> reviews;

    @OneToMany(mappedBy = "product")
    private List<Item> items;

    @ManyToMany
    private List<Category> categories;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public void setDailyPrice(BigDecimal dailyPrice) {
        this.dailyPrice = dailyPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String popis) {
        this.description = popis;
    }

    public List<Item> getItems() {
        return items;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public void setItems(List<Item> polozkyVybaveni) {
        this.items = polozkyVybaveni;
    }
}
