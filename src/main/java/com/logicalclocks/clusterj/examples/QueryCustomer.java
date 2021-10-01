package com.logicalclocks.clusterj.examples;

import com.logicalclocks.clusterj.entity.Customer;
import com.mysql.clusterj.Query;
import com.mysql.clusterj.Session;
import com.mysql.clusterj.query.Predicate;
import com.mysql.clusterj.query.PredicateOperand;
import com.mysql.clusterj.query.QueryBuilder;
import com.mysql.clusterj.query.QueryDomainType;

import java.util.List;

public class QueryCustomer extends BaseExample {

  private static final int AGE_MIN = 20;
  private static final int AGE_MAX = 30;

  @Override
  public void execute() throws Exception {
    Session session = sessionFactory.getSession();
    QueryBuilder qb = session.getQueryBuilder();
    QueryDomainType<Customer> qdt = qb.createQueryDefinition(Customer.class);

    // Age between 20 - 30
    PredicateOperand ageMinParam = qdt.param("age_min");
    PredicateOperand ageMaxParam = qdt.param("age_max");
    Predicate agePredicate = qdt.get("age").between(ageMinParam, ageMaxParam);

    qdt.where(agePredicate);

    Query<Customer> query = session.createQuery(qdt);
    query.setParameter("age_min", AGE_MIN);
    query.setParameter("age_max", AGE_MAX);

    List<Customer> customersBetweenAge = query.getResultList();
    System.out.println(String.format("Found %d customers with age between %d and %d",
            customersBetweenAge.size(), AGE_MIN, AGE_MAX));
    for (Customer c : customersBetweenAge) {
      System.out.println(String.format("Name: %s - Age: %d", c.getName(), c.getAge()));
    }
  }
}
