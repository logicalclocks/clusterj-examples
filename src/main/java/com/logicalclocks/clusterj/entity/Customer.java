package com.logicalclocks.clusterj.entity;

import com.mysql.clusterj.annotation.Column;
import com.mysql.clusterj.annotation.PersistenceCapable;
import com.mysql.clusterj.annotation.PrimaryKey;

@PersistenceCapable(table = "customer")
public interface Customer {

  @PrimaryKey
  @Column(name = "id")
  int getId();
  void setId(int id);

  @Column(name = "name")
  String getName();
  void setName(String name);

  int getAge();
  void setAge(int age);

  @Column(name = "country")
  String getCountryOfOrigin();
  void setCountryOfOrigin(String countryOfOrigin);
}
