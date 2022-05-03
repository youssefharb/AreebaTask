package com.areeba.youssef;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.areeba.youssef.controller.CustomerController;
import com.areeba.youssef.entity.Customer;
import com.areeba.youssef.repository.CustomerRepos;
import com.areeba.youssef.requests.CustomerAddRequest;
import com.areeba.youssef.responses.CustomerResponse;
import com.areeba.youssef.responses.Resp;
import com.areeba.youssef.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;

import javassist.NotFoundException;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;
    
    @MockBean
    CustomerRepos customerRepos;
    
    @MockBean
    CustomerService customerService;
    
    Customer RECORD_1 = new Customer((long)1, "test1","test2","96170808228");
    Customer RECORD_2 = new Customer((long)2, "test1","test2","96170808228");
    Customer RECORD_3 = new Customer((long)3,"test1","test2","96170808228");
    
    CustomerResponse RECORD_11 = new CustomerResponse((long)1, "test1","test2","96170808228");
    CustomerResponse RECORD_22 = new CustomerResponse((long)2, "test1","test2","96170808228");
    CustomerResponse RECORD_33 = new CustomerResponse((long)3,"test1","test2","96170808228");
    
    
    @Test
    public void getAllRecords_success1() throws Exception {
        List<CustomerResponse> records = new ArrayList<>(Arrays.asList(RECORD_11, RECORD_22, RECORD_33));
        
        Mockito.when(customerService.getAllCustomers()).thenReturn(records);
        System.out.println("runninggg");
        mockMvc.perform(MockMvcRequestBuilders
                .get("/customer")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[2].name", is("test1")));
    }
  
//    @Test
//    public void getAllRecords_success() throws Exception {
//        List<Customer> records = new ArrayList<>(Arrays.asList(RECORD_1, RECORD_2, RECORD_3));
//        
//        Mockito.when(customerRepos.findAll()).thenReturn(records);
//        System.out.println("runninggg");
//        mockMvc.perform(MockMvcRequestBuilders
//                .get("/customer")
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//                //.andExpect(jsonPath("$", hasSize(3)))
//                //.andExpect(jsonPath("$[2].name", is("Jane Doe")));
//    }
    
    @Test
    public void createRecord_success() throws Exception {
    	
    	 Resp resp = new Resp();
    	 resp.setCode(4);
    	 resp.setDescription("test");
        CustomerAddRequest customerAddRequest = new CustomerAddRequest();
        customerAddRequest.setName("John Doe");
        customerAddRequest.setAddress("test");
        customerAddRequest.setPhone("96170808228");

        Mockito.when(customerService.addCustomer(customerAddRequest)).thenReturn(resp);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/customer/addcustomer1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(customerAddRequest));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                //.andExpect(jsonPath("$", notNullValue()))
               .andExpect(jsonPath("$.code", is("John Doe")));
        }
    @Test
    public void deletePatientById_success() throws Exception {
    	Resp resp = new Resp();
    	resp.setCode(4);
    	resp.setDescription("success");
    	
        Mockito.when(customerService.deleteById(RECORD_2.getId())).thenReturn(resp);

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/customer/deleteById/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(4)));
    }
    
    @Test
    public void updatePatientRecord_success() throws Exception {
    	 Customer customer = new Customer();
         customer.setName("test");
         customer.setAddress("test");
         customer.setNumber("96170808228");
         customer.setId((long)1);


        Mockito.when(customerRepos.findById(RECORD_1.getId())).thenReturn(Optional.of(RECORD_1));
        Mockito.when(customerRepos.save(customer)).thenReturn(customer);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/customer/updateById1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(customer));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk());
                //.andExpect(jsonPath("$", notNullValue()))
                //.andExpect(jsonPath("$.name", is("Rayven Zambo")));
    }
}