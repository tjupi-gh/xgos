package org.santa.xgos.vo;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class OrderViewObject {

  private String orderId;
  @NotNull
  private Requestor requestor;
  @NotEmpty
  private String xgPictureS3Location;

  @Data
  @Builder
  public static class Requestor {

    @NotEmpty
    private String name;
    @NotEmpty
    private String address;
  }
}
