package org.demo.store.common.order;

import lombok.Builder;
import lombok.Singular;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

@Value
@Builder
public class OrderInfo {

  long orderId;
  double totalAmount;
  String contact;
  LocalDateTime createdAt;
  @Singular
  List<OrderInfoItem> items;
}
