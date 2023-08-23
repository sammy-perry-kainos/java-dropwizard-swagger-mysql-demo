package org.kainos.ea.api;

import org.kainos.ea.cli.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EmployeeService {
    public List<IPayable> getEmployees() {
        Employee employee = new Employee(1, "Sammy", 20000);
        SalesEmployee salesEmployee = new SalesEmployee(1, "Sammy", 20000,
                1000, 0.1f);
        DeliveryEmployee deliveryEmployee = new DeliveryEmployee(1, "SammyDelivery", 10000);
        Contractor contractor = new Contractor("Contractor", 3100, 23);

        List<IPayable> employees = new ArrayList<>(
                Arrays.asList(employee, deliveryEmployee, salesEmployee, contractor));

        for (IPayable e : employees) {
            System.out.println(e.toString());
        }

        return employees;
    }
}
