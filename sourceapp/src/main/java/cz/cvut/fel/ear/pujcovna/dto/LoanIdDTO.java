package cz.cvut.fel.ear.pujcovna.dto;

import jakarta.validation.constraints.NotNull;

public class LoanIdDTO {
    public LoanIdDTO() {
    }

    public Long getLoanID() {
        return loanID;
    }

    public void setLoanID(Long loanID) {
        this.loanID = loanID;
    }
    @NotNull(message = "You need to specify the loan by its id")
    private Long loanID;

    public LoanIdDTO(Long loanID) {
        this.loanID = loanID;
    }
}
