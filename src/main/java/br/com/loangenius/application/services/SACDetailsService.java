package br.com.loangenius.application.services;

import br.com.loangenius.domain.models.Loan;

import br.com.loangenius.domain.models.SACDetails;
import br.com.loangenius.domain.repositories.SACDetailsRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


@Service
public class SACDetailsService {

    private SACDetailsRepository sacDetailsRepository;

    public SACDetailsService(SACDetailsRepository sacDetailsRepository) {
        this.sacDetailsRepository = sacDetailsRepository;
    }

    public void calculateSACTable(Loan loan) {
        BigDecimal principalRemaining = BigDecimal.valueOf(loan.getAmount());
        BigDecimal installmentAmount = principalRemaining.divide(BigDecimal.valueOf(loan.getInstallments()));
        BigDecimal interestRate = BigDecimal.valueOf(loan.getInterest()).divide(BigDecimal.valueOf(100));
        BigDecimal totalRepayment = loan.getTotalRepayment();

        for (int i = 1; i <= loan.getInstallments(); i++) {
            BigDecimal interestPayment = principalRemaining.multiply(interestRate);
            BigDecimal principalPayment = installmentAmount.add(interestPayment);
            BigDecimal debitBalance = totalRepayment.subtract(principalPayment);

            SACDetails sacDetails = new SACDetails();
            sacDetails.setInstallmentNumber(BigDecimal.valueOf(i));
            sacDetails.setPrincipalPayment(principalPayment);
            sacDetails.setInterestPayment(interestPayment);
            sacDetails.setDebitBalance(debitBalance);
            sacDetails.setLoan(loan);

            loan.getSacDetailsList().add(sacDetails);

            principalRemaining = principalRemaining.subtract(installmentAmount);
        }
    }


    public void deleteLoanSAC(Loan loan) {
        List<SACDetails> tabelaSACList = sacDetailsRepository.findByLoanId(loan.getId());

        for (SACDetails sacDetails : tabelaSACList) {
            sacDetailsRepository.deleteById(sacDetails.getId());
        }
    }
}
