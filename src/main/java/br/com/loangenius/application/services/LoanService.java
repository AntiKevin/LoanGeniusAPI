package br.com.loangenius.application.services;

import br.com.loangenius.domain.models.Loan;
import br.com.loangenius.domain.exceptions.BadRequestException;
import br.com.loangenius.domain.repositories.LoanRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LoanService {

    private LoanRepository loanRepository;

    public LoanService(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    public List<Loan> list() {
        return loanRepository.findAll();
    }

    public Loan listById(Long id) {
        Optional<Loan> contaOptional = loanRepository.findById(id);

        if (contaOptional.isPresent()) {
            return loanRepository.getById(id);
        } else {
            throw new BadRequestException("Emprestimo com o id inserido não existe!");
        }
    }

    public Loan create(Loan loan) {
        loanRepository.save(loan);
        return listById(loan.getId());
    }

    public Loan update(Long id, Loan loan){
        loanRepository.findById(id).ifPresentOrElse((existingLoan) -> {
            loan.setId(id);
            loan.setCreatedAt(existingLoan.getCreatedAt());
            loanRepository.save(loan);
        }, () -> {
            throw new BadRequestException("Emprestimo com o id inserido não existe!");
        });
        return listById(id);
    }

    public ResponseEntity<String> delete(Long id) {
        loanRepository.findById(id).ifPresentOrElse((existingLoan) -> {
            loanRepository.deleteById(id);
        }, () -> {
            throw new BadRequestException("Emprestimo com o id inserido não existe!");
        });
        return null;
    }

}
