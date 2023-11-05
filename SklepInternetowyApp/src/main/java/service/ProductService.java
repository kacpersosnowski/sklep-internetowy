package service;

import entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import repo.ProductRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public List<Product> getProducts() {
        return productRepository.findAll()
                .stream()
                .toList();
    }

    public Product getProductById(Long id) {
        return productRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product is not in database"));
    }

    public Product addProduct(Product product) {
        validateProduct(product);
        return productRepository.save(product);
    }

    public Product updateProduct(Product product, Long id) {
        validateProduct(product);
        Product oldProduct = productRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product is not in database"));

        product.setId(oldProduct.getId());
        return productRepository.saveAndFlush(product);
    }

    private void validateProduct(Product product) {
        if (product == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product cannot be null");
        } else if (product.getProductName() == null || product.getProductName().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product name cannot be null or blank");
        } else if (product.getProductDescription() == null || product.getProductDescription().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product description cannot be null or blank");
        } else if (
                product.getAmountInStock() < 0 ||
                        product.getAmountInStock() > product.getMaximumInStock() ||
                        product.getMaximumInStock() < 1
        ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Amount in stock cannot be less than zero");
        } else if (product.getPrice() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product price cannot be less than zero");
        }
    }
}
