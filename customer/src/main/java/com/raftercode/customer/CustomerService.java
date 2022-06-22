package com.raftercode.customer;

import org.springframework.stereotype.Service;

@Service
public record CustomerService(CustomerRepostory customerRepostory) {

    public void registerCustomer(CustomerRegistrationRequest customerRegistrationRequest) {
        Customer customer = Customer.builder()
                .firstName(customerRegistrationRequest.firstName())
                .lastName(customerRegistrationRequest.lastName())
                .email(customerRegistrationRequest.email())
                .build();

        //todo: check valid
        //todo: check if email not taken

        customerRepostory.save(customer);
    }
}
