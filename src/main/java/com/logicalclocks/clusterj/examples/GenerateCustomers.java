package com.logicalclocks.clusterj.examples;

import com.logicalclocks.clusterj.entity.Customer;
import com.mysql.clusterj.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GenerateCustomers extends BaseExample {
  private static final int MAX_CUSTOMERS = 100;

  private static final String[] FIRST_NAMES = {
          "Antonios", "Fabio", "Moritz", "Gautier", "Jim", "Alina", "Nathalia", "Shradda"
  };

  private static final String[] COUNTRY = {
          "Greece", "Italy", "Germany", "France", "Romania", "Brazil", "India"
  };

  @Override
  public void execute() throws Exception {
    Session session = sessionFactory.getSession();
    List<Customer> customers = new ArrayList<>(100);
    Random rand = new Random();
    for (int i = 0; i < MAX_CUSTOMERS; i++) {
      Customer c = session.newInstance(Customer.class);
      c.setId(i);
      c.setName(FIRST_NAMES[rand.nextInt(FIRST_NAMES.length)]);
      c.setAge(rand.nextInt(50));
      c.setCountryOfOrigin(COUNTRY[rand.nextInt(COUNTRY.length)]);
      customers.add(c);
    }
    session.makePersistentAll(customers);
    System.out.println("Inserted " + customers.size() + " new customers");
  }
}
