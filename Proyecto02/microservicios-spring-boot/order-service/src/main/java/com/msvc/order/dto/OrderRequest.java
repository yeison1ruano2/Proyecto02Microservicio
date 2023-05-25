package com.msvc.order.dto;

import com.msvc.order.model.OrderLineItems;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {

  private List<OrderLineItemsDto> orderLineItemsDtoList;
}
