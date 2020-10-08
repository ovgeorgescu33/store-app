package org.demo.store.service.product.admin;

import lombok.val;
import org.demo.store.common.product.ProductData;
import org.demo.store.service.product.Product;
import org.demo.store.service.product.ProductDataConverter;
import org.demo.store.service.product.ProductRepository;
import org.demo.store.service.product.exception.ProductAlreadyCreatedException;
import org.demo.store.service.product.exception.ProductNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class ProductAdminServiceTest {

  private static final LocalDateTime DATE_TIME = LocalDateTime.now();
  private static final long SKU_ID = 235235L;

  @Mock
  private ProductRepository repository;
  @Mock
  private ProductDataConverter converter;
  @Mock
  private Supplier<LocalDateTime> dateTimeSupplier;
  @InjectMocks
  private ProductAdminService productAdminService;
  @Captor
  private ArgumentCaptor<Product> productArgCaptor;

  @BeforeEach
  void setUp() {
    when(dateTimeSupplier.get()).thenReturn(DATE_TIME);
  }

  @Test
  public void shouldCreateProduct() {
    val data = ProductData.builder().skuId(SKU_ID).build();
    val converted = data.toBuilder().name("converted").build();
    val product = Product.builder().skuId(SKU_ID).build();
    when(converter.convert(data)).thenReturn(product);
    when(repository.saveAndFlush(any(Product.class))).thenReturn(product);
    when(converter.convert(product)).thenReturn(converted);
    val result = productAdminService.create(data);
    verify(repository).saveAndFlush(productArgCaptor.capture());
    val savedProduct = productArgCaptor.getValue();
    assertThat(savedProduct.getCreatedAt()).isEqualTo(DATE_TIME);
    assertThat(savedProduct).isEqualToIgnoringGivenFields(product, "createdAt");
    assertThat(result).isEqualTo(converted);
  }

  @Test
  public void shouldUpdateProduct() {
    val data = ProductData.builder().skuId(SKU_ID).enabled(true).name("name").price(25D).build();
    val converted = data.toBuilder().name("converted").build();
    val product = Product.builder().skuId(SKU_ID)
            .enabled(false).name("other_name").price(25).createdAt(DATE_TIME).build();
    when(repository.findById(SKU_ID)).thenReturn(Optional.of(product));
    when(repository.saveAndFlush(any(Product.class))).thenReturn(product);
    when(converter.convert(product)).thenReturn(converted);
    val result = productAdminService.update(data);
    verify(repository).saveAndFlush(productArgCaptor.capture());
    val updatedProduct = productArgCaptor.getValue();
    val expected = Product.builder().skuId(data.getSkuId())
            .enabled(data.isEnabled()).name(data.getName()).price(data.getPrice())
            .skuId(data.getSkuId()).createdAt(DATE_TIME).build();
    assertThat(updatedProduct).isEqualTo(expected);
    assertThat(result).isEqualTo(converted);
  }

  @Test
  public void shouldDisableProduct() {
    val product = Product.builder().skuId(SKU_ID)
            .enabled(true).name("other_name").price(25).createdAt(DATE_TIME).build();
    when(repository.findById(SKU_ID)).thenReturn(Optional.of(product));
    productAdminService.disable(SKU_ID);
    verify(repository).saveAndFlush(productArgCaptor.capture());
    val updatedProduct = productArgCaptor.getValue();
    assertThat(updatedProduct.isEnabled()).isFalse();
  }

  @Test
  public void shouldReturnAllProducts() {
    val product1 = Product.builder().skuId(100).build();
    val product2 = Product.builder().skuId(200).build();
    val data1 = ProductData.builder().skuId(100).build();
    val data2 = ProductData.builder().skuId(200).build();
    when(repository.findAll()).thenReturn(List.of(product1, product2));
    when(converter.convert(product1)).thenReturn(data1);
    when(converter.convert(product2)).thenReturn(data2);
    List<ProductData> result = productAdminService.getAll();
    List<ProductData> expected = List.of(data1, data2);
    assertThat(result).isEqualTo(expected);
  }

  @Test
  public void shouldThrowWhenProductAlreadyExistsOnCreate() {
    val data = ProductData.builder().skuId(SKU_ID).build();
    when(repository.existsById(SKU_ID)).thenReturn(true);
    assertThatThrownBy(() -> productAdminService.create(data))
            .isInstanceOf(ProductAlreadyCreatedException.class)
            .hasMessageMatching("Product already exists: .*");
  }

  @Test
  public void shouldThrowProductNotFoundOnDisable() {
    when(repository.findById(anyLong())).thenReturn(Optional.empty());
    assertThatThrownBy(() -> productAdminService.disable(SKU_ID))
            .isInstanceOf(ProductNotFoundException.class)
            .hasMessageMatching("Unable to find product with sku id: .*");
  }

  @Test
  public void shouldThrowProductNotFoundOnUpdate() {
    when(repository.findById(anyLong())).thenReturn(Optional.empty());
    val data = ProductData.builder().build();
    assertThatThrownBy(() -> productAdminService.update(data))
            .isInstanceOf(ProductNotFoundException.class)
            .hasMessageMatching("Unable to find product with sku id: .*");
  }
}
