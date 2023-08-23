package org.kainos.ea.api;

import org.kainos.ea.cli.Product;
import org.kainos.ea.cli.ProductRequest;
import org.kainos.ea.client.*;
import org.kainos.ea.core.ProductValidator;
import org.kainos.ea.db.ProductDAO;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class ProductService {
    private ProductDAO productDao = new ProductDAO();
    private ProductValidator productValidator = new ProductValidator();

    public List<Product> getAllProducts() throws FailedToGetProductsException {
        /*List<Product> productList = new ArrayList<>();

        Product product1 = new Product(1, "Nintendo Switch", "Video games console", 299.99);
        Product product2 = new Product(2, "Shoes", "Comfortable footwear", 49.99);
        Product product3 = new Product(3, "Coffee", "Tasty drink", 4.99);

        productList.add(product1);
        productList.add(product2);
        productList.add(product3);*/

        List<Product> productList = null;
        try {
            productList = productDao.getAllProducts();
        } catch (SQLException e) {
            System.err.println(e.getMessage());

            throw new FailedToGetProductsException();
        }

        double totalPriceOfProducts = 0;

        // calculate total price using stream
        //double totalPriceOfProducts = productList.stream().mapToDouble(Product::getPrice).sum();

        // Using for-each loop
        for (Product product : productList) {
            totalPriceOfProducts += product.getPrice();
        }

        // Using iterator & while or do while loop
        /*Iterator<Product> productIterator = productList.iterator();

        while (productIterator.hasNext()) {
            Product product = productIterator.next();
            totalPriceOfProducts += product.getPrice();
        }
        do {
            Product product = productIterator.next();
            totalPriceOfProducts += product.getPrice();
        } while (productIterator.hasNext()); */

        // Get products which cost less than £100
        /*for (Product product : productList) {
            if (product.getPrice() < 100) {
                totalPriceOfProducts += product.getPrice();
            }
        }*/

        System.out.println("Total price of products: £" + String.format("%.2f", totalPriceOfProducts));

        // Print out total price of expensive or cheap products
        double totalPriceOfProductsCheap = 0;
        double totalPriceOfProductsExpensive = 0;

        for (Product product : productList) {
            if (product.getPrice() < 100) {
                totalPriceOfProductsCheap += product.getPrice();
            }
            else {
                totalPriceOfProductsExpensive += product.getPrice();
            }
        }

        System.out.println("Total price of cheap products: £" +
                String.format("%.2f", totalPriceOfProductsCheap) +
                "\nTotal price of expensive products: £" +
                String.format("%.2f", totalPriceOfProductsExpensive));

        // switch statement example
        for (Product product : productList) {
            switch (product.getName()) {
                case ("Nintendo switch"):
                    System.out.println("This is the Nintendo Switch Price: £" + product.getPrice());
                default:
                    System.out.println("This is the other price: £" + product.getPrice());
            }
        }

        // duplicate values can exist in list
        List<Integer> intList = Arrays.asList(1, 2, 2, 3, 4);
        // duplicate values cannot exist in set
        Set<Integer> intSet = new HashSet<>(intList);

        // intList.forEach(System.out::println);
        // intSet.forEach(System.out::println);

        // Must implement comparable interface in Product for products to be sorted
        Collections.sort(productList);

        // productList.forEach(System.out::println);

        //System.out.println(Collections.min(productList));

        //System.out.println(Collections.max(productList));

        // Print products with price higher than £150
        productList.stream().filter(product -> product.getPrice() > 150).forEach(System.out::println);

        // Create list of products with price lower than £150
        List<Product> cheapProducts =
                productList.stream().filter(product -> product.getPrice() < 150)
                        .collect(Collectors.toList());

        cheapProducts.forEach(System.out::println);

        // Do invalid search in database
        /* try {
            Product product = productList.get(1000);
        } catch (IndexOutOfBoundsException e) {
            System.err.println(e.getMessage());
        } */

        return productList;
    }

    public Product getProductById(int id) throws FailedToGetProductsException, ProductDoesNotExistException {
        try {
            Product product = productDao.getProductById(id);

            if (product == null) {
                throw new ProductDoesNotExistException();
            }

            return product;

        } catch (SQLException e) {
            System.err.println(e.getMessage());

            throw new FailedToGetProductsException();
        }
    }

    public int createProduct(ProductRequest product) throws FailedToCreateProductException, InvalidProductException {
        try {
            String validation = productValidator.isValidProduct(product);

            if (validation != null) {
                throw new InvalidProductException(validation);
            }

            int id = productDao.createProduct(product);

            if (id == -1) {
                throw new FailedToCreateProductException();
            }

            return id;

        } catch (SQLException e) {
            System.err.println(e.getMessage());

            throw new RuntimeException(e);
        }
    }

    public void updateProduct(int id, ProductRequest product) throws ProductDoesNotExistException,
            InvalidProductException, FailedToUpdateProductException {
        try {
            String validation = productValidator.isValidProduct(product);

            if (validation != null) {
                throw new InvalidProductException(validation);
            }

            Product productToUpdate = productDao.getProductById(id);

            if (productToUpdate == null) {
                throw new ProductDoesNotExistException();
            }

            productDao.updateProduct(id, product);

        } catch (SQLException e) {
            System.err.println(e.getMessage());

            throw new FailedToUpdateProductException();
        }
    }

    public void deleteProduct(int id) throws ProductDoesNotExistException, FailedToDeleteProductException {
        try {
            if (productDao.getProductById(id) == null) {
                throw new ProductDoesNotExistException();
            }

            productDao.deleteProduct(id);
        } catch (SQLException e) {
            System.err.println(e.getMessage());

            throw new FailedToDeleteProductException();
        }
    }
}
