package com.example.order.controller;

import com.example.order.dto.OrderRequest;
import com.example.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String placeOrder(@RequestBody OrderRequest orderRequest) throws IllegalAccessException {
        orderService.placeOrder(orderRequest);

        return "Order Create successfully";
    }

//    public CompletableFuture<String> fallbackMethod(OrderRequest orderRequest, RuntimeException runtimeException) {
//        return CompletableFuture.supplyAsync(() -> "Oops! Something went wrong, please order after some time!");
//    }
}
