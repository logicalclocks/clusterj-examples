package com.logicalclocks.clusterj.examples;

import com.logicalclocks.clusterj.entity.Customer;
import com.mysql.clusterj.Session;

public class PrimaryKeyLookup extends BaseExample {

  public void execute() throws Exception {
    Session session = sessionFactory.getSession();
    Customer customer = session.find(Customer.class, 10);
    System.out.println(String.format("Customer ID: %d - Name: %s", customer.getId(), customer.getName()));
    session.release(customer);
  }
}
