package com.logicalclocks.clusterj.examples;


import com.mysql.clusterj.SessionFactory;

public abstract class BaseExample implements ClusterJExample {
  protected SessionFactory sessionFactory;

  public void init(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }
}
