package org.demo.store.service.order.internal;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.demo.store.common.order.OrderInfo;
import org.demo.store.common.order.OrderRequest;
import org.demo.store.service.order.exception.OrderNotFoundException;
import org.demo.store.service.product.exception.ProductNotFoundException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping("/internal/orders")
@RequiredArgsConstructor
public class OrderInternalApi {

  private final OrderInternalService orderInternalService;

  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Order information retrieved"),
          @ApiResponse(responseCode = "400", description = "Request is not valid"),
          @ApiResponse(responseCode = "404", description = "Order not found"),
          @ApiResponse(responseCode = "500", description = "Unexpected error")
  })
  @Operation(summary = "Retrieve information about an order")
  @GetMapping(path = "/{orderId}", produces = APPLICATION_JSON_VALUE)
  public OrderInfo getOrder(@PathVariable long orderId) {
    return orderInternalService.getOrderInfo(orderId);
  }

  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Order information retrieved"),
          @ApiResponse(responseCode = "400", description = "Request is not valid"),
          @ApiResponse(responseCode = "500", description = "Unexpected error")
  })
  @Operation(summary = "Retrieve all orders in a given interval")
  @GetMapping(produces = APPLICATION_JSON_VALUE)
  public List<OrderInfo> getOrders(
          @Parameter(description = "dd/MM/yyyy HH:mm:ss")
          @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss") LocalDateTime from,
          @Parameter(description = "dd/MM/yyyy HH:mm:ss")
          @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss") LocalDateTime to
  ) {
    return orderInternalService.getOrderInfo(from, to);
  }

  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Order was created"),
          @ApiResponse(responseCode = "400", description = "Request is not valid"),
          @ApiResponse(responseCode = "404", description = "Product not found"),
          @ApiResponse(responseCode = "500", description = "Unexpected error")
  })
  @ResponseStatus(CREATED)
  @Operation(summary = "Create a new order")
  @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
  public OrderInfo createOrder(@RequestBody @Valid OrderRequest orderRequest) {
    return orderInternalService.createOrder(orderRequest);
  }

  @ResponseStatus(BAD_REQUEST)
  @ExceptionHandler({MethodArgumentNotValidException.class, MethodArgumentTypeMismatchException.class})
  public void handleValidationError(Exception e) {
    log.error("400 error in order api", e);
  }

  @ResponseStatus(NOT_FOUND)
  @ExceptionHandler({OrderNotFoundException.class, ProductNotFoundException.class})
  public void handleProductNotFound(Exception e) {
    log.error("404 error in order api", e);
  }

  @ResponseStatus(INTERNAL_SERVER_ERROR)
  @ExceptionHandler(Exception.class)
  public void handleException(Exception e) {
    log.error("500 error in order api", e);
  }
}
