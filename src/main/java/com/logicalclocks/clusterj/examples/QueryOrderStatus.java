package com.logicalclocks.clusterj.examples;

import com.logicalclocks.clusterj.entity.OrderStatus;
import com.mysql.clusterj.Query;
import com.mysql.clusterj.Session;
import com.mysql.clusterj.query.Predicate;
import com.mysql.clusterj.query.PredicateOperand;
import com.mysql.clusterj.query.QueryBuilder;
import com.mysql.clusterj.query.QueryDomainType;

import java.util.List;

public class QueryOrderStatus extends BaseExample {
  private static final int CID_LOW = 10;
  private static final int CID_HIGH = 70;
  private static final String STATUS = "HANDLING";

  @Override
  public void execute() throws Exception {
    Session session = sessionFactory.getSession();
    QueryBuilder qb = session.getQueryBuilder();
    QueryDomainType<OrderStatus> qdt = qb.createQueryDefinition(OrderStatus.class);

    // Customer ID between 10 and 50
    PredicateOperand customerLowParam = qdt.param("cid_low");
    PredicateOperand customerHighParam = qdt.param("cid_high");
    Predicate cidPredicate = qdt.get("customerId").between(customerLowParam, customerHighParam);

    // Order status must be HANDLING
    PredicateOperand orderStatusParam = qdt.param("order_status");
    Predicate statusPredicate = qdt.get("status").equal(orderStatusParam);

    qdt.where(cidPredicate.and(statusPredicate));

    Query<OrderStatus> query = session.createQuery(qdt);
    query.setParameter("cid_low", CID_LOW);
    query.setParameter("cid_high", CID_HIGH);
    query.setParameter("order_status", STATUS);

    List<OrderStatus> orders = query.getResultList();
    System.out.println(String.format("Found %d orders with status %s and customer ID between %d and %d",
            orders.size(), STATUS, CID_LOW, CID_HIGH));
    for (OrderStatus os : orders) {
      System.out.println(String.format("Customer ID: %d Order ID: %d Status: %s", os.getCustomerId(), os.getOrderId()
              , os.getStatus()));
    }
  }
}
