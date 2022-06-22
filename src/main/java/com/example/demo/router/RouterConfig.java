package com.example.demo.router;

import com.example.demo.handler.CustomerHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RouterConfig {

    @Autowired
    private CustomerHandler customerHandler;

    @Bean
    public RouterFunction<ServerResponse> routerFunction() {
        return RouterFunctions.route()
                .GET("/customers/all", customerHandler::getAllCustomers)
                .GET("/customers/all/stream", customerHandler::getAllCustomerStream)
                .GET("/customers/find/{id}", customerHandler::getCustomerById)
                .POST("/customers/save", customerHandler::saveCustomer)
                .build();
    }
}
