package org.kainos.ea.core;

import org.joda.time.DateTime;
import org.kainos.ea.cli.OrderRequest;
import org.kainos.ea.cli.ProductRequest;
import org.kainos.ea.db.CustomerDAO;

import java.sql.SQLException;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;

public class OrderValidator {
    private CustomerDAO customerDAO = new CustomerDAO();

    public String isValidOrder(OrderRequest order) {
        try {
            if (!customerDAO.validateCustomerId(order.getCustomerID())) {
                return "CustomerID must exist in Customer table";
            }

            if (order.getOrderDate().before(DateTime.now().minusYears(1).toDate())) {
                return "OrderDate must be within year of current date";
            }

            return null;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return null;
    }
}
