package br.com.loangenius.repositories;

import br.com.loangenius.entities.loan.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, Long> {
}
