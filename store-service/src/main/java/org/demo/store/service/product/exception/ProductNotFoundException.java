package org.demo.store.service.product.exception;

public class ProductNotFoundException extends RuntimeException {

  public ProductNotFoundException(long skuId) {
    super("Unable to find product with sku id: " + skuId);
  }
}
