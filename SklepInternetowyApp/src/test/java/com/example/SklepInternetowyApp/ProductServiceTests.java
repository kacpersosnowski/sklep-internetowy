package com.example.SklepInternetowyApp;

import com.example.SklepInternetowyApp.testData.TestDataBuilder;
import entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;
import repo.ProductRepository;
import service.ProductService;

import java.util.Optional;
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
	private Product exampleIncorrectProduct;

	@BeforeEach
	void setUp() {
		exampleProduct = TestDataBuilder.exampleProduct()
				.product();
		exampleIncorrectProduct = TestDataBuilder.exampleIncorrectProduct()
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
	final void test_getProductById_shouldThrowException() {
		long productId = -1L;

		assertThrows(ResponseStatusException.class, ()-> productService.getProductById(productId),
				String.format("Should throw exception when product id is %d", productId));
	}


	@Test
	final void test_addNewProduct_shouldReturnProduct() {
		Product expected = exampleProduct;

		when(productRepository.save(exampleProduct))
				.thenReturn(exampleProduct);
		Product actual = productService.addProduct(exampleProduct);

		assertEquals(expected, actual);
		verify(productRepository).save(exampleProduct);
	}

	@Test
	final void test_deleteProduct_shouldCallRepositoryMethod() {
		long productId = 1L;

		productService.deleteProduct(productId);
		verify(productRepository).deleteById(productId);
	}

	@Test
	final void test_updateProduct_shouldThrowException() {
		long productId = -1L;

		assertThrows(ResponseStatusException.class, () -> productService.getProductById(productId),
				String.format("Should throw exception when product id is %d", productId));
	}

	@Test
	final void test_addIncorrectProduct_shouldThrowException() {
		assertThrows(ResponseStatusException.class, () -> productService.addProduct(exampleIncorrectProduct),
				"Should throw exception when product doesn't have a name.");
	}

	@Test
	final void test_addIncorrectProduct_shouldThrowExceptionTwo() {
		assertThrows(ResponseStatusException.class, () -> productService.addProduct(exampleIncorrectProduct),
				"Should throw exception when product doesn't have a name.");
	}

	@ParameterizedTest
	@MethodSource("provideCorrectDataList")
	final void test_addProduct_shouldAddToDatabase(Product product){

		when(productRepository.save(product))
				.thenReturn(product);

		Product actual = productService.addProduct(product);
		Product expected = product;

		assertEquals(expected, actual,
				String.format("Should return product response with name %s", actual.getName()));
	}

	private static Stream<Product> provideCorrectDataList() {
		return TestDataBuilder
				.correctDataList()
				.correctProducts();
	}

}
