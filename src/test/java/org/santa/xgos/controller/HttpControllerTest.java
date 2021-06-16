package org.santa.xgos.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.UUID;
import lombok.val;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.santa.xgos.vo.OrderViewObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class HttpControllerTest {

  private static final String LOCATION = "Location";
  private static final String ORDERS = "/orders";
  @Autowired
  private WebApplicationContext context;
  @Autowired
  private ObjectMapper json;
  private MockMvc mvc;

  @Before
  public void setup() {
    mvc = MockMvcBuilders
        .webAppContextSetup(context)
        .build();
  }

  @Test
  public void shouldReturnOrderIdWhenOrderIsCreated() throws Exception {
    // given valid request
    val vo = OrderViewObject.builder()
        .requestor(
            OrderViewObject.Requestor.builder()
                .name(UUID.randomUUID().toString())
                .address(UUID.randomUUID().toString())
                .build()
        )
        .xgPictureS3Location(UUID.randomUUID().toString())
        .build();

    // when request processed then order ID is returned in Location header
    mvc.perform(post(ORDERS)
        .contentType(MediaType.APPLICATION_JSON)
        .content(json.writeValueAsString(vo)))
        .andExpect(status().isNoContent())
        .andExpect(header().exists(LOCATION));
  }

  @Test
  public void shouldReturnExistingOrder() throws Exception {
    // given valid request created an order
    val vo = OrderViewObject.builder()
        .requestor(
            OrderViewObject.Requestor.builder()
                .name(UUID.randomUUID().toString())
                .address(UUID.randomUUID().toString())
                .build()
        )
        .xgPictureS3Location(UUID.randomUUID().toString())
        .build();

    val newOrderId = mvc.perform(post(ORDERS)
        .contentType(MediaType.APPLICATION_JSON)
        .content(json.writeValueAsString(vo)))
        .andExpect(status().isNoContent())
        .andExpect(header().exists(LOCATION))
        .andReturn()
        .getResponse().getHeader(LOCATION);

    // when GET is fired then order is found
    val voExpected = vo.toBuilder()
        .orderId(newOrderId)
        .build();
    mvc.perform(get(ORDERS + "/" + newOrderId)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().json(json.writeValueAsString(voExpected), false));
  }

  @Test
  public void shouldRejectInvalidCreateOrderRequests() throws Exception {
    // given invalid an order - no s3 location
    val vo = OrderViewObject.builder()
        .requestor(
            OrderViewObject.Requestor.builder()
                .name(UUID.randomUUID().toString())
                .address(UUID.randomUUID().toString())
                .build()
        )
        .build();

    // when comes then
    val newOrderId = mvc.perform(post(ORDERS)
        .contentType(MediaType.APPLICATION_JSON)
        .content(json.writeValueAsString(vo)))
        .andExpect(status().isBadRequest());
  }
}