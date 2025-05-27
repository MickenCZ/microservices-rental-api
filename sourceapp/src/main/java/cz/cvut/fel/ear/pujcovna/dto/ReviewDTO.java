package cz.cvut.fel.ear.pujcovna.dto;

import cz.cvut.fel.ear.pujcovna.model.Review;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class ReviewDTO {
    public ReviewDTO() {
    }

    public ReviewDTO(Review review) {
        this.productName = review.getProduct().getName();
        this.text = review.getText();
        this.rating = review.getRating();
    }

    @NotBlank(message = "Product name cannot be blank")
    private String productName;
    @NotBlank(message = "Text cannot be empty")
    private String text;
    @Min(value = 0, message = "Rating must be at least 0")
    @Max(value = 5, message = "Rating must be at most 5")
    private int rating;

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
