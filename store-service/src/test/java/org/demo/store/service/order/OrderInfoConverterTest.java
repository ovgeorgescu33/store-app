package org.demo.store.service.order;

import lombok.val;
import org.demo.store.common.order.OrderInfo;
import org.demo.store.common.order.OrderInfoItem;
import org.demo.store.common.product.ProductData;
import org.demo.store.service.product.Product;
import org.demo.store.service.product.ProductDataConverter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class OrderInfoConverterTest {

  private static final long ID = 22L;
  private static final double COST = 12452D;
  private static final String CONTACT = "CONTACT";

  @Mock
  private ProductDataConverter productDataConverter;
  @InjectMocks
  private OrderInfoConverter orderInfoConverter;

  @Test
  public void shouldReturnOrderInfo() {
    val orderBuilder = Order.builder().id(ID).cost(COST).contact(CONTACT);
    val product1 = Product.builder().skuId(100).build();
    val product2 = Product.builder().skuId(200).build();
    val product3 = Product.builder().skuId(300).build();
    val data1 = ProductData.builder().skuId(100).build();
    val data2 = ProductData.builder().skuId(200).build();
    val data3 = ProductData.builder().skuId(300).build();
    val quantity1 = 5;
    val quantity2 = 10;
    val quantity3 = 20;

    when(productDataConverter.convert(product1)).thenReturn(data1);
    when(productDataConverter.convert(product2)).thenReturn(data2);
    when(productDataConverter.convert(product3)).thenReturn(data3);
    orderBuilder.item(OrderItem.builder().quantity(quantity1).product(product1).build());
    orderBuilder.item(OrderItem.builder().quantity(quantity2).product(product2).build());
    orderBuilder.item(OrderItem.builder().quantity(quantity3).product(product3).build());
    val order = orderBuilder.build();
    val result = orderInfoConverter.getInfo(order);

    val orderInfoBuilder = OrderInfo.builder().orderId(ID);
    orderInfoBuilder.contact(CONTACT);
    orderInfoBuilder.totalAmount(COST);
    orderInfoBuilder.item(new OrderInfoItem(quantity1, data1));
    orderInfoBuilder.item(new OrderInfoItem(quantity2, data2));
    orderInfoBuilder.item(new OrderInfoItem(quantity3, data3));
    val expected = orderInfoBuilder.build();
    assertThat(result).isEqualTo(expected);
  }

}
