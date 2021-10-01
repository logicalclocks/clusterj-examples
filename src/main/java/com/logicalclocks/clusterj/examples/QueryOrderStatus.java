/*
 * This file is part of clusterj-examples
 * Copyright (C) 2021, Logical Clocks AB. All rights reserved
 *
 * clusterj-examples is free software: you can redistribute it and/or modify it under the terms of
 * the GNU Affero General Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
 *
 * clusterj-examples is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 * PURPOSE.  See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License along with this program.
 * If not, see <https://www.gnu.org/licenses/>.
 *
 * Changes to this file committed before and including commit-id: ccc0d2c5f9a5ac661e60e6eaf138de7889928b8b
 * are released under the following license:
 *
 * Copyright (C) 2013 - 2018, Logical Clocks AB and RISE SICS AB. All rights reserved
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this
 * software and associated documentation files (the "Software"), to deal in the Software
 * without restriction, including without limitation the rights to use, copy, modify, merge,
 * publish, distribute, sublicense, and/or sell copies of the Software, and to permit
 * persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS  OR IMPLIED, INCLUDING
 * BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL  THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR  OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.logicalclocks.clusterj.examples;

import com.logicalclocks.clusterj.entity.OrderStatus;
import com.mysql.clusterj.Query;
import com.mysql.clusterj.Session;
import com.mysql.clusterj.query.Predicate;
import com.mysql.clusterj.query.PredicateOperand;
import com.mysql.clusterj.query.QueryBuilder;
import com.mysql.clusterj.query.QueryDomainType;

import java.util.List;

/**
 * A more complex query than primary key lookup. It's doing a full table scan.
 *
 * For optimum performance the status column should be indexed
 *
 * Select all orders where the associated customer ID is between 10 and 70 and the status is HANDLING
 *
 */
public class QueryOrderStatus extends BaseExample {
  private static final int CID_LOW = 10;
  private static final int CID_HIGH = 70;
  private static final String STATUS = "HANDLING";

  @Override
  public void execute() throws Exception {
    Session session = sessionFactory.getSession();
    QueryBuilder qb = session.getQueryBuilder();
    QueryDomainType<OrderStatus> qdt = qb.createQueryDefinition(OrderStatus.class);

    // Customer ID between 10 and 50
    PredicateOperand customerLowParam = qdt.param("cid_low");
    PredicateOperand customerHighParam = qdt.param("cid_high");
    Predicate cidPredicate = qdt.get("customerId").between(customerLowParam, customerHighParam);

    // Order status must be HANDLING
    PredicateOperand orderStatusParam = qdt.param("order_status");
    Predicate statusPredicate = qdt.get("status").equal(orderStatusParam);

    qdt.where(cidPredicate.and(statusPredicate));

    Query<OrderStatus> query = session.createQuery(qdt);
    query.setParameter("cid_low", CID_LOW);
    query.setParameter("cid_high", CID_HIGH);
    query.setParameter("order_status", STATUS);

    List<OrderStatus> orders = query.getResultList();
    System.out.println(String.format("Found %d orders with status %s and customer ID between %d and %d",
            orders.size(), STATUS, CID_LOW, CID_HIGH));
    for (OrderStatus os : orders) {
      System.out.println(String.format("Customer ID: %d Order ID: %d Status: %s", os.getCustomerId(), os.getOrderId()
              , os.getStatus()));
    }
  }
}
