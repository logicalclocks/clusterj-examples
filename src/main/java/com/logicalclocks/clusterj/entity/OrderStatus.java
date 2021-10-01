package com.logicalclocks.clusterj.entity;

import com.mysql.clusterj.annotation.Column;
import com.mysql.clusterj.annotation.PersistenceCapable;
import com.mysql.clusterj.annotation.PrimaryKey;

@PersistenceCapable(table = "order_status")
public interface OrderStatus {
  @Column(name = "customer_id")
  @PrimaryKey
  int getCustomerId();
  void setCustomerId(int customerId);

  @Column(name = "order_id")
  @PrimaryKey
  int getOrderId();
  void setOrderId(int orderId);

  String getStatus();
  void setStatus(String status);
}
