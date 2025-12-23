package com.ecommerce.ecommerce.controller;

import com.ecommerce.ecommerce.dto.ProductRequestDto;
import com.ecommerce.ecommerce.dto.ProductResponseDto;
import com.ecommerce.ecommerce.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private ProductService productService;
    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductResponseDto> addProduct(@RequestBody ProductRequestDto productRequestDto) throws Exception {
        return ResponseEntity.ok(productService.addProduct(productRequestDto));
    }


    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(productService.getProductById(id));
    }


    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable Long id, @RequestBody ProductRequestDto productRequestDto) throws Exception {
        return ResponseEntity.ok(productService.updateProduct(id, productRequestDto));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id){
        boolean b = productService.deleteProduct(id);
        if(b){
            return ResponseEntity.ok("Product Deleted");
        }
        return ResponseEntity.badRequest().body("Product Id is Wrong");
    }


    @GetMapping
    public ResponseEntity<Page<ProductResponseDto>> getAllProducts(@RequestParam(defaultValue = "0") int page,
                                                                   @RequestParam(defaultValue = "5") int size){
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<ProductResponseDto> allProducts = productService.getAllProducts(pageRequest);
        return ResponseEntity.ok(allProducts);
    }


    @GetMapping("/category/{category_id}")
    public ResponseEntity<Page<ProductResponseDto>> getProductByCategory(@PathVariable Long category_id, @RequestParam(defaultValue = "0") int page,
                                                                         @RequestParam(defaultValue = "5") int size) throws Exception {

        PageRequest pageRequest = PageRequest.of(page, size);
        return ResponseEntity.ok(productService.getProductsByCategory(category_id,pageRequest));
    }


}
