package br.com.loangenius.web;

import br.com.loangenius.entities.Loan;
import br.com.loangenius.services.LoanService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loan")
public class LoanController {
    private LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @GetMapping
    List<Loan> list(){
        return loanService.list();
    }

    @GetMapping("{id}")
    List<Loan> listById(@PathVariable("id") Long id){
        return loanService.listById(id);
    }

    @PostMapping
    List<Loan> create(@RequestBody @Valid Loan loan){
        return loanService.create(loan);
    }

    @PutMapping("{id}")
    List<Loan> update(@PathVariable Long id, @RequestBody Loan loan){
        return loanService.update(id, loan);
    }

    @DeleteMapping("{id}")
    List<Loan> delete(@PathVariable("id") Long id){
        return loanService.delete(id);
    }
}
