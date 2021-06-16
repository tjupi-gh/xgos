package org.santa.xgos.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Order {

  private final String id;
  private final Requestor requestor;
  private final String xgPictureS3Location;
}
