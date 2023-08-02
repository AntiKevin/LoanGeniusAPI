package br.com.loangenius.services;

import br.com.loangenius.entities.Loan;
import br.com.loangenius.exceptions.BadRequestException;
import br.com.loangenius.repositories.LoanRepository;
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

    public List<Loan> listById(Long id) {
        Optional<Loan> contaOptional = loanRepository.findById(id);

        if (contaOptional.isPresent()) {
            return List.of(contaOptional.get());
        } else {
            throw new BadRequestException("Conta com o id inserido não existe!");
        }
    }

    public List<Loan> create(Loan loan) {
        loanRepository.save(loan);
        return listById(loan.getId());
    }

    public List<Loan> update(Long id, Loan loan){
        loanRepository.findById(id).ifPresentOrElse((existingLoan) -> {
            loan.setId(id);
            loanRepository.save(loan);
        }, () -> {
            throw new BadRequestException("Conta com o id inserido não existe!");
        });
        return listById(id);
    }

    public List<Loan> delete(Long id) {
        loanRepository.deleteById(id);
        return listById(id);
    }

}
