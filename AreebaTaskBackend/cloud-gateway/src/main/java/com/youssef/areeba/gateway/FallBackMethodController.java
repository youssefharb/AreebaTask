package com.youssef.areeba.gateway;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@CrossOrigin(origins = "*")
@RestController
public class FallBackMethodController {

    @GetMapping("/customerServiceFallBack")
    public String customerServiceFallBackMethod() {
        return "Customer Service is taking longer than Expected." +
                " Please try again later";
    }

    @GetMapping("/validatorServiceFallBack")
    public String validationServiceFallBackMethod() {
        return "validator Service is taking longer than Expected." +
                " Please try again later";
    }
}
