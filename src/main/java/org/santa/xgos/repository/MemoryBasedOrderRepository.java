package org.santa.xgos.repository;

import static java.lang.String.format;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import lombok.AllArgsConstructor;
import lombok.val;
import org.santa.xgos.model.Order;
import org.springframework.stereotype.Repository;

@AllArgsConstructor
@Repository
public class MemoryBasedOrderRepository implements
    OrderRepository {

  private final ConcurrentMap<String, Order> orders = new ConcurrentHashMap<>();

  @Override
  public Order save(Order order) {
    val orderId = Optional.ofNullable(order.getId())
        .orElseThrow(() -> new IllegalArgumentException("Id is mandatory"));

    val prevOrder = orders.putIfAbsent(orderId, order);
    if (prevOrder != null) {
      throw new IllegalArgumentException(
          format("Order with id='%s' already exists %s", orderId, order));
    }
    return order;
  }

  @Override
  public Optional<Order> find(String orderId) {
    return Optional.ofNullable(orders.get(orderId));
  }
}
