package cz.cvut.fel.nss.gardeningrental.productservice.DTO;

import cz.cvut.fel.nss.gardeningrental.productservice.model.Review;

public class ReviewDTO {
    public ReviewDTO() {
    }

    public ReviewDTO(Review review) {
        this.productName = review.getProduct().getName();
        this.text = review.getText();
        this.rating = review.getRating();
        this.authorUsername = review.getAuthorUsername();
    }

    private String productName;
    private String text;
    private int rating;
    private String authorUsername;

    public String getAuthorUsername() {
        return authorUsername;
    }

    public void setAuthorUsername(String authorUsername) {
        this.authorUsername = authorUsername;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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
}
