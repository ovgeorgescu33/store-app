package org.demo.store.service.order;

import org.demo.store.service.product.ProductDataConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderContext {

  @Bean
  public OrderCostCalculator orderCostCalculator() {
    return new OrderCostCalculator();
  }

  @Bean
  public OrderInfoConverter orderInfoConverter(ProductDataConverter converter) {
    return new OrderInfoConverter(converter);
  }
}
