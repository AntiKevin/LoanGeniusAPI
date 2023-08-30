package br.com.loangenius.domain.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "loans")
@EntityListeners(AuditingEntityListener.class)
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private Double amount;
    @NotBlank
    private Double installments;
    @NotBlank
    private Double interest;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotBlank
    private User user;
    @OneToMany(mappedBy = "loan", cascade = CascadeType.ALL)
    @NotBlank
    @JsonManagedReference
    private List<SACDetails> sacDetailsList = new ArrayList<>();
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getInstallments() {
        return installments;
    }

    public void setInstallments(Double installments) {
        this.installments = installments;
    }

    public Double getInterest() {
        return interest;
    }

    public void setInterest(Double interest) {
        this.interest = interest;
    }

    public BigDecimal setBigDecimal(double valor) {
        return BigDecimal.valueOf(valor).setScale(2, RoundingMode.HALF_UP);
    }

    @Transient
    public BigDecimal getTotalRepayment() {
        BigDecimal totalRepayment = BigDecimal.ZERO;
        for (SACDetails sacDetails : sacDetailsList) {
            totalRepayment = totalRepayment.add(sacDetails.getPrincipalPayment());
        }
        return totalRepayment;
    }

    public Long getUser() {
        return user.getId();
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<SACDetails> getSacDetailsList() {
        return sacDetailsList;
    }

    public void setSacDetailsList(List<SACDetails> sacDetailsList) {
        this.sacDetailsList = sacDetailsList;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
