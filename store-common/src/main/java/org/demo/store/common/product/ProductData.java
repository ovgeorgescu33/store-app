package org.demo.store.common.product;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Value
@Builder(toBuilder = true)
public class ProductData {

  @Min(value = 1, message = "Sku id should be > 0")
  long skuId;
  @NotBlank(message = "Name should be populated")
  String name;
  @Min(value = 0, message = "Price should >= 0")
  double price;
  boolean enabled;
}
