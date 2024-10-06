package com.example.exchange;

import com.example.exchange.account.AccountRepository;
import com.example.exchange.account.AccountService;
import com.example.exchange.exchange.ExchangeService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ApiControllerPositiveFlowIntegrationTest {

    @InjectMocks
    private ExchangeService exchangeService;

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private AccountService accountService;

    @Mock
    private AccountRepository accountRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @Order(1)
    public void testCreateUser() throws Exception {
        mockMvc.perform(post("/v1/api/users/create")
                        .param("firstName", "Jan")
                        .param("lastName", "Kowalski"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("Jan"))
                .andExpect(jsonPath("$.lastName").value("Kowalski"));
    }

    @Test
    @Order(2)
    public void testGetUser() throws Exception {
        mockMvc.perform(get("/v1/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Jan"))
                .andExpect(jsonPath("$.lastName").value("Kowalski"));
    }

    @Test
    @Order(3)
    public void testCreateAccount() throws Exception {
        mockMvc.perform(post("/v1/api/accounts/create")
                        .param("userId", "1")
                        .param("initialBalance", "1000.00")
                        .param("currency", "PLN"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.accountId").value(1L))
                .andExpect(jsonPath("$.user.firstName").value("Jan"))
                .andExpect(jsonPath("$.user.lastName").value("Kowalski"))
                .andExpect(jsonPath("$.currency").value("PLN"))
                .andExpect(jsonPath("$.amount").value(1000.00));
    }

    // This test call NBP directly
    @Disabled("Do not use this integration test on other environments than local")
    @Test
    @Order(4)
    public void testExchangeUSDtoPLN() throws Exception {
        mockMvc.perform(post("/v1/api/accounts/exchange")
                        .param("accountId", "1")
                        .param("destinationCurrency", "USD"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountDto.accountId").value(1L))
                .andExpect(jsonPath("$.accountDto.user.firstName").value("Jan"))
                .andExpect(jsonPath("$.accountDto.user.lastName").value("Kowalski"))
                .andExpect(jsonPath("$.accountDto.currency").value("PLN"))
                .andExpect(jsonPath("$.accountDto.amount").value(1000.00))
                .andExpect(jsonPath("$.destinationCurrency").value("USD"))
                .andExpect(jsonPath("$.exchangeRate").value(3.9118))
                .andExpect(jsonPath("$.destinationAmount").value(255.64));
    }

    @Test
    public void testExchangePLNtoUSD() throws Exception {
        // JW
        assert true;
    }

}
