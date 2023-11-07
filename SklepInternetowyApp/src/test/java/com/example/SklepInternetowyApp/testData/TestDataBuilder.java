package com.example.SklepInternetowyApp.testData;

import entity.Product;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestDataBuilder {
    
    private TestDataBuilder(){}

    public static ExampleProduct exampleProduct(){
        Long productId = 1L;

        Product product = Product.builder()
                .id(productId)
                .name("testProduct")
                .description("testDescription")
                .price(10.0)
                .amountInStock(1)
                .maximumInStock(100)
                .build();

        return new ExampleProduct(product);
    }

    public static List<Product> exampleProductList() {
        Product product1 = Product.builder()
                .id(1L)
                .name("testProduct1")
                .description("testDescription1")
                .price(10.0)
                .amountInStock(1)
                .maximumInStock(100)
                .build();

        Product product2 = Product.builder()
                .id(2L)
                .name("testProduct2")
                .description("testDescription2")
                .price(10.0)
                .amountInStock(1)
                .maximumInStock(100)
                .build();

        Product product3 = Product.builder()
                .id(3L)
                .name("testProduct3")
                .description("testDescription3")
                .price(10.0)
                .amountInStock(1)
                .maximumInStock(100)
                .build();

        return new ArrayList<>(Arrays.asList(product1, product2, product3));
    }

    public static CorrectDataList correctDataList() throws JSONException, IOException {
        return new CorrectDataList(loadProductsArrayFromFile("correctProducts.json").stream());
    }

    public static IncorrectDataList incorrectDataList() throws JSONException, IOException {
        Product product1 = Product.builder()
                .id(1L)
                .description("testDescription")
                .price(10.0)
                .amountInStock(1)
                .maximumInStock(100)
                .build();
        Product product2 = Product.builder()
                .id(1L)
                .name("testName")
                .price(10.0)
                .amountInStock(1)
                .maximumInStock(100)
                .build();

        List<Product> incorrectProducts = loadProductsArrayFromFile("incorrectProducts.json");
        incorrectProducts.add(product1);
        incorrectProducts.add(product2);
        incorrectProducts.add(null);

        return new IncorrectDataList(incorrectProducts.stream());
    }

    private static List<Product> loadProductsArrayFromFile(String fileName) throws IOException, JSONException {
        List<Product> products = new ArrayList<>();
        String resourcesDir = "src/test/java/com/example/SklepInternetowyApp/resources/";
        String jsonData = new String(Files.readAllBytes(Paths.get(resourcesDir + fileName)));
        JSONArray jsonArray = new JSONArray(jsonData);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonProduct = jsonArray.getJSONObject(i);
            Product product = Product.builder()
                    .id(jsonProduct.getLong("id"))
                    .name(jsonProduct.getString("name"))
                    .description(jsonProduct.getString("description"))
                    .price(jsonProduct.getDouble("price"))
                    .amountInStock(jsonProduct.getInt("amountInStock"))
                    .maximumInStock(jsonProduct.getInt("maximumInStock"))
                    .build();

            products.add(product);
        }
        return products;
    }
}
