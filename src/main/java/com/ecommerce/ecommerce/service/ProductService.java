package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.dto.ProductRequestDto;
import com.ecommerce.ecommerce.dto.ProductResponseDto;
import com.ecommerce.ecommerce.entity.CategoryEntity;
import com.ecommerce.ecommerce.entity.ProductEntity;
import com.ecommerce.ecommerce.repository.CategoryRepo;
import com.ecommerce.ecommerce.repository.ProductRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class ProductService {

    private ProductRepo productRepo;

    private CategoryRepo categoryRepo;

    public ProductService(ProductRepo productRepo, CategoryRepo categoryRepo){
        this.productRepo = productRepo;
        this.categoryRepo = categoryRepo;
    }
    @Transactional
    public ProductResponseDto addProduct(ProductRequestDto productRequestDto) throws Exception {

        CategoryEntity categoryFound = categoryRepo.findById(productRequestDto.category()).orElseThrow(() -> new Exception("Categpry Not Found"));
        ProductEntity product = new ProductEntity();
        product.setProductName(productRequestDto.name());
        product.setDescription(productRequestDto.description());
        product.setPrice(productRequestDto.price());
        product.setStockPresent(productRequestDto.stockPresent());
        product.setCategory(categoryFound);
        product.setCreatedAt(new Date(System.currentTimeMillis()));
        product.setUpdatedAt(new Date(System.currentTimeMillis()));
        ProductEntity save = productRepo.save(product);
        return mapToDto(save);
    }



    public ProductResponseDto getProductById(Long id) throws Exception {
        ProductEntity productEntity = productRepo.findById(id).orElseThrow(() -> new Exception("Product not Found By Id"));
        return mapToDto(productEntity);
    }

    @Transactional
    public ProductResponseDto updateProduct(Long id , ProductRequestDto productRequestDto) throws Exception {

        CategoryEntity category = categoryRepo.findById(productRequestDto.category()).orElseThrow(() -> new Exception("Categpry Not Found"));
        ProductEntity productEntity = productRepo.findById(id).orElseThrow(() -> new Exception("Product not Found By Id"));
        productEntity.setProductName(productRequestDto.name());
        productEntity.setDescription(productRequestDto.description());
        productEntity.setPrice(productRequestDto.price());
        productEntity.setStockPresent(productRequestDto.stockPresent());
        productEntity.setCategory(category);
        productEntity.setUpdatedAt(new Date(System.currentTimeMillis()));
        ProductEntity save = productRepo.save(productEntity);
        return mapToDto(save);
    }


    public boolean deleteProduct(Long id ){
        if(!productRepo.existsById(id)){
            return false;
        }
        productRepo.deleteById(id);
        return true;
    }


    public Page<ProductResponseDto> getAllProducts(Pageable pageable){
        Page<ProductEntity> all = productRepo.findAll(pageable);
        return all.map(product -> new ProductResponseDto(product.getProductId(),product.getProductName(), product.getDescription(), product.getPrice(), product.getStockPresent(), product.getCategory().getName()));
    }


    public Page<ProductResponseDto> getProductsByCategory(Long category_id,Pageable pageable) throws Exception {
        categoryRepo.findById(category_id).orElseThrow(()->new Exception("Category not Found"));
         return productRepo.findProductsByCategory(category_id,pageable).map(this::mapToDto);
    }








    private ProductResponseDto mapToDto(ProductEntity p){
        return new ProductResponseDto(
                p.getProductId(),
                p.getProductName(),
                p.getDescription(),
                p.getPrice(),
                p.getStockPresent(),
                p.getCategory().getName()
        );
    }
}

