package com.example.SklepInternetowyApp.testData;

import entity.Product;

import java.util.List;
import java.util.stream.Stream;

public class TestDataBuilder {
    
    private TestDataBuilder(){}

    public static ExampleProduct exampleProduct(){
        Long productId = 1L;

        Product product = Product.builder()
                .id(productId)
                .name("testProduct")
                .description("testDescription")
                .amountInStock(1)
                .maximumInStock(100)
                .build();

        return new ExampleProduct(product);
    }

    public static ExampleProduct exampleIncorrectProduct(){
        Long productId = 1L;

        Product product = Product.builder()
                .id(productId)
                .description("testDescription")
                .amountInStock(1)
                .maximumInStock(100)
                .build();

        return new ExampleProduct(product);
    }

    public static CorrectDataList correctDataList(){
        Product product = Product.builder()
                .id(1L)
                .name("test product")
                .description("test description")
                .amountInStock(1)
                .maximumInStock(100)
                .build();

        Product product2 = Product.builder()
                .id(10000L)
                .name("Product 2")
                .description("Description 2")
                .amountInStock(100)
                .maximumInStock(100)
                .build();

        Product product3 = Product.builder()
                .id(10L)
                .name("PRODUCT 3")
                .description("DESCRIPTION 3")
                .amountInStock(0)
                .maximumInStock(100)
                .build();

        Product product4 = Product.builder()
                .id(3L)
                .name("product^%$^%$^")
                .description("description!@#@%$#%")
                .amountInStock(10)
                .maximumInStock(100)
                .build();

        Product product5 = Product.builder()
                .id(100L)
                .name("PRODUCT ^%$^%$^")
                .description("DESCRIPTION !@#@%$#%")
                .amountInStock(10)
                .maximumInStock(100)
                .build();

        return new CorrectDataList(Stream.of(product,
                product2,
                product3,
                product4,
                product5));

    }

}
