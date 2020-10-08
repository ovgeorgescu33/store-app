package org.demo.store.common.order;

import lombok.Value;
import org.demo.store.common.product.ProductData;

@Value
public class OrderInfoItem {
  int quantity;
  ProductData product;
}
