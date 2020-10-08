package org.demo.store.service.order;

import java.util.function.BiFunction;

public class OrderCostCalculator {

  private static final BiFunction<Double, OrderItem, Double> PARTIAL_RESULT_FUNCTION =
          (total, item) -> total + (item.getProduct().getPrice() * item.getQuantity());

  public double calculate(Order order) {
    return order.getItems().stream().reduce(0D, PARTIAL_RESULT_FUNCTION, Double::sum);
  }
}
