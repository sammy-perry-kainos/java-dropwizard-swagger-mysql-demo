package org.kainos.ea.api;

import org.kainos.ea.cli.Order;
import org.kainos.ea.cli.OrderRequest;
import org.kainos.ea.cli.Product;
import org.kainos.ea.cli.ProductRequest;
import org.kainos.ea.client.*;
import org.kainos.ea.core.OrderValidator;
import org.kainos.ea.db.OrderDAO;
import java.sql.SQLException;
import java.util.*;

public class OrderService {
    private OrderDAO orderDao = new OrderDAO();
    private OrderValidator orderValidator = new OrderValidator();

    public List<Order> getAllOrders() throws FailedToGetOrdersException {

        List<Order> orderList = null;
        try {
            orderList = orderDao.getAllOrders();
        } catch (SQLException e) {
            System.err.println(e.getMessage());

            throw new FailedToGetOrdersException();
        }

        /* List<Order> orderList = new ArrayList<>();

        Order order1 = new Order(1, 1, new Date());
        Order order2 = new Order(2, 1, new Date());

        orderList.add(order1);
        orderList.add(order2);*/

        // Sort orders by order date (implemented in Order class)
        Collections.sort(orderList);

        // print all orders
        orderList.forEach(System.out::println);

        // get orders which are newer than 2 days ago
        long DAY_IN_MS = 1000 * 60 * 60 * 24;
        Date dateAWeekAgo = new Date(System.currentTimeMillis() - (2 * DAY_IN_MS));
        orderList.stream().filter(order -> order.getOrderDate().compareTo(dateAWeekAgo) > 0).forEach(System.out::println);

        // only show orders with customer id 1
        orderList.stream().filter(order -> order.getCustomerID() == 1).forEach(System.out::println);

        // only show most recent order
        System.out.println(Collections.max(orderList));

        // only show oldest order
        System.out.println(Collections.min(orderList));

        // Print count of orders
        System.out.println(orderList.size());

        // Show customer with most orders
        Map<Integer, Integer> customerMap = new HashMap<>();

        for (Order order : orderList) {
            if (customerMap.containsKey(order.getCustomerID())) {
                customerMap.put(order.getCustomerID(), customerMap.get(order.getCustomerID()) + 1);
            } else {
                customerMap.put(order.getCustomerID(), 1);
            }
        }

        List<Map.Entry<Integer, Integer>> entries = new ArrayList<>(customerMap.entrySet());

        Collections.sort(entries, Map.Entry.comparingByValue());

        List<Integer> sortedCustomerIds = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : entries) {
            sortedCustomerIds.add(entry.getKey());
        }

        // Now, sortedCustomerMap contains the HashMap entries sorted by values
        System.out.println("Max CustomerID Orders: " + sortedCustomerIds.get(sortedCustomerIds.size() - 1));
        System.out.println("Min CustomerID Orders: " + sortedCustomerIds.get(0));

        return orderList;
    }

    public Order getOrderById(int id) throws FailedToGetProductsException, OrderDoesNotExistException {
        try {
            Order order = orderDao.getOrderById(id);

            if (order == null) {
                throw new OrderDoesNotExistException();
            }

            return order;

        } catch (SQLException e) {
            System.err.println(e.getMessage());

            throw new FailedToGetProductsException();
        }
    }

    public int createOrder(OrderRequest order) throws FailedToCreateOrderException, InvalidOrderException {
        try {
            String validation = orderValidator.isValidOrder(order);

            if (validation != null) {
                throw new InvalidOrderException(validation);
            }

            int id = orderDao.createOrder(order);

            if (id == -1) {
                throw new FailedToCreateOrderException();
            }

            return id;

        } catch (SQLException e) {
            System.err.println(e.getMessage());

            throw new RuntimeException(e);
        }
    }

    public void updateOrder(int id, OrderRequest order) throws OrderDoesNotExistException,
            InvalidOrderException, FailedToUpdateOrderException {
        try {
            String validation = orderValidator.isValidOrder(order);

            if (validation != null) {
                throw new InvalidOrderException(validation);
            }

            Order orderToUpdate = orderDao.getOrderById(id);

            if (orderToUpdate == null) {
                throw new OrderDoesNotExistException();
            }

            orderDao.updateOrder(id, order);

        } catch (SQLException e) {
            System.err.println(e.getMessage());

            throw new FailedToUpdateOrderException();
        }
    }

    public void deleteOrder(int id) throws OrderDoesNotExistException, FailedToDeleteOrderException {
        try {
            if (orderDao.getOrderById(id) == null) {
                throw new OrderDoesNotExistException();
            }

            orderDao.deleteOrder(id);
        } catch (SQLException e) {
            System.err.println(e.getMessage());

            throw new FailedToDeleteOrderException();
        }
    }
}