package com.logicalclocks.clusterj;

import com.logicalclocks.clusterj.examples.ClusterJExample;

import java.lang.reflect.Constructor;

public class Main {
  public static void main(String... argv) throws Exception {
    if (argv.length < 1) {
      System.err.println("Usage: com.logicalclocks.clusterj.Main EXAMPLE_CLASS");
      System.exit(1);
    }
    Class<ClusterJExample> clazz = (Class<ClusterJExample>) Class.forName(argv[0]);
    Constructor<ClusterJExample> constructor = clazz.getConstructor();
    ClusterJExample example = constructor.newInstance();
    com.mysql.clusterj.SessionFactory sessionFactory = SessionFactory.getInstance();
    example.init(sessionFactory);
    example.execute();
  }
}
