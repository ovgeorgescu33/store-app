package org.demo.store.service.product.exception;

public class ProductAlreadyCreatedException extends RuntimeException {

  public ProductAlreadyCreatedException(long skuId) {
    super("Product already exists: " + skuId);
  }
}
