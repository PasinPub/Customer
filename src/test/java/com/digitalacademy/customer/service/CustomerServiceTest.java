package com.digitalacademy.customer.service;


import com.digitalacademy.customer.model.Customer;
import com.digitalacademy.customer.repositories.CustomerRepository;
import com.digitalacademy.customer.support.CustomerSupportTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CustomerServiceTest {
    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    CustomerService customerService;

    public static CustomerSupportTest customerSupportTest;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        customerService = new CustomerService(customerRepository);
    }

    @DisplayName("Test get all customer should return list")
    @Test
    void testGetAllCustomer(){

        when(customerRepository.findAll()).thenReturn(CustomerSupportTest.getCustomerList());

        List<Customer> resp = customerService.getCustomerList();

        assertEquals(1,resp.get(0).getId().intValue());
        assertEquals("Ryan",resp.get(0).getFirstName());
        assertEquals("lol",resp.get(0).getLastName());
        assertEquals("6668345798345",resp.get(0).getPhoneNo());
        assertEquals("Ryan.lol@hot.com",resp.get(0).getEmail());
        assertEquals(23,resp.get(0).getAge().intValue());

        assertEquals(2,resp.get(1).getId().intValue());
        assertEquals("GG",resp.get(1).getFirstName());
        assertEquals("WP",resp.get(1).getLastName());
        assertEquals("6668345798354",resp.get(1).getPhoneNo());
        assertEquals("GG.WP@hot.com",resp.get(1).getEmail());
        assertEquals(40,resp.get(1).getAge().intValue());

    }
    @DisplayName("Test get customer by  id should return customer")
    @Test
    void testGetCustomerById() {
        Long reqParam = 1l;

        when(customerRepository.findAllById(reqParam)).thenReturn(CustomerSupportTest.getCustomerList().get(0));

        Customer resp = customerService.getCustomerById(reqParam);

        assertEquals(1,resp.getId().intValue());
        assertEquals("Ryan",resp.getFirstName());
        assertEquals("lol",resp.getLastName());
        assertEquals("6668345798345",resp.getPhoneNo());
        assertEquals("Ryan.lol@hot.com",resp.getEmail());
        assertEquals(23,resp.getAge().intValue());
//
//        assertEquals(2,resp.getId().intValue());
//        assertEquals("GG",resp.getFirstName());
//        assertEquals("WP",resp.getLastName());
//        assertEquals("6668345798354",resp.getPhoneNo());
//        assertEquals("GG.WP@hot.com",resp.getEmail());
//        assertEquals(40,resp.getAge().intValue());


    }

    @DisplayName("Test create customer should return new customer")
    @Test
    void testCreateCustomer(){

        when(customerRepository.save(CustomerSupportTest.getReqNewCustomer())).thenReturn(CustomerSupportTest.getResNewCustomer());

        Customer resp = customerService.createCustomer(CustomerSupportTest.getReqNewCustomer());
        assertEquals(1, resp.getId().intValue());
        assertEquals("New Name", resp.getFirstName());
        assertEquals("New LastName", resp.getLastName());
        assertEquals("123456789", resp.getPhoneNo());
        assertEquals("new@new.com", resp.getEmail());
        assertEquals(15,resp.getAge().intValue());
    }

    @DisplayName("Test update customer should return success")
    @Test
    void testUpdateCustomer(){
        Long reqId = 2L;

        when(customerRepository.findAllById(reqId)).thenReturn(CustomerSupportTest.getOldCustomer());
        when(customerRepository.save(CustomerSupportTest.getNewCustomer())).thenReturn(CustomerSupportTest.getNewCustomer());

        Customer resp = customerService.updateCustomer(reqId, CustomerSupportTest.getNewCustomer());
        assertEquals(2, resp.getId().intValue());
        assertEquals("Noon",resp.getFirstName());
        assertEquals("Bow",resp.getLastName());
        assertEquals("123456789",resp.getPhoneNo());
        assertEquals("bow@bow.com",resp.getEmail());
        assertEquals(5,resp.getAge().intValue());


    }

    @DisplayName("Test update should return fail")
    @Test
    void testUpdateCustomerFail(){
        Long reqId = 4L;

        when(customerRepository.findAllById(reqId)).thenReturn(null);
        Customer resp = customerService.updateCustomer(reqId, CustomerSupportTest.getUpdatefailCustomer());
        assertEquals(null, resp);
    }

    @DisplayName("Test delete customer, should return true")
    @Test
    void testDeleteCustomer(){
        Long reqId = 1L;
        doNothing().when(customerRepository).deleteById(reqId);
        boolean resp = customerService.deleteById(reqId);
        assertEquals(true,resp);
        assertTrue(resp);
        assertTrue(customerService.deleteById(reqId));
    }

    @DisplayName("Test delete customer, should return fail")
    @Test
    void testDeleteCustomerFail(){
        Long reqId = 1L;
        doThrow(EmptyResultDataAccessException.class).when(customerRepository).deleteById(reqId);
        boolean resp = customerService.deleteById(reqId);
        assertEquals(false,resp);
        assertFalse(resp);
        assertFalse(customerService.deleteById(reqId));
    }
}
