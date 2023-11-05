package com.example.SklepInternetowyApp.testData;

import entity.Product;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class TestDataBuilder {

    private TestDataBuilder() {
    }

    public static ExampleProduct exampleProduct() {
        Long productId = 1L;

        Product product = Product.builder()
                .id(productId)
                .productName("testProduct")
                .productDescription("testDescription")
                .amountInStock(1)
                .maximumInStock(100)
                .price(15.5)
                .build();

        return new ExampleProduct(product);
    }

    public static ExampleProduct exampleIncorrectProduct() {
        Long productId = 1L;

        Product product = Product.builder()
                .id(productId)
                .productDescription("testDescription")
                .amountInStock(1)
                .maximumInStock(100)
                .build();

        return new ExampleProduct(product);
    }

    public static DataList correctDataList() {
        Product product = Product.builder()
                .id(1L)
                .productName("test product")
                .productDescription("test description")
                .amountInStock(1)
                .maximumInStock(100)
                .price(0.01)
                .build();

        Product product2 = Product.builder()
                .id(10000L)
                .productName("Product 2")
                .productDescription("Description 2")
                .amountInStock(100)
                .maximumInStock(100)
                .price(1000.10)
                .build();

        Product product3 = Product.builder()
                .id(10L)
                .productName("PRODUCT 3")
                .productDescription("DESCRIPTION 3")
                .amountInStock(0)
                .maximumInStock(100)
                .price(12.23)
                .build();

        Product product4 = Product.builder()
                .id(3L)
                .productName("product^%$^%$^")
                .productDescription("description!@#@%$#%")
                .amountInStock(10)
                .maximumInStock(100)
                .price(7.15)
                .build();

        Product product5 = Product.builder()
                .id(100L)
                .productName("PRODUCT ^%$^%$^")
                .productDescription("DESCRIPTION !@#@%$#%")
                .amountInStock(10)
                .maximumInStock(100)
                .price(10)
                .build();

        return new DataList(Stream.of(product,
                product2,
                product3,
                product4,
                product5));

    }

    public static DataList incorrectDataList() {
        Product product = Product.builder()
                .id(1L)
                .productName(null)
                .productDescription("test description")
                .amountInStock(1)
                .maximumInStock(100)
                .price(0.01)
                .build();

        Product product2 = Product.builder()
                .id(1L)
                .productName("product name")
                .productDescription(null)
                .amountInStock(1)
                .maximumInStock(100)
                .price(0.01)
                .build();

        Product product3 = Product.builder()
                .id(1L)
                .productName(null)
                .productDescription(null)
                .amountInStock(1)
                .maximumInStock(100)
                .price(0.01)
                .build();

        Product product4 = Product.builder()
                .id(1L)
                .productName("test name")
                .productDescription("test description")
                .amountInStock(102)
                .maximumInStock(100)
                .price(0.01)
                .build();

        Product product5 = Product.builder()
                .id(1L)
                .productName("test name")
                .productDescription("test description")
                .amountInStock(-5)
                .maximumInStock(100)
                .price(0.01)
                .build();

        Product product6 = Product.builder()
                .id(1L)
                .productName("test name")
                .productDescription("test description")
                .amountInStock(-15)
                .maximumInStock(-10)
                .price(0.01)
                .build();

        Product product7 = Product.builder()
                .id(1L)
                .productName("test name")
                .productDescription("test description")
                .amountInStock(102)
                .maximumInStock(100)
                .price(-0.01)
                .build();

        Product product8 = Product.builder()
                .id(1L)
                .productName("test name")
                .productDescription("test description")
                .amountInStock(0)
                .maximumInStock(0)
                .price(0.01)
                .build();

        Product product9 = Product.builder()
                .id(1L)
                .productName(null)
                .productDescription(null)
                .amountInStock(-5)
                .maximumInStock(-5)
                .price(-0.01)
                .build();

        Product product10 = Product.builder()
                .id(1L)
                .productName("test name")
                .productDescription("test description")
                .amountInStock(1)
                .maximumInStock(5)
                .price(0)
                .build();

        return new DataList(Stream.of(product,
                product2,
                product3,
                product4,
                product5,
                product6,
                product7,
                product8,
                product9,
                product10,
                null));

    }

    public static DataList correctDataListFromFile(){
        return getDataListFromFile("correctData.json");
    }

    public static DataList incorrectDataListFromFile(){
        return getDataListFromFile("incorrectData.json");
    }

    public static DataList getDataListFromFile(String fileName) {
        JSONParser parser = new JSONParser();
        List<Product> productList = new ArrayList<>();
        try {
            JSONArray array = (JSONArray) parser.parse(new FileReader(fileName));
            for (Object o : array) {
                JSONObject product = (JSONObject) o;
                Long id = (Long) product.get("id");
                String productName = (String) product.get("productName");
                String productDescription = (String) product.get("productDescription");
                Long amountInStockJSON = (Long) product.get("amountInStock");
                int amountInStock = amountInStockJSON.intValue();
                Long maximumInStockJSON = (Long) product.get("maximumInStock");
                int maximumInStock = maximumInStockJSON.intValue();
                double price = (double) product.get("price");
                productList.add(
                        Product.builder()
                                .id(id)
                                .productName(productName)
                                .productDescription(productDescription)
                                .amountInStock(amountInStock)
                                .maximumInStock(maximumInStock)
                                .price(price)
                                .build()
                );
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return new DataList(productList.stream());
    }

}
