package cz.cvut.fel.ear.pujcovna.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CategoryProductDTO {
    @NotBlank(message = "Category name must not be empty")
    private String categoryName;
    @NotNull(message = "Must have a product id")
    private String productName;

    public CategoryProductDTO(String categoryName, String productName) {
        this.categoryName = categoryName;
        this.productName = productName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }


}
