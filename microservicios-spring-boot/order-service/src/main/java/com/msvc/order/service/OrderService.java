package com.msvc.order.service;

import com.msvc.order.dto.OrderLineItemsDto;
import com.msvc.order.dto.OrderRequest;
import com.msvc.order.model.Order;
import com.msvc.order.model.OrderLineItems;
import com.msvc.order.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderService {
  @Autowired
  private OrderRepository orderRepository;
  public void placeOrder(OrderRequest orderRequest){
    Order order= new Order();
    order.setNumeroPedido(UUID.randomUUID().toString());
    List<OrderLineItems> orderLineItems= orderRequest.getOrderLineItemsDtoList()
            .stream()
            .map(this::mapToDto)
            .collect(Collectors.toList());
    order.setOrderLineItems(orderLineItems);
    orderRepository.save(order);
  }

  private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto){
    OrderLineItems orderLineItems= new OrderLineItems();
    orderLineItems.setPrecio(orderLineItemsDto.getPrecio());
    orderLineItems.setCantidad(orderLineItemsDto.getCantidad());
    orderLineItems.setCodigoSku(orderLineItemsDto.getCodigoSku());
    return orderLineItems;
  }
}
