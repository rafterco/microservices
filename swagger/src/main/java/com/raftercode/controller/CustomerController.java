package com.raftercode.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/customers")
public class CustomerController {

    @PostMapping("/register")
    public void registerCustomer(String str) {
        System.out.println("new customer registration " + str);
        //customerService.registerCustomer(customerRegistrationRequest);
    }
}
