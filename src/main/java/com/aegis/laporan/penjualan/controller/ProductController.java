package com.aegis.laporan.penjualan.controller;

import com.aegis.laporan.penjualan.dto.ProductDto;
import com.aegis.laporan.penjualan.exception.AegisException;
import com.aegis.laporan.penjualan.model.Product;
import com.aegis.laporan.penjualan.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/v1/Product")
public class ProductController extends BaseController{

    @Autowired
    ProductService productService;

    @GetMapping("/{id}")
    public Object Get(@PathVariable UUID id) throws Throwable {
        Optional<Product> product = productService.get(id);
        if(product.isPresent()){
            return success(productService.get(id).get());
        }else {
            throw new AegisException("no data found");
        }

    }

    @PostMapping("/update/{id}")
    public Object Update(@PathVariable UUID id, @RequestBody ProductDto dto) throws Throwable {
        Optional<Product> product = productService.update(id);
        if(product.isPresent()){
            return success(productService.get(id).get());
        }else {
            throw new AegisException("no data found");
        }

    }

    @PostMapping("/add")
    public Object add(@RequestBody ProductDto dto) throws Throwable {
        return success(productService.createFromDto(dto));
    }
}
