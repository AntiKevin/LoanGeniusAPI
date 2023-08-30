package br.com.loangenius.domain.repositories;

import br.com.loangenius.domain.models.Loan;
import br.com.loangenius.domain.models.SACDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SACDetailsRepository extends JpaRepository<SACDetails, Long> {
    List<SACDetails> findByLoanId(Long loanId);
}
