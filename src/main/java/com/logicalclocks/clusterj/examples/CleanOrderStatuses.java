package com.logicalclocks.clusterj.examples;

import com.logicalclocks.clusterj.entity.OrderStatus;
import com.mysql.clusterj.Query;
import com.mysql.clusterj.Session;
import com.mysql.clusterj.query.QueryBuilder;
import com.mysql.clusterj.query.QueryDomainType;

import java.util.List;

public class CleanOrderStatuses extends BaseExample {
  @Override
  public void execute() throws Exception {
    Session session = sessionFactory.getSession();

    QueryBuilder qb = session.getQueryBuilder();
    QueryDomainType<OrderStatus> qdt = qb.createQueryDefinition(OrderStatus.class);

    session.currentTransaction().begin();

    Query<OrderStatus> query = session.createQuery(qdt);
    List<OrderStatus> results = query.getResultList();
    session.deletePersistentAll(results);
    session.currentTransaction().commit();
    System.out.println("Deleted " + results.size() + " order status rows");
  }
}
