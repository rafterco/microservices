package com.raftercode.customer;

import com.raftercode.amqp.RabbitMQMessageProducer;
import com.raftercode.clients.fraud.FraudCheckResponse;
import com.raftercode.clients.fraud.FraudClient;
import com.raftercode.clients.notification.NotificationClient;
import com.raftercode.clients.notification.NotificationRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final RestTemplate restTemplate;
    private final FraudClient fraudClient;
    private final RabbitMQMessageProducer rabbitMQMessageProducer;

    public void registerCustomer(CustomerRegistrationRequest customerRegistrationRequest) {
        Customer customer = Customer.builder()
                .firstName(customerRegistrationRequest.firstName())
                .lastName(customerRegistrationRequest.lastName())
                .email(customerRegistrationRequest.email())
                .build();

        //todo: check valid
        //todo: check if email not taken
        customerRepository.saveAndFlush(customer);
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
        //create notification microservice
        //setup erueak server add dependency
        //setup erueka client
        //setup new db in db server
        //add new client in clients module


//        notificationClient.sendNotification(
//                new NotificationRequest(customer.getId(),
//                        customer.getEmail(),
//                        String.format("Welcome %s to raftercode", customer.getFirstName())
//                )
//        );

        NotificationRequest notificationRequest = new NotificationRequest(
                customer.getId(),
                customer.getEmail(),
                String.format("Hi %s welcome to Raftercode ... ",
                        customer.getFirstName())
        );

        rabbitMQMessageProducer.publish(
                notificationRequest,
                "internal.exchange",
                "internal.notification.routing-key"
        );
    }
}
