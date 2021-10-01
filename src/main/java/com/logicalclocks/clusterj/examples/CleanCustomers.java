package com.logicalclocks.clusterj.examples;

import com.logicalclocks.clusterj.entity.Customer;
import com.mysql.clusterj.Query;
import com.mysql.clusterj.Session;
import com.mysql.clusterj.query.QueryBuilder;
import com.mysql.clusterj.query.QueryDomainType;

import java.util.List;

public class CleanCustomers extends BaseExample {
  @Override
  public void execute() throws Exception {
    Session session = sessionFactory.getSession();

    QueryBuilder qb = session.getQueryBuilder();
    QueryDomainType<Customer> qdt = qb.createQueryDefinition(Customer.class);

    session.currentTransaction().begin();

    Query<Customer> query = session.createQuery(qdt);
    List<Customer> results = query.getResultList();
    session.deletePersistentAll(results);
    session.currentTransaction().commit();
    System.out.println("Deleted " + results.size() + " Customer rows");
  }
}
