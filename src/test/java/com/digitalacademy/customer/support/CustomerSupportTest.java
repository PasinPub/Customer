package com.digitalacademy.customer.support;

import com.digitalacademy.customer.model.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerSupportTest {
    public static List<Customer> getCustomerList(){
        List<Customer> customerList = new ArrayList<>();
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("Ryan");
        customer.setLastName("lol");
        customer.setPhoneNo("6668345798345");
        customer.setEmail("Ryan.lol@hot.com");
        customer.setAge(23);
        customerList.add(customer);

        customer = new Customer();
        customer.setId(2L);
        customer.setFirstName("GG");
        customer.setLastName("WP");
        customer.setPhoneNo("6668345798354");
        customer.setEmail("GG.WP@hot.com");
        customer.setAge(40);
        customerList.add(customer);
        return customerList;
    }

    public static Customer getReqNewCustomer(){
        Customer customerReq = new Customer();
        customerReq.setFirstName("New Name");
        customerReq.setLastName("New LastName");
        customerReq.setPhoneNo("123456789");
        customerReq.setEmail("new@new.com");
        customerReq.setAge(15);
        return customerReq;
    }

    public static Customer getResNewCustomer(){
        Customer customerReturn = new Customer();
        customerReturn.setId(1L);
        customerReturn.setFirstName("New Name");
        customerReturn.setLastName("New LastName");
        customerReturn.setPhoneNo("123456789");
        customerReturn.setEmail("new@new.com");
        customerReturn.setAge(15);
        return customerReturn;
    }

    public static Customer getOldCustomer(){
        Customer oldCustomer = new Customer();
        oldCustomer.setId(2L);
        oldCustomer.setFirstName("OldNoon");
        oldCustomer.setLastName("OldBow");
        oldCustomer.setEmail("old@old.com");
        oldCustomer.setPhoneNo("123456789");
        oldCustomer.setAge(20);
        return oldCustomer;
    }

    public static Customer getNewCustomer(){
        Customer reqCustomer = new Customer();
        reqCustomer.setId(2L);
        reqCustomer.setFirstName("Noon");
        reqCustomer.setLastName("Bow");
        reqCustomer.setEmail("bow@bow.com");
        reqCustomer.setPhoneNo("123456789");
        reqCustomer.setAge(5);
        return reqCustomer;
    }

    public static Customer getUpdatefailCustomer() {
        Customer reqCustomer = new Customer();
        reqCustomer.setId(1L);
        reqCustomer.setFirstName("Noon");
        reqCustomer.setLastName("Bow");
        reqCustomer.setEmail("bow@test.com");
        reqCustomer.setPhoneNo("123456789");
        return reqCustomer;
    }

}
