package org.demo.store.service.product;

import org.demo.store.common.product.ProductData;

public class ProductDataConverter {

  public ProductData convert(Product product) {
    return ProductData.builder()
            .name(product.getName())
            .price(product.getPrice())
            .skuId(product.getSkuId())
            .enabled(product.isEnabled())
            .build();
  }

  public Product convert(ProductData data) {
    return Product.builder()
            .name(data.getName())
            .price(data.getPrice())
            .skuId(data.getSkuId())
            .enabled(data.isEnabled())
            .build();
  }
}
