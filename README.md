# clusterj-examples

This repository contains a collection of examples using ClusterJ to communicate with [RonDB](https://www.rondb.com
) cluster. Using ClusterJ bypasses the MySQL server and uses the RonDB API to connect directly to RonDB [datanodes
](https://docs.rondb.com/rondb_overview_apis).

## Prerequisites

### Java 11
You need to have Java runtime installed

### Maven
This is a Maven project so you need to install Maven to build it

### Native RonDB API
ClusterJ is Java client which internally is using JNI to make calls to the native C++ RonDB library. The library is
 need at **runtime**. Follow the steps below to correctly install the library.
 
 1. Download the native library tarball from [here](https://repo.hops.works/master/lib-ndb-6.1.0.tgz)
 2. Extract the tarball
 3. Copy the contents to your Linux distribution default library path, usually `/usr/lib` Otherwise when you run the
  examples you should override the library path using `-Djava.library.path=PATH_TO_LIBRARY` 

### ClusterJ
To use ClusterJ from your application you need to include the ClusterJ jar in your classpath. You can download the
 jar file from [here](https://archiva.hops.works/repository/Hops/com/mysql/ndb/clusterj-rondb)
 
 If you are using Maven then you can include it as a dependency in the pom file and add the RonDB repository. Look at
  `pom.xml` of the current project for details.

### RonDB cluster
In order to run the examples in this repository you need an existing RonDB cluster. Use [hopsworks.ai](https://www.hopsworks.ai)
to create a Hopsworks cluster or follow the instructions [here](https://docs.rondb.com/cloud_script) to install RonDB.

As mentioned earlier, ClusterJ uses the native RonDB API so you application **must** be able to reach RonDB
 Management node and RonDB datanodes. Make sure the following ports are open to your firewall/security group.
 
 1. RonDB management port: 1186
 2. RonDB datanode port: 10000
 
## Running the examples

### 1. Create tables

The examples work on two tables which **must** be created beforehand. For that connect to the MySQL server and
 execute the following
 
```sql
CREATE DATABASE clusterj_examples;

CREATE TABLE `customer` (
  `id` int NOT NULL,
  `name` varchar(1024) COLLATE utf8_unicode_ci NOT NULL,
  `age` int NOT NULL,
  `country` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=ndbcluster

CREATE TABLE `order_status` (
  `customer_id` int NOT NULL,
  `order_id` int NOT NULL,
  `status` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`customer_id`,`order_id`)
) ENGINE=ndbcluster
```

### 2. Prepare connection properties file
ClusterJ needs to know how to connect to RonDB management. This application looks for a configuration file at `$HOME
/.rondb.props`

The bare minimum are the following
```
com.mysql.clusterj.connectstring = MANAGEMENT_NODE_IP:1186
com.mysql.clusterj.database = clusterj_examples
```

### 3. Build project
Build the current project with `mvn package`

### 4. Run examples
There are several examples you can run which show the basic functionality of ClusterJ. There is description on each
 file but we briefly explain below.
 
 1. `GenerateCustomers`: Load rows in customers table. It **batches** the inserts instead of inserting one by one
 2. `GenerateOrderStatus`: Similarly to *GenerateCustomers* it loads entries to order_status table
 3. `CleanCustomers`: Clean customers table performing a select all and delete
 4. `CleanOrderStatuses`: Same as *CleanCustomers* but for order_status table
 5. `PrimaryKeyLookUp`: Lookup in customers table based on the primary key
 6. `CompositePrimaryKeyLookup`: Lookup in order_status table but this time the primary key is composed by **multiple**
 columns 
 7. `FindAndUpdateTx`: Replace a row based on the primary key but in the context of a **transaction**
 8. `InsertRows`: A simple insert into customer table
 9. `QueryCustomer`: A more complex range scan on customer table
 10. `QueryOrderStatus`:  A more complex query on order_status table
 
 To run any of the above examples use the provided runner and replace the class name:
 `java -cp target/rondb-clusterj-1.0-SNAPSHOT-jar-with-dependencies.jar com.logicalclocks.clusterj.Run com.logicalclocks.clusterj.examples.EXAMPLE_CLASS_NAME`
 
## Next steps
As the name suggests this is just example use cases. There are many configuration parameters to
[tweak](https://docs.rondb.com/rondb_clusterj/#properties-file) and more techniques to improve performance.

You can find more information at [RonDB documentation](https://docs.rondb.com)

Also you can contact [sales](mailto:sales@logicalclocks.com) to arrange a demo with Logical Clocks engineers.