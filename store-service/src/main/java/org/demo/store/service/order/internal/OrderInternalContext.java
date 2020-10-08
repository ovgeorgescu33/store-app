package org.demo.store.service.order.internal;

import org.demo.store.service.order.OrderCostCalculator;
import org.demo.store.service.order.OrderInfoConverter;
import org.demo.store.service.order.OrderRepository;
import org.demo.store.service.product.ProductService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.function.Supplier;

@Configuration
public class OrderInternalContext {

  @Bean
  public OrderInternalService orderInternalService(OrderRepository orderRepository,
                                                   OrderCostCalculator calculator,
                                                   ProductService productService,
                                                   OrderInfoConverter orderInfoConverter,
                                                   Supplier<LocalDateTime> dateTimeSupplier) {
    return new OrderInternalService(orderRepository, calculator, productService,
            orderInfoConverter, dateTimeSupplier);
  }
}
