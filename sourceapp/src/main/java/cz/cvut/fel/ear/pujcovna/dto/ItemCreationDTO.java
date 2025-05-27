package cz.cvut.fel.ear.pujcovna.dto;

import jakarta.validation.constraints.NotBlank;

public class ItemCreationDTO {
    @NotBlank(message = "Serial number cannot be empty")
    private String serialNumber;
    @NotBlank(message = "Item must belong to a product")
    private String productName;

    public ItemCreationDTO() {
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public ItemCreationDTO(String serialNumber, String productName) {
        this.serialNumber = serialNumber;
        this.productName = productName;
    }
}
