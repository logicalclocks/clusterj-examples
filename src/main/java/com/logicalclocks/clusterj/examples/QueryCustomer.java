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

import com.logicalclocks.clusterj.entity.Customer;
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
 * For optimum performance the age column should be indexed
 *
 * Select all customers whose age is between 20 and 30
 *
 */
public class QueryCustomer extends BaseExample {

  private static final int AGE_MIN = 20;
  private static final int AGE_MAX = 30;

  @Override
  public void execute() throws Exception {
    Session session = sessionFactory.getSession();
    QueryBuilder qb = session.getQueryBuilder();
    QueryDomainType<Customer> qdt = qb.createQueryDefinition(Customer.class);

    // Age between 20 - 30
    PredicateOperand ageMinParam = qdt.param("age_min");
    PredicateOperand ageMaxParam = qdt.param("age_max");
    Predicate agePredicate = qdt.get("age").between(ageMinParam, ageMaxParam);

    qdt.where(agePredicate);

    Query<Customer> query = session.createQuery(qdt);
    query.setParameter("age_min", AGE_MIN);
    query.setParameter("age_max", AGE_MAX);

    List<Customer> customersBetweenAge = query.getResultList();
    System.out.println(String.format("Found %d customers with age between %d and %d",
            customersBetweenAge.size(), AGE_MIN, AGE_MAX));
    for (Customer c : customersBetweenAge) {
      System.out.println(String.format("Name: %s - Age: %d", c.getName(), c.getAge()));
    }
  }
}
