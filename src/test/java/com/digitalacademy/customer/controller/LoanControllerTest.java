package com.digitalacademy.customer.controller;


import com.digitalacademy.customer.api.LoanApi;
import com.digitalacademy.customer.model.response.GetLoanInfoResponse;
import com.digitalacademy.customer.util.Util;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class LoanControllerTest {

    @Mock
    LoanApi loanApi;

    @InjectMocks
    LoanController loanController;
    
    private MockMvc mvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        loanController = new LoanController(loanApi);
        mvc = MockMvcBuilders.standaloneSetup(loanController).build();
    }

    @DisplayName("Test get loan info should return loan information")
    @Test
    void testGetLoanInfo() throws Exception{
        Long reqId = 1L;

        GetLoanInfoResponse getLoanInfoResponse = new GetLoanInfoResponse();
        getLoanInfoResponse.setId(reqId);
        getLoanInfoResponse.setStatus("OK");
        getLoanInfoResponse.setAccountPayable("x02-222-2200");
        getLoanInfoResponse.setAccountReceivable("x02-222-2200");
        getLoanInfoResponse.setPrincipalAmount(3000000.00);

        when(loanApi.getLoanInfo(reqId)).thenReturn(getLoanInfoResponse);

        MvcResult mvcResult = mvc.perform(get("/loan/" + reqId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andReturn();

        JSONObject resp = new JSONObject(mvcResult.getResponse().getContentAsString());

        assertEquals(1,resp.get("id"));
        assertEquals("OK",resp.get("status").toString());
        assertEquals("x02-222-2200", resp.get("account_payable"));
        assertEquals("x02-222-2200", resp.get("account_receivable"));
        assertEquals(3000000.00, resp.get("principal_amount"));

    }

}
