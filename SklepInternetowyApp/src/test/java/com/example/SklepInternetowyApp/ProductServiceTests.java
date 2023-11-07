package com.example.SklepInternetowyApp;

import com.example.SklepInternetowyApp.testData.TestDataBuilder;
import entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import repo.ProductRepository;
import service.ProductService;

import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Slf4j
@SpringBootTest(classes = {ProductRepository.class})
class ProductServiceTests {

	@InjectMocks
	private ProductService productService;

	@Mock
	private ProductRepository productRepository;
	private Product exampleProduct;

	@BeforeEach
	void setUp() {
		exampleProduct = TestDataBuilder.exampleProduct()
				.product();
	}

	@Test
	final void test_getProductById_shouldReturnProduct() {
		long productId = 1L;

		when(productRepository.findById(productId))
				.thenReturn(Optional.ofNullable(exampleProduct));

		Product actual = productService.getProductById(productId);
		Product expected = exampleProduct;

		assertEquals(expected, actual,
				String.format("Should return product with id %d", productId));
	}

	@Test
	final void test_getProductById_shouldThrowResponseStatusException() {
		long productId = -1L;

		assertThrows(ResponseStatusException.class, ()-> productService.getProductById(productId),
				String.format("Should throw exception when product id is %d", productId));
	}

	@Test
	final void test_deleteProduct_shouldCallRepositoryMethod() {
		long productId = 1L;

		productService.deleteProduct(productId);
		verify(productRepository).deleteById(productId);
	}

	@Test
	final void test_updateProduct_shouldReturnUpdatedProduct() {
		Product oldProduct = exampleProduct;
		Product newProduct = TestDataBuilder.exampleProduct().product();
		newProduct.setDescription("A new description");

		when(productRepository.findById(oldProduct.getId()))
				.thenReturn(Optional.of(oldProduct));
		when(productRepository.saveAndFlush(newProduct))
				.thenReturn(newProduct);
		Product actual = productService.updateProduct(newProduct, oldProduct.getId());
		Product expected = newProduct;

		assertEquals(expected, actual,
				String.format("Should return product response with new description: %s", expected.getDescription()));
	}

	@Test
	final void test_updateProduct_shouldThrowResponseStatusException() {
		long productId = -1L;
		Product newProduct = exampleProduct;

		when(productRepository.findById(productId))
				.thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found."));

		assertThrows(ResponseStatusException.class, () -> productService.updateProduct(newProduct, productId),
				String.format("Should throw exception when product id is %d", productId));
	}

	@ParameterizedTest
	@MethodSource("provideCorrectDataList")
	final void test_addProduct_shouldReturnProduct(Product product) {

		when(productRepository.save(product))
				.thenReturn(product);

		Product actual = productService.addProduct(product);
		Product expected = product;

		assertEquals(expected, actual,
				String.format("Should return product response with name %s", actual.getName()));
	}

	@ParameterizedTest
	@MethodSource("provideIncorrectDataList")
	final void test_addProduct_shouldThrowResponseStatusException(Product product){
		assertThrows(ResponseStatusException.class, () -> productService.addProduct(product),
				"Should not pass validation and throw an Exception");
	}

	private static Stream<Product> provideCorrectDataList() throws JSONException, IOException {
		return TestDataBuilder
				.correctDataList()
				.correctProducts();
	}

	private static Stream<Product> provideIncorrectDataList() throws JSONException, IOException {
		return TestDataBuilder
				.incorrectDataList()
				.incorrectProducts();
	}

	@Test
	final void test_getProducts_ShouldReturnListOfProducts() {
		Product product2 = exampleProduct;
		product2.setId(2L);
		List<Product> products = new ArrayList<>(Arrays.asList(exampleProduct, product2));

		when(productRepository.findAll())
				.thenReturn(products);
		List<Product> expected = products;
		List<Product> actual = productService.getProducts();

		assertEquals(expected, actual,
				String.format("Should return product response with length of %d", expected.size()));
	}
}
