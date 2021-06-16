package org.santa.xgos.service;

import java.util.UUID;
import lombok.val;
import org.santa.xgos.model.Order;
import org.santa.xgos.model.Requestor;
import org.santa.xgos.vo.OrderViewObject;
import org.springframework.stereotype.Component;

@Component
public class ViewObjectMapper {

  public Order map(OrderViewObject orderViewObject) {
    val requestorVO = orderViewObject.getRequestor();
    return Order.builder()
        .xgPictureS3Location(orderViewObject.getXgPictureS3Location())
        .id(UUID.randomUUID().toString())
        .requestor(
            Requestor.builder()
                .address(requestorVO.getAddress())
                .name(requestorVO.getName())
                .build()
        )
        .build();
  }

  public OrderViewObject map(Order order) {
    val requestor = order.getRequestor();
    return OrderViewObject.builder()
        .orderId(order.getId())
        .xgPictureS3Location(order.getXgPictureS3Location())
        .requestor(
            OrderViewObject.Requestor.builder()
                .address(requestor.getAddress())
                .name(requestor.getName())
                .build()
        )
        .build();
  }
}
