package org.demo.store.service.order.internal;

import lombok.val;
import org.demo.store.common.order.OrderInfo;
import org.demo.store.common.order.OrderRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class OrderInternalApiTest {

  private static final long ORDER_ID = 125L;

  @Mock
  private OrderInternalService orderInternalService;
  @InjectMocks
  private OrderInternalApi orderInternalApi;

  @Test
  public void shouldGetOrder() {
    val orderInfo = OrderInfo.builder().build();
    when(orderInternalService.getOrderInfo(ORDER_ID)).thenReturn(orderInfo);
    val result = orderInternalApi.getOrder(ORDER_ID);
    assertThat(result).isEqualTo(orderInfo);
  }

  @Test
  public void shouldGetOrdersByDates() {
    List<OrderInfo> orders = Collections.singletonList(OrderInfo.builder().build());
    when(orderInternalService.getOrderInfo(LocalDateTime.MIN, LocalDateTime.MAX))
            .thenReturn(orders);
    val result = orderInternalApi.getOrders(LocalDateTime.MIN, LocalDateTime.MAX);
    assertThat(result).isEqualTo(orders);
  }

  @Test
  public void shouldCreateOrder() {
    val request = OrderRequest.builder().build();
    val orderInfo = OrderInfo.builder().build();
    when(orderInternalService.createOrder(request)).thenReturn(orderInfo);
    val result = orderInternalApi.createOrder(request);
    assertThat(result).isEqualTo(orderInfo);
  }
}
