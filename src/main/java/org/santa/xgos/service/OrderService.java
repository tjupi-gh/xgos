package org.santa.xgos.service;

import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.santa.xgos.repository.OrderRepository;
import org.santa.xgos.vo.OrderViewObject;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class OrderService {

  private final ViewObjectMapper viewObjectMapper;
  private final OrderRepository orderRepository;

  public OrderViewObject save(OrderViewObject rawOrderViewObject) {
    return Optional.ofNullable(rawOrderViewObject)
        .map(viewObjectMapper::map)
        .map(orderRepository::save)
        .map(viewObjectMapper::map)
        .orElseThrow(() -> {
          log.info("Order {} could not be persisted", rawOrderViewObject);
          return new IllegalStateException("");
        });
  }

  public Optional<OrderViewObject> find(String orderId) {
    return Optional.ofNullable(orderId)
        .flatMap(orderRepository::find)
        .map(viewObjectMapper::map);
  }
}
