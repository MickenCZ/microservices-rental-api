package cz.cvut.fel.ear.pujcovna.controller;

import cz.cvut.fel.ear.pujcovna.dto.ProductCreationDTO;
import cz.cvut.fel.ear.pujcovna.dto.ProductDTO;
import cz.cvut.fel.ear.pujcovna.dto.ProductUpdateDTO;
import cz.cvut.fel.ear.pujcovna.model.Product;
import cz.cvut.fel.ear.pujcovna.service.ProductService;
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

    @GetMapping("/{productName}")
    public ResponseEntity<ProductDTO> getProductByName(@PathVariable String productName) {
        Product product = productService.getProduct(productName);
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
        Long productId = productService.createNewProduct(
                productCreationDTO.getName(),
                productCreationDTO.isActive(),
                productCreationDTO.getDailyPrice(),
                productCreationDTO.getDescription(),
                productCreationDTO.getCategories(),
                productCreationDTO.getManufacturer()
        );
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/products/" + productId));
        return new ResponseEntity<>("Product created", headers, HttpStatus.CREATED);
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
}
