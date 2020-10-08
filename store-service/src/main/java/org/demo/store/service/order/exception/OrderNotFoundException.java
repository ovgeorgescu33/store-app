package org.demo.store.service.order.exception;

public class OrderNotFoundException extends RuntimeException {

  public OrderNotFoundException(long id) {
    super("Unable to find order: " + id);
  }
}
