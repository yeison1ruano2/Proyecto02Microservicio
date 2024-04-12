package com.msvc.order.service;

import com.msvc.order.dto.InventarioResponse;
import com.msvc.order.dto.OrderLineItemsDto;
import com.msvc.order.dto.OrderRequest;
import com.msvc.order.model.Order;
import com.msvc.order.model.OrderLineItems;
import com.msvc.order.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderService {
  @Autowired
  private OrderRepository orderRepository;
  @Autowired
  private WebClient webClient;
  public void placeOrder(OrderRequest orderRequest) throws IllegalAccessException {
    Order order= new Order();
    order.setNumeroPedido(UUID.randomUUID().toString());
    List<OrderLineItems> orderLineItems= orderRequest.getOrderLineItemsDtoList()
            .stream()
            .map(this::mapToDto)
            .collect(Collectors.toList());
    order.setOrderLineItems(orderLineItems);
    List<String> codigoSku = order.getOrderLineItems().stream()
                    .map(OrderLineItems::getCodigoSku)
                            .collect(Collectors.toList());
    InventarioResponse[] inventarioResponseArray = webClient.get()
                    .uri("http://localhost:8082/api/inventario",uriBuilder -> uriBuilder.queryParam("codigoSku",codigoSku).build())
                    .retrieve()
                    .bodyToMono(InventarioResponse[].class).block();
    boolean allProductosInStock= Arrays.stream(inventarioResponseArray)
                    .allMatch(InventarioResponse::isInStock);
    if(!allProductosInStock){
      throw new IllegalAccessException("El producto no esta en stock");
    }
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
