package org.demo.store.service.order.internal;

import lombok.RequiredArgsConstructor;
import org.demo.store.common.order.OrderInfo;
import org.demo.store.common.order.OrderRequest;
import org.demo.store.common.order.OrderRequestItem;
import org.demo.store.service.order.*;
import org.demo.store.service.order.exception.OrderNotFoundException;
import org.demo.store.service.product.Product;
import org.demo.store.service.product.ProductService;
import org.demo.store.service.product.exception.ProductNotFoundException;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@RequiredArgsConstructor
public class OrderInternalService {

  private final OrderRepository orderRepository;
  private final OrderCostCalculator calculator;
  private final ProductService productService;
  private final OrderInfoConverter converter;
  private final Supplier<LocalDateTime> dateTimeSupplier;

  public OrderInfo getOrderInfo(long orderId) {
    return orderRepository.findById(orderId).map(converter::getInfo)
            .orElseThrow(() -> new OrderNotFoundException(orderId));
  }

  public List<OrderInfo> getOrderInfo(LocalDateTime from, LocalDateTime to) {
    return orderRepository.findAllBetween(from, to).stream()
            .map(converter::getInfo).collect(toList());
  }

  @Transactional
  public OrderInfo createOrder(OrderRequest request) {
    Order order = new Order();
    List<OrderItem> items = getOrderItems(request, order);
    order.setItems(items);
    order.setCreatedAt(dateTimeSupplier.get());
    order.setContact(request.getContact());
    double cost = calculator.calculate(order);
    order.setCost(cost);
    Order result = orderRepository.saveAndFlush(order);
    return converter.getInfo(result);
  }

  private List<OrderItem> getOrderItems(OrderRequest request, Order order) {
    Set<Long> skuIds = request.getItems().stream().map(OrderRequestItem::getSkuId).collect(toSet());
    Map<Long, Product> products = productService.findAll(skuIds);
    return request.getItems().stream().map(i -> getOrderItem(i, products, order)).collect(toList());
  }

  private OrderItem getOrderItem(OrderRequestItem orderItemDto, Map<Long, Product> products, Order order) {
    OrderItem orderItem = new OrderItem();
    Product product = products.get(orderItemDto.getSkuId());
    if (product == null) {
      throw new ProductNotFoundException(orderItemDto.getSkuId());
    }
    orderItem.setProduct(product);
    orderItem.setOrder(order);
    orderItem.setQuantity(orderItemDto.getQuantity());
    return orderItem;
  }
}
