package com.logicalclocks.clusterj;

import com.mysql.clusterj.ClusterJHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Properties;

public class SessionFactory {

  private static com.mysql.clusterj.SessionFactory rondbSessionFactory;

  private SessionFactory() {}

  public static com.mysql.clusterj.SessionFactory getInstance() throws IOException {
    return getInstance(Optional.empty());
  }

  public static com.mysql.clusterj.SessionFactory getInstance(Optional<File> properties) throws IOException {
    if (rondbSessionFactory == null) {
      synchronized (SessionFactory.class) {
        if (rondbSessionFactory == null) {
          File propsFile = properties.orElse(Paths.get(System.getProperty("user.home"), ".rondb.props").toFile());
          System.out.println("Reading from: " + propsFile.getAbsolutePath());
          try (InputStream in = new FileInputStream(propsFile)) {
            Properties props = new Properties();
            props.load(in);
            rondbSessionFactory = ClusterJHelper.getSessionFactory(props);
          }
        }
      }
    }
    return rondbSessionFactory;
  }
}
