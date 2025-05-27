package cz.cvut.fel.ear.pujcovna.repository;

import cz.cvut.fel.ear.pujcovna.model.Loan;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    @Query("SELECT l FROM Loan l WHERE l.customer.username = :username")
    List<Loan> getAllByUsername(@Param("username") String username);

    @Query("SELECT COALESCE(SUM(l.price), 0) FROM Loan l WHERE l.paymentDate BETWEEN :startDate AND :endDate")
    BigDecimal getTotalMoneyPaidBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query(name = "Loan.getTopBorrowedProducts") // In loan entity
    List<Object[]> getTopBorrowedProducts(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, Pageable pageable);
}
