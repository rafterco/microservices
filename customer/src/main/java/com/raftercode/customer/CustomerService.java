package com.raftercode.customer;

import com.raftercode.clients.fraud.FraudCheckResponse;
import com.raftercode.clients.fraud.FraudClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepostory customerRepostory;
    private final RestTemplate restTemplate;
    private final FraudClient fraudClient;
    public void registerCustomer(CustomerRegistrationRequest customerRegistrationRequest) {
        Customer customer = Customer.builder()
                .firstName(customerRegistrationRequest.firstName())
                .lastName(customerRegistrationRequest.lastName())
                .email(customerRegistrationRequest.email())
                .build();

        //todo: check valid
        //todo: check if email not taken
        customerRepostory.saveAndFlush(customer);
        //todo: check if fraudster
        FraudCheckResponse fraudCheckResponse =
                fraudClient.isFraudster(customer.getId());

       /* FraudCheckResponse fraudCheckResponse = restTemplate.getForObject(
                //"http://localhost:8081/api/v1/fraud-check/{customerId}",
                "http://FRAUD/api/v1/fraud-check/{customerId}",
                FraudCheckResponse.class,
                customer.getId()
        );*/
        if (fraudCheckResponse.isFraudster()) {
            throw new IllegalStateException("fraudster");
        }
        //todo: send notification
    }
}
