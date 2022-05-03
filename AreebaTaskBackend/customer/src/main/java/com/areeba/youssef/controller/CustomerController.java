package com.areeba.youssef.controller;


import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.areeba.youssef.entity.Customer;
import com.areeba.youssef.requests.CustomerAddRequest;
import com.areeba.youssef.requests.CustomerUpdateRequest;
import com.areeba.youssef.responses.CustomerResponse;
import com.areeba.youssef.responses.Resp;
import com.areeba.youssef.service.CustomerService;

import java.util.List;

import javax.ws.rs.POST;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value="/customer")
@Slf4j
public class CustomerController {
    @Autowired
    CustomerService customerService;
    @GetMapping("/getAllCustomers")
    private  ResponseEntity<List<CustomerResponse>>  getAllCustomers(){
        List<CustomerResponse> customerResponseList = customerService.getAllCustomers();
         return new ResponseEntity<>(customerResponseList, HttpStatus.OK);
    }

    @DeleteMapping("/deleteById/{id}")

    private  ResponseEntity<Resp>  deleteById(@PathVariable("id")Long id){
        try {
            Resp resp = customerService.deleteById( id);
            return  new ResponseEntity<>(resp ,HttpStatus.OK);
        }catch(Exception exception){
            return   new ResponseEntity<> (HttpStatus.INTERNAL_SERVER_ERROR);
          }	
    }
    @PutMapping("/updateById")

    private  ResponseEntity<Resp>  updateById(@RequestParam(name = "id")  Long id ,@RequestParam(name = "name")  String name ,@RequestParam(name = "address")  String address,@RequestParam(name = "phone")  String phone){
        CustomerUpdateRequest   customerUpdateRequest = new CustomerUpdateRequest();
        customerUpdateRequest.setId(id);
        customerUpdateRequest.setName(name);
        customerUpdateRequest.setAddress(address);
        customerUpdateRequest.setPhone(phone);

        try {
            Resp resp = customerService.updateById(customerUpdateRequest);
            return  new ResponseEntity<>(resp ,HttpStatus.OK);
        }
        catch(Exception exception){
          return   new ResponseEntity<> (HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }
    @PostMapping("/addCustomer")

    private  ResponseEntity<Resp>  addCustomer(@RequestParam(name = "name")  String name ,@RequestParam(name = "address")  String address,@RequestParam(name = "phone")  String phone){
        CustomerAddRequest customerAddRequest = new CustomerAddRequest();
        customerAddRequest.setName(name);
        customerAddRequest.setAddress(address);
        customerAddRequest.setPhone(phone);
        try {

        	 Resp resp = customerService.addCustomer(customerAddRequest);
              return  new ResponseEntity<>(resp ,HttpStatus.OK);
             
//             else {
//          	   return  new ResponseEntity<>(resp ,HttpStatus.BAD_REQUEST);
//             }
        }
        catch(Exception exception){
            return   new ResponseEntity<> (HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }
    
   
    
    @RequestMapping(value = "/addcustomer1", method = {RequestMethod.POST})
    private  ResponseEntity<Resp> addCustomer1(@RequestBody CustomerAddRequest customerAddRequest){
System.out.println("test");
        try {

           Resp resp = customerService.addCustomer(customerAddRequest);
            return  new ResponseEntity<>(resp ,HttpStatus.OK);
        }
        catch(Exception exception){
            return   new ResponseEntity<> (HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }
    @RequestMapping(value = "/updateById1", method = {RequestMethod.PUT})
    private  ResponseEntity<Resp>  updateById1(@RequestBody Customer customer){
        try {
        	 CustomerUpdateRequest customerUpdateRequest = new CustomerUpdateRequest();
        	 customerUpdateRequest.setName(customer.getName());
        	 customerUpdateRequest.setAddress(customer.getAddress());
        	 customerUpdateRequest.setPhone(customer.getNumber());
        	 customerUpdateRequest.setId(customer.getId());
            Resp resp = customerService.updateById(customerUpdateRequest);
            return  new ResponseEntity<>(resp ,HttpStatus.OK);
        }
        catch(Exception exception){
          return   new ResponseEntity<> (HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }
    @GetMapping
    public List<CustomerResponse> getAllRecords() {
        return customerService.getAllCustomers();
    }


}
