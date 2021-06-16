package org.santa.xgos.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Requestor {

  private final String name;
  private final String address;
}
