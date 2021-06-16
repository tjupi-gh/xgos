package org.santa.xgos.repository;

import java.util.Optional;
import org.santa.xgos.model.Order;

public interface OrderRepository {

  Order save(Order order);

  Optional<Order> find(String orderId);
}
