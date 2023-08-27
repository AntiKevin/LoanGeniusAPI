package br.com.loangenius.application.services;

import br.com.loangenius.application.dtos.CalculateLoanDTO;
import br.com.loangenius.domain.models.Loan;
import br.com.loangenius.domain.exceptions.BadRequestException;
import br.com.loangenius.domain.models.User;
import br.com.loangenius.domain.repositories.LoanRepository;
import br.com.loangenius.domain.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LoanService {

    private LoanRepository loanRepository;

    private UserRepository userRepository;

    private AuthenticationService authenticationService;

    public LoanService(LoanRepository loanRepository,
                       UserRepository userRepository,
                       AuthenticationService authenticationService) {
        this.loanRepository = loanRepository;
        this.userRepository = userRepository;
        this.authenticationService = authenticationService;
    }

    public List<Loan> list() {
        User principal = authenticationService.getCurrentUser();
        Long userId = principal.getId();

        return loanRepository.findByUserId(userId);
    }

    public Loan listById(Long id) {
        Optional<Loan> contaOptional = loanRepository.findById(id);

        if (contaOptional.isPresent()) {
            User currentUser = authenticationService.getCurrentUser();
            Long currentUserId = currentUser.getId();
            Loan loan = loanRepository.getById(id);
            Long loanId = loan.getId();

            if (loanId.equals(currentUserId)){
                return loan;
            }
            else {
                throw new BadRequestException("Emprestimo com o id inserido inacessível!");
            }

        }
        else {
            throw new BadRequestException("Emprestimo com o id inserido não existe!");
        }
    }

    public Loan create(Loan loan) {
        User principal = authenticationService.getCurrentUser();
        if (principal == null) {
            // Trate o caso onde o usuário não foi encontrado
            throw new BadRequestException("User not found");
        }

        loan.setUser(principal);
        loanRepository.save(loan);
        return loan;
    }


    public Loan update(Long id, Loan loan){
        Loan existingLoan = listById(id);
        loan.setId(id);
        loan.setCreatedAt(existingLoan.getCreatedAt());
        loanRepository.save(loan);
        return listById(id);
    }

    public ResponseEntity<String> delete(Long id) {
        Loan existingLoan = listById(id);
        Long existingLoanId = existingLoan.getId();
        loanRepository.deleteById(existingLoanId);

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
