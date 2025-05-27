package cz.cvut.fel.ear.pujcovna.dto;

import cz.cvut.fel.ear.pujcovna.model.Loan;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class LoanGetDTO {
    private Long id;
    private LocalDate fromDate;
    private LocalDate toDate;
    private BigDecimal price;
    private boolean returned;
    private List<String> productNames;
    private LocalDate paymentDate;

    public LoanGetDTO(Loan loan) {
        this.fromDate = loan.getFromDate();
        this.toDate = loan.getToDate();
        this.price = loan.getPrice();
        this.returned = loan.isReturned();
        this.id = loan.getId();
        this.paymentDate = loan.getPaymentDate();
        this.productNames = loan.getItems()
                .stream()
                .map(item -> item.getProduct().getName())
                .toList();
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public boolean isReturned() {
        return returned;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }

    public List<String> getProductNames() {
        return productNames;
    }

    public void setProductNames(List<String> productNames) {
        this.productNames = productNames;
    }
}
