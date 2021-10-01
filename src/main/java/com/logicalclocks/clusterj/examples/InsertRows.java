package com.logicalclocks.clusterj.examples;

import com.logicalclocks.clusterj.entity.Customer;
import com.mysql.clusterj.Session;

import java.util.ArrayList;
import java.util.List;

public class InsertRows extends BaseExample {

  public void execute() throws Exception {
    Session session = sessionFactory.getSession();
    List<Customer> customers2insert = new ArrayList<>();
    Customer newCustomer0 = session.newInstance(Customer.class);
    Customer newCustomer1 = session.newInstance(Customer.class);
    newCustomer0.setId(100);
    newCustomer0.setName("Gautier");
    customers2insert.add(newCustomer0);

    newCustomer1.setId(101);
    newCustomer1.setName("Fabio");
    customers2insert.add(newCustomer1);

    session.makePersistentAll(customers2insert);
  }
}
