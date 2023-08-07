package br.com.loangenius.web;

import br.com.loangenius.entities.loan.Loan;
import br.com.loangenius.services.LoanService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    Loan listById(@PathVariable("id") Long id){
        return loanService.listById(id);
    }

    @PostMapping
    public ResponseEntity<Loan> createLoan(@RequestBody @Valid Loan loan) {
        Loan createdLoan = loanService.create(loan);
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(createdLoan);
    }


    @PutMapping("{id}")
    Loan update(@PathVariable Long id, @RequestBody Loan loan){
        return loanService.update(id, loan);
    }

    @DeleteMapping("{id}")
    ResponseEntity<String> delete(@PathVariable("id") Long id){
        loanService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
