package org.demo.store.service.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

  @Query("SELECT o FROM Order o WHERE o.createdAt >= :from AND o.createdAt <= :to")
  List<Order> findAllBetween(@Param("from") LocalDateTime from, @Param("to") LocalDateTime to);
}
