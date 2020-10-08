package org.demo.store.service.product;

import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ProductService {

  private final ProductRepository repository;

  public Map<Long, Product> findAll(Set<Long> skuIds) {
    return repository.findAllById(skuIds).stream().filter(Product::isEnabled)
            .collect(Collectors.toMap(Product::getSkuId, Function.identity()));
  }

}
