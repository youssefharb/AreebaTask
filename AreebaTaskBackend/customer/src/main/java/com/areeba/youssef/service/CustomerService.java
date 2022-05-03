package com.areeba.youssef.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.jni.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.areeba.youssef.entity.Customer;
import com.areeba.youssef.repository.CustomerRepos;
import com.areeba.youssef.requests.CustomerAddRequest;
import com.areeba.youssef.requests.CustomerUpdateRequest;
import com.areeba.youssef.responses.CustomerResponse;
import com.areeba.youssef.responses.Resp;
import com.areeba.youssef.responses.Validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CustomerService {
    @Autowired
    CustomerRepos customerRepos;
    @Autowired
    private RestTemplate restTemplate;

    public List<CustomerResponse> getAllCustomers() {
        List<CustomerResponse> customerResponseList = new ArrayList<>();
        List<Customer> customers = customerRepos.findAll();
        customers.stream().forEach(c -> {
            CustomerResponse customerResponse = new CustomerResponse();
            customerResponse.setAddress(c.getAddress());
            customerResponse.setId(c.getId());
            customerResponse.setPhone(c.getNumber());
            customerResponse.setName(c.getName());
            customerResponseList.add(customerResponse);

        });
        return customerResponseList;
    }


    public Resp deleteById(Long id) {
    	 Resp resp = new Resp();
        Optional<Customer> customerOptional = customerRepos.findById(id);
        if (customerOptional.isPresent()) {
            customerRepos.deleteById(id);
            resp.setCode(11);
            resp.setDescription("Delete Was Succesful");
           return resp;
        } else {
        	resp.setCode(12);
        	resp.setDescription("Something Went Wrong");
            return resp;

        }

    }


    public Resp updateById(CustomerUpdateRequest customerUpdateRequest) {
        Resp resp = new Resp();
        boolean canUpdate = true;
        
        if (customerUpdateRequest.getId() != null) {
            Optional<Customer> customerOptional = customerRepos.findById(customerUpdateRequest.getId());
            if (customerOptional.isPresent()) {
                Customer customer = customerOptional.get();
                if (customerUpdateRequest.getAddress() == null || customerUpdateRequest.getAddress().isEmpty()) {
                    resp.setCode(7);
                    resp.setDescription("empty address update failed");
                    canUpdate = false;
                } else {
                    customer.setAddress(customerUpdateRequest.getAddress());

                }
                if (customerUpdateRequest.getName() == null || customerUpdateRequest.getName().isEmpty()) {
                    resp.setCode(7);
                    canUpdate = false;

                    resp.setDescription("empty name update failed");
                } else {

                    customer.setName(customerUpdateRequest.getName());

                }
                if (customerUpdateRequest.getPhone() == null || customerUpdateRequest.getPhone().isEmpty()) {
                    canUpdate = false;

                    resp.setCode(8);
                    resp.setDescription("empty phone number update failed");
                } else {
                    if (!isValid(customerUpdateRequest.getPhone())) {
                        resp.setCode(9);
                        resp.setDescription("invalid phone number");
                        canUpdate = false;

                    } else {
                        customer.setNumber(customerUpdateRequest.getPhone());

                    }

                }
                if (canUpdate) {

                    customerRepos.save(customer);
                    resp.setCode(2);
                    resp.setDescription("update success");
                }
            } else {
                resp.setCode(4);
                resp.setDescription("customer not found");
            }

        } else {
            resp.setCode(5);
            resp.setDescription("invalid id ");

        }
        return resp;
        
		
    }


    private boolean isValid(String number) {
        Validation validation =
                restTemplate.getForObject("http://VALIDATOR-SERVICE/validator/" + number
                        , Validation.class);
        if (validation.getCountryName() == null||validation.getCountryName().isEmpty()) {

            return false;
        }
        return true;

    }

    public Resp addCustomer(CustomerAddRequest customerAddRequest) {
        Resp resp = new Resp();

        Customer customer = new Customer();
        boolean canAdd = true;
        if (customerAddRequest.getAddress() == null || customerAddRequest.getAddress().isEmpty()) {
            resp.setCode(7);
            resp.setDescription("empty address add failed");
            canAdd = false;
        } else {
            customer.setAddress(customerAddRequest.getAddress());

        }
        if (customerAddRequest.getName() == null || customerAddRequest.getName().isEmpty()) {
            resp.setCode(10);
            canAdd = false;

            resp.setDescription("empty name add failed");
        } else {

            customer.setName(customerAddRequest.getName());

        }
        if (customerAddRequest.getPhone() == null || customerAddRequest.getPhone().isEmpty()) {
            canAdd = false;

            resp.setCode(8);
            resp.setDescription("empty phone number add failed");
        } else {
           if (!isValid(customerAddRequest.getPhone())) {
                resp.setCode(9);
                resp.setDescription("invalid phone number");
                canAdd = false;

            } else {
                if(numberExist(customerAddRequest.getPhone())){
                    resp.setCode(11);
                    resp.setDescription(" phone number is already exist ");
                    canAdd = false;
                }else{
                    customer.setNumber(customerAddRequest.getPhone());

                }

            }

        }
        if (canAdd) {

            customerRepos.save(customer);
            resp.setCode(2);
            resp.setDescription("add success");
        }


        return resp;


    }

    private boolean numberExist(String number){
        if (customerRepos.findByNumber(number).isPresent()){
            return  true;
        }
        return false;
    }
}
