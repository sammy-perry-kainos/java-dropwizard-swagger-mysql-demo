package org.kainos.ea.core;

import org.kainos.ea.cli.ProductRequest;

public class ProductValidator {
    public String isValidProduct(ProductRequest product) {
        if (product.getName().length() > 50) {
            return "Name greater than 50 characters";
        }

        if (product.getDescription().length() > 500) {
            return "Description greater than 500 characters";
        }

        if (product.getPrice() < 10) {
            return "Price is less than £10";
        }

        return null;
    }
}
