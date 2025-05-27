package cz.cvut.fel.nss.gardeningrental.productservice.controller;

import cz.cvut.fel.nss.gardeningrental.productservice.DTO.ProductCreationDTO;
import cz.cvut.fel.nss.gardeningrental.productservice.DTO.ProductDTO;
import cz.cvut.fel.nss.gardeningrental.productservice.DTO.ProductUpdateDTO;
import cz.cvut.fel.nss.gardeningrental.productservice.model.Product;
import cz.cvut.fel.nss.gardeningrental.productservice.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDTO> getProductByName(@PathVariable Long productId) {
        Product product = productService.getProduct(productId);
        if (product == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            ProductDTO productDTO = new ProductDTO(product);
            return ResponseEntity.ok(productDTO);
        }
    }

    @GetMapping("/categories/{categoryName}")
    public ResponseEntity<List<ProductDTO>> getAllByCategory(@PathVariable String categoryName) {
        List<ProductDTO> products = productService.getAllByCategory(categoryName)
                .stream()
                .map(product -> new ProductDTO(product))
                .collect(Collectors.toList());

        return ResponseEntity.ok(products);
    }

    @PostMapping("/")
    public ResponseEntity<?> createNewProduct(@Valid @RequestBody ProductCreationDTO productCreationDTO) {
        productService.createNewProduct(
                productCreationDTO.getName(),
                productCreationDTO.isActive(),
                productCreationDTO.getDailyPrice(),
                productCreationDTO.getDescription(),
                productCreationDTO.getCategories(),
                productCreationDTO.getManufacturer()
        );
        return new ResponseEntity<>("Product created", HttpStatus.CREATED);
    }

    @PutMapping("/")
    public ResponseEntity<?> updateProduct(@Valid @RequestBody ProductUpdateDTO productUpdateDTO) {
        productService.updateProductDetails(
                productUpdateDTO.getOldName(),
                productUpdateDTO.getNewName(),
                productUpdateDTO.isActive(),
                productUpdateDTO.getDailyPrice(),
                productUpdateDTO.getDescription());
        return ResponseEntity.ok("Product " + productUpdateDTO.getOldName() + " updated.");
    }

    @GetMapping("/exists/{productId}")
    public ResponseEntity<Boolean> existsById(@PathVariable Long productId) {
        return new ResponseEntity<>(productService.existsById(productId), HttpStatus.OK);
    }
}
