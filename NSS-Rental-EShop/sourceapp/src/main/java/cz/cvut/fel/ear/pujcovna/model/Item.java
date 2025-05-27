package cz.cvut.fel.ear.pujcovna.model;

import jakarta.persistence.*;

@Entity
public class Item {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String serialNumber;

    @ManyToOne
    private Loan loan;  // If it is null, we have it in stock

    @ManyToOne(optional = false)
    private Product product;

    public Item() {

    }

    public Item(String serialNumber, Product product) {
        this.serialNumber = serialNumber;
        this.product = product;
        this.loan = null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
