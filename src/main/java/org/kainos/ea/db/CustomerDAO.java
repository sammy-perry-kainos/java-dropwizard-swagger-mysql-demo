package org.kainos.ea.db;

import org.kainos.ea.cli.Order;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {
    private DatabaseConnector databaseConnector = new DatabaseConnector();

    public boolean validateCustomerId(int id) throws SQLException {
        Connection c = databaseConnector.getConnection();
        Statement st = c.createStatement();

        // Valid request
        ResultSet rs = st.executeQuery("SELECT COUNT(CustomerID) FROM Customer WHERE CustomerID=" + id);

        while (rs.next()) {
            if (rs.getInt("COUNT(CustomerID)") > 0) {
                return true;
            }
        }

        return false;
    }
}
