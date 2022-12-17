package com.example.order.dto;

import com.example.order.model.OrderLineItems;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import java.util.List;
@Data
@Getter
@Setter
public class OrderRequest {
    private Long id;
    private String orderNumber;
    private List<OrderLineItemsDto> orderLineItemsList;
}
