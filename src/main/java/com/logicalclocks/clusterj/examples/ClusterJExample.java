package com.logicalclocks.clusterj.examples;

import com.mysql.clusterj.SessionFactory;

public interface ClusterJExample {
  void init(SessionFactory sessionFactory);
  void execute() throws Exception;
}
