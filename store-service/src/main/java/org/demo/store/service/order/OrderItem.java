package org.demo.store.service.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.demo.store.service.product.Product;

import javax.persistence.*;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ORDER_ITEM")
public class OrderItem {

  @Id
  @Column(name = "ORDER_ITEM_ID")
  @GeneratedValue
  private long id;

  @Column(name = "ORDER_ITEM_QUANTITY")
  private int quantity;

  @ManyToOne
  @JoinColumn(name = "ORDER_ID", nullable = false)
  private Order order;

  @ManyToOne
  @JoinColumn(name = "PRODUCT_SKU_ID", nullable = false)
  private Product product;
}
