package org.kainos.ea.cli;

import java.sql.Date;

public class Order implements Comparable<Order> {
    private int orderId;
    private int customerID;
    private Date orderDate;

    public Order(int orderId, int customerID, Date orderDate) {
        setOrderId(orderId);
        setCustomerID(customerID);
        setOrderDate(orderDate);
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    @Override
    public String toString() {
        return "OrderID: " + getOrderId() + ", CustomerID: " + getCustomerID() + ", OrderDate: " + getOrderDate();
    }

    @Override
    public int compareTo(Order order) {
        return this.getOrderDate().compareTo(order.getOrderDate());
    }
}
