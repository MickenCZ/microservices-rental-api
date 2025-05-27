package cz.cvut.fel.ear.pujcovna.dto;

import jakarta.validation.constraints.NotBlank;

public class SerialNumberDTO {
    @NotBlank(message = "Serial number cannot be empty")
    private String serialNumber;

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public SerialNumberDTO(String serialNumber) {
        this.serialNumber = serialNumber;
    }
}
