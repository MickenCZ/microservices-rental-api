package cz.cvut.fel.ear.pujcovna.controller;

import cz.cvut.fel.ear.pujcovna.dto.LoanCreationDTO;
import cz.cvut.fel.ear.pujcovna.dto.LoanGetDTO;
import cz.cvut.fel.ear.pujcovna.model.Loan;
import cz.cvut.fel.ear.pujcovna.service.LoanService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/loans")
public class LoanController {
    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @GetMapping("/")
    public ResponseEntity<List<LoanGetDTO>> getAllLoansByUser() {
        List<Loan> loans = loanService.getAllLoansByUser();
        List<LoanGetDTO> loanGetDTOS = loans.stream().map(loan -> new LoanGetDTO(loan)).toList();
        return ResponseEntity.ok(loanGetDTOS);
    }

    @GetMapping("/all")
    public ResponseEntity<List<LoanGetDTO>> getAllLoansTotal() {
        List<Loan> loans = loanService.getAllLoansTotal();
        List<LoanGetDTO> loanGetDTOS = loans.stream().map(loan -> new LoanGetDTO(loan)).toList();
        return ResponseEntity.ok(loanGetDTOS);
    }

    @PostMapping("/")
    public ResponseEntity<String> createLoan(@Valid @RequestBody LoanCreationDTO loanCreationDTO) {
        loanService.create(loanCreationDTO.getFromDate(), loanCreationDTO.getToDate(), loanCreationDTO.getProductNames());
        return new ResponseEntity<>("Loan created successfully", HttpStatus.CREATED);
    }

    @GetMapping("/revenue")
    public ResponseEntity<BigDecimal> getRevenueBetweenDates(@RequestParam("startDate") LocalDate startDate,
                                                             @RequestParam("endDate") LocalDate endDate) {
        return ResponseEntity.ok(loanService.getRevenueBetweenDates(startDate, endDate));
    }

    @GetMapping("/bestproducts")
    public ResponseEntity<List<Object[]>> getTopNBorrowedProducts(
            @RequestParam("startDate") LocalDate startDate,
            @RequestParam("endDate") LocalDate endDate,
            @RequestParam("N") int N)
    {
        return ResponseEntity.ok(loanService.getTopNBorrowedProducts(startDate, endDate, N));
    }

}
