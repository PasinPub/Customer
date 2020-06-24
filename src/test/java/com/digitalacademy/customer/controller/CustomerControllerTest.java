package com.digitalacademy.customer.controller;

import com.digitalacademy.customer.model.Customer;
import com.digitalacademy.customer.service.CustomerService;
import com.digitalacademy.customer.support.CustomerSupportTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.json.JSONArray;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    @InjectMocks
    CustomerController customerController;

    private MockMvc mvc;

    public static final String urlCustomerList = "/customer/list/";
    public static final String urlCustomer = "/customer/";
    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        customerController = new CustomerController(customerService);
        mvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @DisplayName("Test get customer should return customer list")
    @Test
    void testGetCustomerList() throws Exception{
        when(customerService.getCustomerList())
                .thenReturn(CustomerSupportTest.getCustomerList());

        MvcResult mvcResult = mvc.perform(get(urlCustomerList))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)).andReturn();

        JSONArray jsonArray = new JSONArray(mvcResult.getResponse().getContentAsString());
        // mock 1
        assertEquals("1", jsonArray.getJSONObject(0).get("id").toString());
        assertEquals("Ryan", jsonArray.getJSONObject(0).get("firstName"));
        assertEquals("lol", jsonArray.getJSONObject(0).get("lastName"));
        assertEquals("6668345798345", jsonArray.getJSONObject(0).get("phoneNo"));
        assertEquals("Ryan.lol@hot.com", jsonArray.getJSONObject(0).get("email"));
        assertEquals(23, jsonArray.getJSONObject(0).get("age"));
        // mock 2
        assertEquals("2", jsonArray.getJSONObject(1).get("id").toString());
        assertEquals("GG", jsonArray.getJSONObject(1).get("firstName"));
        assertEquals("WP", jsonArray.getJSONObject(1).get("lastName"));
        assertEquals("6668345798354", jsonArray.getJSONObject(1).get("phoneNo"));
        assertEquals("GG.WP@hot.com", jsonArray.getJSONObject(1).get("email"));
        assertEquals(40, jsonArray.getJSONObject(1).get("age"));
    }

    @DisplayName("Test get customer by id should return customer")
    @Test
    void testGetCustomerById() throws Exception{
        Long reqId = 2L;
        when(customerService.getCustomerById(reqId)).thenReturn(CustomerSupportTest.getOldCustomer());

        MvcResult mvcResult = mvc.perform(get(urlCustomer + "" + reqId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andReturn();

        JSONObject jsonObject = new JSONObject(mvcResult.getResponse().getContentAsString());
        assertEquals("2",jsonObject.get("id").toString());
        assertEquals("OldNoon",jsonObject.get("firstName"));
        assertEquals("OldBow",jsonObject.get("lastName"));
        assertEquals("123456789",jsonObject.get("phoneNo"));
        assertEquals("old@old.com",jsonObject.get("email"));
        assertEquals(20,jsonObject.get("age"));
    }


    @DisplayName("Test get customer by id should return not found")
    @Test
    void testGetCustomerByIdNotFound() throws Exception{
        Long reqId = 5L;
        MvcResult mvcResult = mvc.perform(get(urlCustomerList + "" + reqId))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @DisplayName("Test get customer by name should return customer")
    @Test
    void testGetCustomerByName() throws Exception{
        String reqName = "Ryan" ;
        when(customerService.getCustomerByName(reqName)).thenReturn(CustomerSupportTest.getCustomerList());

        MvcResult mvcResult = mvc.perform(get(urlCustomer + "?name=" + reqName))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andReturn();

        JSONArray jsonArray = new JSONArray(mvcResult.getResponse().getContentAsString());
        assertEquals("1",jsonArray.getJSONObject(0).get("id").toString());
        assertEquals("Ryan",jsonArray.getJSONObject(0).get("firstName"));
        assertEquals("lol",jsonArray.getJSONObject(0).get("lastName"));
        assertEquals("6668345798345",jsonArray.getJSONObject(0).get("phoneNo"));
        assertEquals("Ryan.lol@hot.com",jsonArray.getJSONObject(0).get("email"));
        assertEquals(23,jsonArray.getJSONObject(0).get("age"));
    }

    @DisplayName("Test get customer by name should return not found")
    @Test
    void testGetCustomerByNameNotFound() throws Exception{
        String reqName = "";
        MvcResult mvcResult = mvc.perform(get(urlCustomer + "?name=" + reqName))
                .andExpect(status().isNotFound())
                .andReturn();
    }


    @DisplayName("Test Create Customer Should return success")
    @Test
    void testCreateCustomer() throws Exception{
        Customer reqCustomer = CustomerSupportTest.getReqNewCustomer();

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(reqCustomer);

        when(customerService.createCustomer(reqCustomer))
                .thenReturn(CustomerSupportTest.getResNewCustomer());

        MvcResult mvcResult = mvc.perform(post(urlCustomer)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
                ).andExpect(status().isCreated())
                .andReturn();

        JSONObject jsonObject = new JSONObject(mvcResult.getResponse().getContentAsString());
        assertEquals("1",jsonObject.get("id").toString());
        assertEquals("New Name",jsonObject.get("firstName"));
        assertEquals("New LastName",jsonObject.get("lastName"));
        assertEquals("123456789",jsonObject.get("phoneNo"));
        assertEquals("new@new.com",jsonObject.get("email"));
        assertEquals(15,jsonObject.get("age"));
    }


    @DisplayName("Test Create Customer with firstname is empty")
    @Test
    void testCreateCustomerWithNameIdEmpty() throws Exception{
        Customer reqCustomer = CustomerSupportTest.getReqNewCustomer();
        reqCustomer.setFirstName("");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(reqCustomer);

        when(customerService.createCustomer(reqCustomer))
                .thenReturn(CustomerSupportTest.getResNewCustomer());

        MvcResult mvcResult = mvc.perform(post(urlCustomer)
        .contentType(MediaType.APPLICATION_JSON)
        .content(requestJson)
        ).andExpect(status().isBadRequest())
                .andReturn();

        assertEquals("Validation failed for argument [0] in public org.springframework.http.ResponseEntity<?> com.digitalacademy.customer.controller.CustomerController.createCustomer(com.digitalacademy.customer.model.Customer): [Field error in object 'customer' on field 'firstName': rejected value []; codes [Size.customer.firstName,Size.firstName,Size.java.lang.String,Size]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [customer.firstName,firstName]; arguments []; default message [firstName],100,1]; default message [Please type your first name size 1-100]] ", mvcResult.getResolvedException().getMessage());

    }


    @DisplayName("Test update customer should return success")
    @Test
    void testUpdateCustomer() throws Exception {
        Customer reqCustomer = CustomerSupportTest.getOldCustomer();
        Long reqId = 2L;


        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE,false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(reqCustomer);

        when(customerService.updateCustomer(reqId,reqCustomer))
                .thenReturn(CustomerSupportTest.getNewCustomer());

        MvcResult mvcResult = mvc.perform(put(urlCustomer+""+reqId)
        .contentType(MediaType.APPLICATION_JSON)
        .content(requestJson)
        ).andExpect(status().isOk())
                .andReturn();

        JSONObject jsonObject = new JSONObject(mvcResult.getResponse().getContentAsString());
        assertEquals("2", jsonObject.get("id").toString());
        assertEquals("Noon", jsonObject.get("firstName"));
        assertEquals("Bow", jsonObject.get("lastName"));
        assertEquals("123456789", jsonObject.get("phoneNo"));
        assertEquals("bow@bow.com", jsonObject.get("email"));
        assertEquals(5, jsonObject.get("age"));
    }

    @DisplayName("Test update customer should return id not found")
    @Test
    void testUpdateCustomerIdNotFound() throws Exception {
        Customer reqCustomer = CustomerSupportTest.getOldCustomer();
        Long reqId = 2L;

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE,false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(reqCustomer);

        when(customerService.updateCustomer(reqId,reqCustomer))
                .thenReturn(null);

        MvcResult mvcResult = mvc.perform(put(urlCustomer+""+reqId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
        ).andExpect(status().isNotFound())
                .andReturn();

        verify(customerService, times(1)).updateCustomer(reqId,reqCustomer);

    }

    @DisplayName("Test delete customer should success")
    @Test
    void testDeleteCustomer() throws Exception {
        Long reqId = 4L;
        when(customerService.deleteById(reqId)).thenReturn(true);

        MvcResult mvcResult = mvc.perform(delete(urlCustomer +""+ reqId)
                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andReturn();

        verify(customerService, times(1)).deleteById(reqId);
    }

}