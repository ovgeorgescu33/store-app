package org.demo.store.service.product.admin;

import lombok.val;
import org.demo.store.common.product.ProductData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class ProductAdminApiTest {

  private static final long SKU_ID = 252L;

  @Mock
  private ProductAdminService adminService;
  @InjectMocks
  private ProductAdminApi productAdminApi;

  @Test
  public void shouldListProducts() {
    List<ProductData> products = Collections.singletonList(ProductData.builder().build());
    when(adminService.getAll()).thenReturn(products);
    val result = productAdminApi.list();
    assertThat(result).isEqualTo(products);
  }

  @Test
  public void shouldCreateProduct() {
    val data = ProductData.builder().build();
    when(adminService.create(data)).thenReturn(data);
    val result = productAdminApi.create(data);
    assertThat(result).isEqualTo(data);
    verify(adminService).create(data);
  }

  @Test
  public void shouldUpdateProduct() {
    val data = ProductData.builder().build();
    when(adminService.update(data)).thenReturn(data);
    val result = productAdminApi.update(data);
    assertThat(result).isEqualTo(data);
    verify(adminService).update(data);
  }

  @Test
  public void shouldDisableProduct() {
    productAdminApi.disable(SKU_ID);
    verify(adminService).disable(SKU_ID);
  }
}
