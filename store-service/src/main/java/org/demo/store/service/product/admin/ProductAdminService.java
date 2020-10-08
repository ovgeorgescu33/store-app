package org.demo.store.service.product.admin;

import lombok.RequiredArgsConstructor;
import org.demo.store.common.product.ProductData;
import org.demo.store.service.product.Product;
import org.demo.store.service.product.ProductDataConverter;
import org.demo.store.service.product.ProductRepository;
import org.demo.store.service.product.exception.ProductAlreadyCreatedException;
import org.demo.store.service.product.exception.ProductNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ProductAdminService {

  private final ProductRepository repository;
  private final ProductDataConverter converter;
  private final Supplier<LocalDateTime> dateTimeSupplier;

  public ProductData create(ProductData data) {
    boolean exists = repository.existsById(data.getSkuId());
    if (exists) {
      throw new ProductAlreadyCreatedException(data.getSkuId());
    }
    Product product = converter.convert(data);
    product.setCreatedAt(dateTimeSupplier.get());
    Product result = repository.saveAndFlush(product);
    return converter.convert(result);
  }

  public ProductData update(ProductData data) {
    Product product = findOrThrow(data.getSkuId());
    product.setName(data.getName());
    product.setPrice(data.getPrice());
    product.setEnabled(data.isEnabled());
    Product result = repository.saveAndFlush(product);
    return converter.convert(result);
  }

  public void disable(long skuId) {
    Product product = findOrThrow(skuId);
    product.setEnabled(false);
    repository.saveAndFlush(product);
  }

  public List<ProductData> getAll() {
    return repository.findAll().stream().map(converter::convert).collect(Collectors.toList());
  }

  private Product findOrThrow(long skuId) {
    return repository.findById(skuId).orElseThrow(() -> new ProductNotFoundException(skuId));
  }
}
