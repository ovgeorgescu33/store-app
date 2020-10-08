package org.demo.store.service.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.demo.store.service.order.OrderItem;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "PRODUCT")
public class Product {

  @Id
  @Column(name = "PRODUCT_SKU_ID")
  long skuId;

  @Column(name = "PRODUCT_NAME")
  String name;

  @Column(name = "PRODUCT_PRICE")
  double price;

  @Column(name = "PRODUCT_ENABLED")
  boolean enabled;

  @Column(name = "PRODUCT_CREATED_AT")
  LocalDateTime createdAt;

  @JsonIgnore
  @OneToMany(mappedBy = "product")
  private List<OrderItem> items;
}
