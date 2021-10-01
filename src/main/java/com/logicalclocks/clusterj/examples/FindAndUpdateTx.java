package com.logicalclocks.clusterj.examples;

import com.logicalclocks.clusterj.entity.Customer;
import com.mysql.clusterj.LockMode;
import com.mysql.clusterj.Session;

public class FindAndUpdateTx extends BaseExample {

  private static final int PRIMARY_KEY = 10;

  @Override
  public void execute() throws Exception {
    Session session = sessionFactory.getSession();
    session.setLockMode(LockMode.EXCLUSIVE);
    session.currentTransaction().begin();
    Customer customer = session.find(Customer.class, PRIMARY_KEY);
    customer.setName("Jim");
    session.updatePersistent(customer);
    session.currentTransaction().commit();
  }
}
