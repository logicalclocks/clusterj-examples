package com.logicalclocks.clusterj.examples;

import com.logicalclocks.clusterj.entity.OrderStatus;
import com.mysql.clusterj.Session;

public class CompositePrimaryKeyLookup extends BaseExample {
  @Override
  public void execute() throws Exception {
    Session session = sessionFactory.getSession();

    // Order should match the order of the columns
    Object[] pk = new Object[]{-1, 0};
    OrderStatus status = session.find(OrderStatus.class, pk);
    if (status != null) {
      System.out.println(String.format("Order ID: %d - Customer ID: %d - Status: %s", status.getOrderId(),
              status.getCustomerId(), status.getStatus()));
      session.release(status);
    } else {
      System.err.println("Could not find any order status with Customer ID -1 and Order ID 0");
    }
  }
}
