package com.example.SklepInternetowyApp.testData;

import entity.Product;

import java.util.stream.Stream;

public record IncorrectDataList(Stream<Product> incorrectProducts) {
}
