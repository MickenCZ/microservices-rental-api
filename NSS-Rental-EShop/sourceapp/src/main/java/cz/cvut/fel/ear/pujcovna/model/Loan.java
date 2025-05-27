package cz.cvut.fel.ear.pujcovna.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@NamedQuery(name = "Loan.getTopBorrowedProducts",
        query = "SELECT i.product.id, COUNT(i.product.id) as borrowCount " +
                "FROM Loan l JOIN l.items i " +
                "WHERE l.fromDate BETWEEN :startDate AND :endDate " +
                "GROUP BY i.product.id ORDER BY borrowCount DESC"
)
public class Loan {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private LocalDate paymentDate;

    @Column(nullable = false)
    private LocalDate fromDate;

    @Column(nullable = false)
    private LocalDate toDate;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private boolean returned;

    @OneToMany(mappedBy = "loan")
    private List<Item> items;

    @ManyToOne(optional = false)
    private User customer;

    public Loan() {
    }



    public Loan(LocalDate fromDate, LocalDate toDate, BigDecimal price, boolean returned, List<Item> items, User customer) {
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.price = price;
        this.returned = returned;
        this.items = items;
        this.customer = customer;
        this.paymentDate = LocalDate.now();
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public boolean isReturned() {
        return returned;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> polozkyVybaveni) {
        this.items = polozkyVybaveni;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }
}
