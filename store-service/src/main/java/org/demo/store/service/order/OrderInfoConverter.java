package org.demo.store.service.order;

import lombok.RequiredArgsConstructor;
import org.demo.store.common.order.OrderInfo;
import org.demo.store.common.order.OrderInfoItem;
import org.demo.store.common.product.ProductData;
import org.demo.store.service.product.ProductDataConverter;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
public class OrderInfoConverter {

  private final ProductDataConverter productDataConverter;

  public OrderInfo getInfo(Order order) {
    List<OrderInfoItem> items = getItems(order);
    return OrderInfo.builder().orderId(order.getId())
            .contact(order.getContact())
            .contact(order.getContact())
            .totalAmount(order.getCost())
            .createdAt(order.getCreatedAt())
            .items(items)
            .build();
  }

  private List<OrderInfoItem> getItems(Order order) {
    return order.getItems().stream().map(this::getOrderInfoItem).collect(toList());
  }

  private OrderInfoItem getOrderInfoItem(OrderItem orderItem) {
    ProductData data = productDataConverter.convert(orderItem.getProduct());
    return new OrderInfoItem(orderItem.getQuantity(), data);
  }
}
