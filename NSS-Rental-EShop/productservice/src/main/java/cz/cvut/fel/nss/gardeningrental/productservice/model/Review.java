package cz.cvut.fel.nss.gardeningrental.productservice.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Review {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private LocalDate date;

    private String text;

    @Column(nullable = false)
    private int rating;

    @ManyToOne(optional = false)
    private Product product;

    @Column(nullable = false)
    private String authorUsername;
    /* we only need username from user entity. Both id and username are unique.
    If we stored id, we would have to call Auth microservice each time to get username. This is faster. */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthorUsername() {
        return authorUsername;
    }

    public void setAuthorUsername(String authorUsername) {
        this.authorUsername = authorUsername;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}