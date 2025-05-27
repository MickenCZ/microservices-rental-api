package cz.cvut.fel.nss.gardeningrental.productservice.controller;

import cz.cvut.fel.nss.gardeningrental.productservice.DTO.CategoryCreationDTO;
import cz.cvut.fel.nss.gardeningrental.productservice.DTO.CategoryProductDTO;
import cz.cvut.fel.nss.gardeningrental.productservice.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/")
    public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryCreationDTO categoryCreationDTO) {
        categoryService.createNewCategory(categoryCreationDTO.getName());

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/products/categories/" + categoryCreationDTO.getName()));

        return new ResponseEntity<>("Category created", headers, HttpStatus.CREATED);
    }

    @PutMapping("/products/")
    public ResponseEntity<?> addCategoryToProduct(@Valid @RequestBody CategoryProductDTO categoryProductDTO) {
        categoryService.addCategoryToProduct(categoryProductDTO.getProductName(), categoryProductDTO.getCategoryName());

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/products/categories/" + categoryProductDTO.getCategoryName()));

        return new ResponseEntity<>("Category added", headers, HttpStatus.OK);
    }
}
