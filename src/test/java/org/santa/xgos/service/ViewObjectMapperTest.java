package org.santa.xgos.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.UUID;
import lombok.val;
import org.junit.Before;
import org.junit.Test;
import org.santa.xgos.model.Order;
import org.santa.xgos.vo.OrderViewObject;

public class ViewObjectMapperTest {

  private ViewObjectMapper viewObjectMapper;

  @Before
  public void setup() {
    viewObjectMapper = new ViewObjectMapper();
  }

  @Test
  public void shouldSuccessfullyConvertToModel() {
    // given vo is provided
    val vo = OrderViewObject.builder()
        .requestor(
            OrderViewObject.Requestor.builder()
                .name(UUID.randomUUID().toString())
                .address(UUID.randomUUID().toString())
                .build()
        )
        .xgPictureS3Location(UUID.randomUUID().toString())
        .build();

    // when mapping request comes then model object is instatiated
    val actual = viewObjectMapper.map(vo);
    assertEquals(vo.getXgPictureS3Location(), actual.getXgPictureS3Location());
    assertEquals(vo.getRequestor().getAddress(), actual.getRequestor().getAddress());
    assertEquals(vo.getRequestor().getName(), actual.getRequestor().getName());
  }

}