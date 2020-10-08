package org.demo.store.service.order;

import lombok.val;
import org.demo.store.service.product.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
public class OrderCostCalculatorTest {

  private final OrderCostCalculator orderCostCalculator = new OrderCostCalculator();

  @Test
  public void shouldCalculateTotalAmount() {
    val product1 = Product.builder().skuId(100).price(32).build();
    val product2 = Product.builder().skuId(200).price(11).build();
    val quantity1 = 5;
    val quantity2 = 10;
    val orderBuilder = Order.builder();
    orderBuilder.item(OrderItem.builder().quantity(quantity1).product(product1).build());
    orderBuilder.item(OrderItem.builder().quantity(quantity2).product(product2).build());
    val order = orderBuilder.build();
    val result = orderCostCalculator.calculate(order);
    val expected = product1.getPrice() * quantity1 + product2.getPrice() * quantity2;
    assertThat(result).isEqualTo(expected);
  }
}
