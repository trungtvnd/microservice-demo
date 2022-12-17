package com.example.order.service;

import com.example.order.dto.InventoryResponse;
import com.example.order.dto.OrderLineItemsDto;
import com.example.order.dto.OrderRequest;
import com.example.order.model.Order;
import com.example.order.model.OrderLineItems;
import com.example.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.startup.WebAnnotationSet;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final WebClient webClient;
    public void placeOrder(OrderRequest orderRequest) throws IllegalAccessException {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderLineItems> orderList = orderRequest.getOrderLineItemsList().stream()
                .map(this::mapToDto).collect(Collectors.toList());
        order.setOrderLineItemsList(orderList);

        List<String> listSkuCode = order.getOrderLineItemsList()
                .stream()
                .map(OrderLineItems::getSkuCode).collect(Collectors.toList());

        InventoryResponse[] inventoryResponsesArray = webClient.get()
                .uri("http://localhost:8082/api/inventory"
                        , uriBuilder -> uriBuilder.queryParam("skuCode", listSkuCode).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();

        boolean allProductInStock = Arrays.stream(inventoryResponsesArray).allMatch(InventoryResponse::isInStock);

        if(allProductInStock){
            orderRepository.save(order);
        }else {
            throw new IllegalAccessException("Product is not in stock, please try Again");
        }

    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }
}
