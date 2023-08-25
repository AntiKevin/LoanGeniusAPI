package br.com.loangenius.api;

import br.com.loangenius.domain.models.Loan;
import br.com.loangenius.application.services.LoanService;
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

import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;
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

    @Test
    public void testGetLoanById() throws Exception {
        // Create a sample loan object
        Loan loan = new Loan();
        loan.setId(1L);
        loan.setAmount(1000.0);
        loan.setInstallments(12.0);
        loan.setInterest(5.0);

        // Mock the behavior of the loanService.getById(id) method
        when(loanService.listById(1L)).thenReturn(loan);

        // Perform the GET request to the endpoint with the ID 1
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/loan/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
    }

    @Test
    public void testUpdateLoan() throws Exception {
        // Create a sample loan object with updated data
        Loan updatedLoan = new Loan();
        updatedLoan.setId(1L);
        updatedLoan.setAmount(2000.0);
        updatedLoan.setInstallments(24.0);
        updatedLoan.setInterest(3.5);

        // Mock the behavior of the loanService.update(id, loan) method
        when(loanService.update(any(Long.class), any(Loan.class))).thenReturn(updatedLoan);

        // Perform the PUT request to the endpoint with the updated loan data
        String requestJson = objectMapper.writeValueAsString(updatedLoan);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/api/loan/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.amount").value(2000.0))
                .andExpect(jsonPath("$.installments").value(24.0))
                .andExpect(jsonPath("$.interest").value(3.5))
                .andReturn();
    }

    @Test
    public void testDeleteLoan() throws Exception {
        // Perform the DELETE request to the endpoint with the ID 1
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/api/loan/1"))
                .andExpect(status().isNoContent())
                .andReturn();

        // Verify that the loanService.delete(id) method was called with the correct ID (1)
        verify(loanService).delete(1L);
    }

}
