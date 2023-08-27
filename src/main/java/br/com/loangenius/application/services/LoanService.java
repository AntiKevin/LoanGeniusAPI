package br.com.loangenius.application.services;

import br.com.loangenius.application.dtos.CalculateLoanDTO;
import br.com.loangenius.domain.models.Loan;
import br.com.loangenius.domain.exceptions.BadRequestException;
import br.com.loangenius.domain.models.User;
import br.com.loangenius.domain.repositories.LoanRepository;
import br.com.loangenius.domain.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LoanService {

    private LoanRepository loanRepository;

    private UserRepository userRepository;

    public LoanService(LoanRepository loanRepository, UserRepository userRepository) {
        this.loanRepository = loanRepository;
        this.userRepository = userRepository;
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
        User user = userRepository.findById(loan.getUser()).orElse(null);
        if (user == null) {
            // Trate o caso onde o usuário não foi encontrado
            throw new BadRequestException("User not found");
        }

        loan.setUser(user);
        loanRepository.save(loan);
        return loan;
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

    public CalculateLoanDTO calculate(Loan loan){
        CalculateLoanDTO response = new CalculateLoanDTO();
        response.setAmount(loan.getAmount());
        response.setInstallments(loan.getInstallments());
        response.setInterest(loan.getInterest());
        response.setTotalRepayment(loan.getTotalRepayment());
        return response;
    }
}
