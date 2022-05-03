package com.areeba.youssef.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.areeba.youssef.entity.Customer;

import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface CustomerRepos  extends JpaRepository<Customer,Long> {

Optional<Customer> findByNumber(String number);

}
