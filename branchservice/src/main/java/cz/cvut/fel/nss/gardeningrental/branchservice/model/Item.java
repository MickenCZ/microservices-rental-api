package cz.cvut.fel.nss.gardeningrental.branchservice.model;

import jakarta.persistence.*;

@Entity
public class Item {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String serialNumber;

    @ManyToOne(optional = false)
    private Branch branch;

    public Item() {
    }

    public Item(String serialNumber, Branch branch, Long productId) {
        this.serialNumber = serialNumber;
        this.branch = branch;
        this.productId = productId;
    }

    @Column(nullable = false)
    private Long productId;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
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

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }
}
