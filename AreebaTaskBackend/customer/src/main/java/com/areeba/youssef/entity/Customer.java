package com.areeba.youssef.entity;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name = "customers")
@EntityListeners(AuditingEntityListener.class)

public class Customer {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id ;
   private String name ;
   private String number ;
   private String address ;

   public Customer(){
	      
	   }
   public Customer(Long i, String name, String address, String number) {
	// TODO Auto-generated constructor stub
	   id=i;
	   this.name=name;
	   this.address=address;
	   this.number=number;
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

   public String getNumber() {
      return number;
   }

   public void setNumber(String number) {
      this.number = number;
   }

   public String getAddress() {
      return address;
   }

   public void setAddress(String address) {
      this.address = address;
   }
}
