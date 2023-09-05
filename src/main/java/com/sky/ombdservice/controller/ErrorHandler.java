package com.sky.ombdservice.controller;
import javax.validation.ConstraintViolationException;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * ErrorWebExceptionHandler that is intended to return ONLY HTTP status codes. Runs after the {
 * @link org.springframework.web.reactive.handler.WebFluxResponseStatusExceptionHandler} with has an Order
 * = 0
 */
@Log4j2
@Order(1)
@Component
public class ErrorHandler implements ErrorWebExceptionHandler {

    /** Returns an error if the response is already committed. 503 otherwise */
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ex.printStackTrace();
        return (ex.getClass().equals(ConstraintViolationException.class))
                ? returnClientError(exchange, ex)
                : returnServerError(exchange, ex);
    }

    private Mono<Void> returnServerError(ServerWebExchange exchange, Throwable ex) {
        ex.printStackTrace();
        return exchange.getResponse().setStatusCode(HttpStatus.SERVICE_UNAVAILABLE)
                ? exchange.getResponse().setComplete()
                : Mono.error(ex);
    }
    private Mono<Void> returnClientError(ServerWebExchange exchange, Throwable ex) {
        ex.printStackTrace();
        return exchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST)
                ? exchange.getResponse().setComplete()
                : Mono.error(ex);
    }

    private Mono<Void> returnBrandAccessError(ServerWebExchange exchange, Throwable ex) {
        ex.printStackTrace();
        return exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN)
                ? exchange.getResponse().setComplete()
                : Mono.error(ex);
    }
}