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
import com.mysql.clusterj.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Generate and load data in order_status table batching the inserts
 */
public class GenerateOrderStatus extends BaseExample {
  private static final String[] ORDER_STATUS = {"NEW", "HANDLING", "DELIVERED", "CANCELED"};
  private static final int MAX_ORDER_STATUS = 100;

  @Override
  public void execute() throws Exception {
    Session session = sessionFactory.getSession();
    Random rand = new Random();
    List<OrderStatus> orderStatuses = new ArrayList<>(MAX_ORDER_STATUS);

    OrderStatus s = session.newInstance(OrderStatus.class);
    s.setOrderId(0);
    s.setCustomerId(-1);
    s.setStatus(ORDER_STATUS[1]);
    orderStatuses.add(s);

    for (int i = 1; i < MAX_ORDER_STATUS; i++) {
      OrderStatus o = session.newInstance(OrderStatus.class);
      o.setOrderId(i);
      o.setCustomerId(rand.nextInt(i));
      o.setStatus(ORDER_STATUS[rand.nextInt(ORDER_STATUS.length)]);
      orderStatuses.add(o);
    }

    session.makePersistentAll(orderStatuses);
    System.out.println(String.format("Added %d order statuses", orderStatuses.size()));
  }
}
