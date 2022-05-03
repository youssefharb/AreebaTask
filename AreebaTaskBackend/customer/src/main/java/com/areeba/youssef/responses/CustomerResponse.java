package com.areeba.youssef.responses;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class CustomerResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id ;

    private  String name ;
    private   String address ;
    private  String phone ;

    public CustomerResponse(){
	      
	   }
public CustomerResponse(Long i, String name, String address, String number) {
	// TODO Auto-generated constructor stub
	   id=i;
	   this.name=name;
	   this.address=address;
	   this.phone=number;
}
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
