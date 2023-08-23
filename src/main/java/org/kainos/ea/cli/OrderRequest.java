package org.kainos.ea.cli;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Date;

public class OrderRequest {
    private int customerID;
    private Date orderDate;

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

    @JsonCreator
    public OrderRequest(
            @JsonProperty("customerID") int customerID,
            @JsonProperty("orderDate") Date orderDate) {
        this.customerID = customerID;
        this.orderDate = orderDate;
    }

}