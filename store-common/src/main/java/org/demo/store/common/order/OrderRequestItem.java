package org.demo.store.common.order;

import lombok.Value;

import javax.validation.constraints.Min;

@Value
public class OrderRequestItem {
  @Min(value = 1, message = "Sku id must be > 0")
  long skuId;
  @Min(value = 1, message = "Quantity must be > 0")
  int quantity;
}
