package org.demo.store.service.product;

import lombok.val;
import org.demo.store.common.product.ProductData;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductDataConverterTest {

  private static final long SKU_ID = 134235L;
  private static final String NAME = "product-name";
  private static final double PRICE = 255.15D;
  private static final boolean ENABLED = true;

  private final ProductDataConverter converter = new ProductDataConverter();

  @Test
  public void shouldConvertToDto() {
    val product = getProduct();
    val result = converter.convert(product);
    val expected = getProductData();
    assertThat(result).isEqualTo(expected);
  }

  @Test
  public void shouldCovertToProduct() {
    val data = getProductData();
    val result = converter.convert(data);
    val expected = new Product(SKU_ID, NAME, PRICE, ENABLED, null, null);
    assertThat(result).isEqualTo(expected);
  }

  private Product getProduct() {
    return Product.builder().skuId(SKU_ID).name(NAME).price(PRICE).enabled(ENABLED).createdAt(null).items(null).build();
  }

  private ProductData getProductData() {
    return ProductData.builder().skuId(SKU_ID).name(NAME).price(PRICE).enabled(ENABLED).build();
  }
}