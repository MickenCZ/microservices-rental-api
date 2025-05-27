package cz.cvut.fel.ear.pujcovna.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class ProductUpdateDTO {
    @NotBlank(message = "Existing product name cannot be empty")
    private String oldName;
    @NotBlank(message = "New name cannot be empty")
    private String newName;
    private boolean active;
    @NotNull(message = "Daily price cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Daily price must be greater than zero")
    private BigDecimal dailyPrice;
    private String description;

    public ProductUpdateDTO(String oldName, String newName, boolean active, BigDecimal dailyPrice, String description) {
        this.oldName = oldName;
        this.newName = newName;
        this.active = active;
        this.dailyPrice = dailyPrice;
        this.description = description;
    }

    public String getOldName() {
        return oldName;
    }

    public void setOldName(String oldName) {
        this.oldName = oldName;
    }

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
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
}
