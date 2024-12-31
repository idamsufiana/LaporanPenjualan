package com.aegis.laporan.penjualan.controller;

import com.aegis.laporan.penjualan.constant.ApplicationEnum;
import com.aegis.laporan.penjualan.constant.Secured;
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

    @Secured({ApplicationEnum.Group.Kasir, ApplicationEnum.Group.Admin})
    @GetMapping("/{id}")
    public Object Get(@PathVariable UUID id) throws Throwable {
        Optional<Product> product = productService.get(id);
        if(product.isPresent()){
            return success(productService.get(id).get());
        }else {
            throw new AegisException("no data found");
        }

    }

    @Secured({ApplicationEnum.Group.Kasir, ApplicationEnum.Group.Admin})
    @PostMapping("/update/{id}")
    public Object Update(@PathVariable UUID id, @RequestBody ProductDto dto) throws Throwable {
        return success(productService.update(id, dto));

    }

    @Secured({ApplicationEnum.Group.Kasir, ApplicationEnum.Group.Admin})
    @PostMapping("/add")
    public Object add(@RequestBody ProductDto dto) throws Throwable {
        return success(productService.createFromDto(dto));
    }

    @Secured({ApplicationEnum.Group.Kasir, ApplicationEnum.Group.Admin})
    @DeleteMapping("/delete/{id}")
    public Object add(@PathVariable UUID id) throws Throwable {
        return success(productService.delete(id));
    }
}
