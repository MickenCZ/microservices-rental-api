package cz.cvut.fel.ear.pujcovna.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

public class ProductCreationDTO {
    @NotBlank(message = "Name cannot be blank")
    private String name;
    private boolean active;
    @NotNull(message = "Daily price cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Daily price must be greater than zero")
    private BigDecimal dailyPrice;
    private String description;
    private List<@NotBlank(message = "Category name cannot be blank") String> categories;
    @NotBlank(message = "Manufacturer must not be blank")
    private String manufacturer;

    public String getName() {
        return name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
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

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }
}
