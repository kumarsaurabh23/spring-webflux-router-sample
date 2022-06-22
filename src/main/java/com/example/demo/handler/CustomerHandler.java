package com.example.demo.handler;

import com.example.demo.dao.CustomerDao;
import com.example.demo.dto.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class CustomerHandler {

    @Autowired
    private CustomerDao customerDao;

    public Mono<ServerResponse> getAllCustomers(ServerRequest request) {
        return ServerResponse.ok().body(customerDao.getCustomerList(), Customer.class);
    }

    public Mono<ServerResponse> getAllCustomerStream(ServerRequest request) {
        return ServerResponse.ok().
                contentType(MediaType.TEXT_EVENT_STREAM).
                body(customerDao.getCustomerStream(), Customer.class);
    }

    public Mono<ServerResponse> getCustomerById(ServerRequest request) {
        return ServerResponse.ok().body(customerDao.getCustomerList().
                filter(c-> Integer.toString(c.getId()).equals(request.pathVariable("id"))).
                take(1).single(),
                Customer.class);
    }

    public Mono<ServerResponse> saveCustomer(ServerRequest request) {
        return ServerResponse.ok().body(request.bodyToMono(Customer.class).map(c -> c.toString()), String.class);
    }
}
