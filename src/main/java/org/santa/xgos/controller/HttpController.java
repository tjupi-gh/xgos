package org.santa.xgos.controller;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NO_CONTENT;

import java.net.URI;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.santa.xgos.service.OrderService;
import org.santa.xgos.vo.OrderViewObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/orders")
public class HttpController {

  private final OrderService orderService;

  @PostMapping
  public ResponseEntity<?> createRawOrderFromPicture(
      @Valid @RequestBody OrderViewObject orderViewObject) {

    return ResponseEntity.status(NO_CONTENT).location(URI.create(
        orderService.save(orderViewObject).getOrderId()))
        .build();
  }

  @GetMapping("/{id}")
  public ResponseEntity<OrderViewObject> getOrder(@PathVariable("id") String orderId) {
    return orderService.find(orderId)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.status(NOT_FOUND).build());
  }
}
