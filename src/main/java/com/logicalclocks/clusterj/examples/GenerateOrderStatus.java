package com.logicalclocks.clusterj.examples;

import com.logicalclocks.clusterj.entity.OrderStatus;
import com.mysql.clusterj.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GenerateOrderStatus extends BaseExample {
  private static final String[] ORDER_STATUS = {"NEW", "HANDLING", "DELIVERED", "CANCELED"};
  private static final int MAX_ORDER_STATUS = 100;

  @Override
  public void execute() throws Exception {
    Session session = sessionFactory.getSession();
    Random rand = new Random();
    List<OrderStatus> orderStatuses = new ArrayList<>(MAX_ORDER_STATUS);

    OrderStatus s = session.newInstance(OrderStatus.class);
    s.setOrderId(0);
    s.setCustomerId(-1);
    s.setStatus(ORDER_STATUS[1]);
    orderStatuses.add(s);

    for (int i = 1; i < MAX_ORDER_STATUS; i++) {
      OrderStatus o = session.newInstance(OrderStatus.class);
      o.setOrderId(i);
      o.setCustomerId(rand.nextInt(i));
      o.setStatus(ORDER_STATUS[rand.nextInt(ORDER_STATUS.length)]);
      orderStatuses.add(o);
    }

    session.makePersistentAll(orderStatuses);
    System.out.println(String.format("Added %d order statuses", orderStatuses.size()));
  }
}
