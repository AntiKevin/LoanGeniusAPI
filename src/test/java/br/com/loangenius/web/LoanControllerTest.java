package br.com.loangenius.web;

import br.com.loangenius.entities.Loan;
import br.com.loangenius.services.LoanService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class LoanControllerTest {

    @Autowired
    public MockMvc mockMvc;

    @MockBean
    public LoanService loanService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateLoan() throws Exception {
        Loan loanRequest = new Loan();
        loanRequest.setAmount(1000.0);
        loanRequest.setInstallments(12.0);
        loanRequest.setInterest(5.0);

        Loan createdLoan = new Loan();
        createdLoan.setId(1L);
        createdLoan.setAmount(1000.0);
        createdLoan.setInstallments(12.0);
        createdLoan.setInterest(5.0);

        when(loanService.create(loanRequest)).thenReturn(createdLoan);

        String requestJson = objectMapper.writeValueAsString(loanRequest);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/loan")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andReturn();
    }

    @Test
    public void testGetAllLoans() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/loan"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
    }


}
