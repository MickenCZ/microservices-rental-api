package cz.cvut.fel.ear.pujcovna.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public class LoanCreationDTO {
    @NotNull(message = "From date cannot be null")
    @FutureOrPresent(message = "To date must be in the future or present")
    private LocalDate fromDate;
    @NotNull(message = "To date cannot be null")
    @FutureOrPresent(message = "To date must be in the future or present")
    private LocalDate toDate;
    @NotEmpty(message = "You have to borrow at least 1 item")
    List<String> productNames;

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public List<String> getProductNames() {
        return productNames;
    }

    public void setProductNames(List<String> productNames) {
        this.productNames = productNames;
    }
}
