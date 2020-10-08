package org.demo.store.service.product.admin;

import org.demo.store.service.product.ProductDataConverter;
import org.demo.store.service.product.ProductRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.function.Supplier;

@Configuration
public class ProductAdminContext {

  @Bean
  public ProductAdminService productAdminService(ProductRepository repository,
                                                 ProductDataConverter converter, Supplier<LocalDateTime> dateTimeSupplier) {
    return new ProductAdminService(repository, converter, dateTimeSupplier);
  }
}
