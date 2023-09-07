package br.com.loangenius.domain.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

@Entity
public class SACDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private BigDecimal installmentNumber;
    @NotBlank
    private BigDecimal principalPayment;
    @NotBlank
    private BigDecimal interestPayment;
    @NotBlank
    private BigDecimal debitBalance;

    @ManyToOne
    @JoinColumn(name = "loan_id")
    @JsonBackReference
    private Loan loan;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getInstallmentNumber() {
        return installmentNumber;
    }

    public void setInstallmentNumber(BigDecimal installmentNumber) {
        this.installmentNumber = installmentNumber;
    }

    public BigDecimal getPrincipalPayment() {
        return principalPayment;
    }

    public void setPrincipalPayment(BigDecimal principalPayment) {
        this.principalPayment = principalPayment;
    }

    public BigDecimal getInterestPayment() {
        return interestPayment;
    }

    public void setInterestPayment(BigDecimal interestPayment) {
        this.interestPayment = interestPayment;
    }

    public BigDecimal getDebitBalance() {
        return debitBalance;
    }

    public void setDebitBalance(BigDecimal debitBalance) {
        this.debitBalance = debitBalance;
    }

    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }
}

