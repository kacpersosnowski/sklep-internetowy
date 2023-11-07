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

    public Product addProduct(Product product) {
        validateProduct(product);
        return productRepository.save(product);
    }

    public List<Product> getProducts() {
        return productRepository.findAll()
                .stream()
                .toList();
    }

    public Product getProductById(Long id) {
        return productRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found."));
    }

    public Product updateProduct(Product product, Long id) {
        validateProduct(product);
        Product oldProduct = productRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found."));

        product.setId(oldProduct.getId());
        return productRepository.saveAndFlush(product);
    }

    private void validateProduct(Product product) {
        if(product == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product must not be null.");
        } else if (product.getName() == null || product.getName().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product name is mandatory.");
        } else if (product.getDescription() == null || product.getDescription().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product description is mandatory.");
        } else if (
                product.getAmountInStock() < 0 ||
                        product.getAmountInStock() > product.getMaximumInStock()
        ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Amount cannot be less than zero.");
        }
    }
}
