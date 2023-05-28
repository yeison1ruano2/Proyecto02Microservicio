package com.msvc.order.controller;

import com.msvc.order.dto.OrderRequest;
import com.msvc.order.model.Order;
import com.msvc.order.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/order")
public class OrderController {

  @Autowired
  private OrderService orderService;



  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @CircuitBreaker(name="inventario",fallbackMethod = "falBackMethod")
  @TimeLimiter(name="inventario")
  @Retry(name="inventario")
  public CompletableFuture<String> realizarPedido(@RequestBody OrderRequest orderRequest){
    return CompletableFuture.supplyAsync(()-> orderService.placeOrder(orderRequest));
  }

  public CompletableFuture<String> fallBackMethod(OrderRequest orderRequest, RuntimeException runtimeException){
    return CompletableFuture.supplyAsync(()-> "Oops! Ha ocurrido un error al realizar el pedido");

  }
}
