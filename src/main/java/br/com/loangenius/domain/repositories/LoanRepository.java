package br.com.loangenius.domain.repositories;

import br.com.loangenius.domain.models.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, Long> {
}
