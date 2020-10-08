package org.demo.store.service.order;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ORDER_DATA")
public class Order {

  @Id
  @Column(name = "ORDER_ID")
  @GeneratedValue
  private long id;

  @Column(name = "ORDER_CONTACT")
  private String contact;

  @Column(name = "ORDER_DATE")
  private LocalDateTime createdAt;

  @Column(name = "ORDER_COST")
  private double cost;

  @Singular
  @OneToMany(mappedBy = "order", cascade = ALL)
  private List<OrderItem> items;
}
