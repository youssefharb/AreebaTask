package com.youssef.validator.controller;


import com.youssef.validator.response.ValidationResponse;
import com.youssef.validator.services.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController

@RequestMapping(value="/validator")
public class ValidatorController {
    @Autowired
    Validate validate;
@GetMapping("/{phoneNumber}")
public ResponseEntity<ValidationResponse> getValidation(@PathVariable ("phoneNumber") String phoneNumber){
    try {
        ValidationResponse validationResponse= validate.check(phoneNumber);

        return new ResponseEntity(validationResponse, HttpStatus.OK);
    }
    catch(Exception ex) {
        return new ResponseEntity( HttpStatus.INTERNAL_SERVER_ERROR);

    }
}

}
