package org.santa.xgos.repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.UUID;
import lombok.val;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.santa.xgos.model.Order;

public class MemoryBasedOrderRepositoryTest {

  private MemoryBasedOrderRepository memoryBasedOrderRepository;

  @Before
  public void setup() {
    memoryBasedOrderRepository = new MemoryBasedOrderRepository();
  }

  @Test
  public void shouldReturnEmptyForNotExistingOrderId() {
    // given orderId does not exist
    val orderId = UUID.randomUUID().toString();

    // when a request comes for not existing order then empty is returned
    assertTrue(memoryBasedOrderRepository.find(orderId).isEmpty());
  }

  @Test
  public void shouldPersistOrders() {
    // given orderId exists
    val order = Order.builder()
        .id(UUID.randomUUID().toString())
        .build();
    memoryBasedOrderRepository.save(order);

    // when a request comes for not existing order then empty is returned
    assertThat(memoryBasedOrderRepository.find(order.getId()).get(),
        CoreMatchers.equalToObject(order));
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldFailOnNullOrderId() {
    // given orderId is null
    val order = Order.builder()
        .id(null)
        .build();

    // when a request comes with null orderId then boom
    memoryBasedOrderRepository.save(order);
    fail();
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldFailOnDuplicates() {
    // given orderId is already exists
    val order = Order.builder()
        .id(UUID.randomUUID().toString())
        .build();
    memoryBasedOrderRepository.save(order);

    // when a request comes with existing orderId then boom
    memoryBasedOrderRepository.save(order);
    fail();
  }
}