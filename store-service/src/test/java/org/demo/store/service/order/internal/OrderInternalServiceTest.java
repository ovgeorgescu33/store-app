package org.demo.store.service.order.internal;

import lombok.val;
import org.demo.store.common.order.OrderInfo;
import org.demo.store.common.order.OrderRequest;
import org.demo.store.common.order.OrderRequestItem;
import org.demo.store.service.order.Order;
import org.demo.store.service.order.OrderCostCalculator;
import org.demo.store.service.order.OrderInfoConverter;
import org.demo.store.service.order.OrderRepository;
import org.demo.store.service.order.exception.OrderNotFoundException;
import org.demo.store.service.product.Product;
import org.demo.store.service.product.ProductService;
import org.demo.store.service.product.exception.ProductNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class OrderInternalServiceTest {

  private static final long ORDER_ID = 235235L;
  private static final LocalDateTime DATE_TIME = LocalDateTime.now();
  private static final double COST = 2000D;
  private static final String CONTACT = "CONTACT";

  @Mock
  private OrderRepository orderRepository;
  @Mock
  private OrderCostCalculator calculator;
  @Mock
  private ProductService productService;
  @Mock
  private OrderInfoConverter converter;
  @Mock
  private Supplier<LocalDateTime> dateTimeSupplier;
  @InjectMocks
  private OrderInternalService orderInternalService;

  @BeforeEach
  void setUp() {
    when(dateTimeSupplier.get()).thenReturn(DATE_TIME);
    when(calculator.calculate(any(Order.class))).thenReturn(COST);
  }

  @Test
  public void shouldReturnOrderInfo() {
    val order = Order.builder().build();
    val orderInfo = OrderInfo.builder().build();
    when(orderRepository.findById(ORDER_ID)).thenReturn(Optional.of(order));
    when(converter.getInfo(order)).thenReturn(orderInfo);
    val result = orderInternalService.getOrderInfo(ORDER_ID);
    assertThat(result).isEqualTo(orderInfo);
  }

  @Test
  public void shouldReturnOrdersInfoByInterval() {
    val order = Order.builder().build();
    val orders = Collections.singletonList(order);
    val orderInfo = OrderInfo.builder().build();
    when(orderRepository.findAllBetween(LocalDateTime.MIN, LocalDateTime.MAX))
            .thenReturn(orders);
    when(converter.getInfo(order)).thenReturn(orderInfo);
    val result = orderInternalService.getOrderInfo(LocalDateTime.MIN, LocalDateTime.MAX);
    val expected = Collections.singletonList(orderInfo);
    assertThat(result).isEqualTo(expected);
  }

  @Test
  public void shouldCreateOrder() {
    val orderInfo = OrderInfo.builder().build();
    val requestBuilder = OrderRequest.builder().contact(CONTACT);
    requestBuilder.item(new OrderRequestItem(1L, 5));
    requestBuilder.item(new OrderRequestItem(2L, 10));
    requestBuilder.item(new OrderRequestItem(3L, 20));
    val request = requestBuilder.build();
    when(converter.getInfo(any(Order.class))).thenReturn(orderInfo);
    when(orderRepository.saveAndFlush(any(Order.class))).thenReturn(new Order());
    when(productService.findAll(Set.of(1L, 2L, 3L))).thenReturn(
            Map.of(1L, Product.builder().build(),
                    2L, Product.builder().build(),
                    3L, Product.builder().build()
            )
    );
    val result = orderInternalService.createOrder(request);
    assertThat(result).isEqualTo(orderInfo);
  }

  @Test
  public void shouldThrowWhenOrderMissingOnFind() {
    when(orderRepository.findById(ORDER_ID)).thenReturn(Optional.empty());
    assertThatThrownBy(() -> orderInternalService.getOrderInfo(ORDER_ID))
            .isInstanceOf(OrderNotFoundException.class)
            .hasMessageMatching("Unable to find order: .*");
  }

  @Test
  public void shouldThrowWhenProductMissingOnCreate() {
    val orderInfo = OrderInfo.builder().build();
    val requestBuilder = OrderRequest.builder().contact(CONTACT);
    requestBuilder.item(new OrderRequestItem(1L, 5));
    requestBuilder.item(new OrderRequestItem(2L, 10));
    val request = requestBuilder.build();
    when(converter.getInfo(any(Order.class))).thenReturn(orderInfo);
    when(orderRepository.saveAndFlush(any(Order.class))).thenReturn(new Order());
    when(productService.findAll(Set.of(1L, 2L, 3L))).thenReturn(
            Map.of(1L, Product.builder().build())
    );
    assertThatThrownBy(() -> orderInternalService.createOrder(request))
            .isInstanceOf(ProductNotFoundException.class)
            .hasMessageMatching("Unable to find product with sku id: .*");
  }
}
