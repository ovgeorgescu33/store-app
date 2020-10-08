package org.demo.store.common.order;

import lombok.Builder;
import lombok.Singular;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Value
@Builder
public class OrderRequest {

  @NotBlank(message = "Contact is mandatory")
  String contact;
  @Singular
  List<OrderRequestItem> items;
}
