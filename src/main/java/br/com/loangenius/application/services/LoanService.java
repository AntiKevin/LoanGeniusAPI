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
        Optional<Loan> loanOptional = loanRepository.findById(id);

        if (loanOptional.isPresent()) {
            Loan loan = loanOptional.get();
            User currentUser = authenticationService.getCurrentUser();
            Long currentUserId = currentUser.getId();
            Long loanUserId = loan.getUser();

            if (loanUserId.equals(currentUserId)) {
                return loan;
            } else {
                throw new BadRequestException("Empréstimo com o ID inserido inacessível!");
            }
        } else {
            throw new BadRequestException("Empréstimo com o ID inserido não existe!");
        }
    }


    public Loan create(Loan loan) {
        User principal = authenticationService.getCurrentUser();
        if (principal == null) {
            throw new BadRequestException("Usuário não encontrado");
        }

        loan.setUser(principal);
        loanRepository.save(loan);
        return loan;
    }


    public Loan update(Long id, Loan loan) {
        Loan existingLoan = listById(id);
        try {
            User currentUser = authenticationService.getCurrentUser();
            loan.setId(id);
            loan.setUser(currentUser);
            loan.setCreatedAt(existingLoan.getCreatedAt());
            loanRepository.save(loan);
            return listById(id);
        } catch (Exception exception) {
            throw new BadRequestException("Erro ao atualizar empréstimo");
        }
    }


    public ResponseEntity<String> delete(Long id) {
        Loan existingLoan = listById(id);
        try{
            Long existingLoanId = existingLoan.getId();
            loanRepository.deleteById(existingLoanId);
            return null;
        }
        catch (Exception exception){
            throw new BadRequestException("erro ao deletar empréstimo");
        }

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
