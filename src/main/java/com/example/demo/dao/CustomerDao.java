package com.example.demo.dao;

import com.example.demo.dto.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@Slf4j
public class CustomerDao {

    public List<Customer> getCustomers() {
        return IntStream.rangeClosed(1,50).
                peek(CustomerDao::sleepExecution).
                peek(i -> log.info("processing count : {}", i)).
                mapToObj(i -> new Customer(i, "customer" + i)).
                collect(Collectors.toList());
    }

    private static void sleepExecution(int i) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            log.error(e.getLocalizedMessage());
        }
    }

    public Flux<Customer> getCustomerStream() {
        return Flux.range(1,10).
                delayElements(Duration.ofSeconds(1)).
                doOnNext(i -> log.info("processing count : {}", i)).
                map(i -> new Customer(i, "customer" + i));
    }

    public Flux<Customer> getCustomerList() {
        return Flux.range(1,10).
                doOnNext(i -> log.info("processing count : {}", i)).
                map(i -> new Customer(i, "customer" + i));
    }
}
