package org.demo.store.service.product;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductContext {

  @Bean
  public ProductDataConverter productDtoConverter() {
    return new ProductDataConverter();
  }

  @Bean
  public ProductService productService(ProductRepository productRepository) {
    return new ProductService(productRepository);
  }
}
