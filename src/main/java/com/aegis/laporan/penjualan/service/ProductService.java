package com.aegis.laporan.penjualan.service;

import com.aegis.laporan.penjualan.dto.ProductDto;
import com.aegis.laporan.penjualan.model.CrudEntity;
import com.aegis.laporan.penjualan.model.Product;
import com.aegis.laporan.penjualan.repository.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    public Product createFromDto( ProductDto dto) throws Throwable {
        try {
            Product entity = new Product();
            BeanUtils.copyProperties(dto, entity);
            entity.setCreatedDate(new Date());
            productRepository.save(entity);
            return entity;
        } catch (Throwable var5) {
            Throwable $ex = var5;
            throw $ex;
        }
    }

    public Optional<Product> get(UUID id) throws Throwable {
        try {
            return productRepository.findById(id);
        } catch (Throwable var3) {
            Throwable $ex = var3;
            throw $ex;
        }
    }

    public Product update(Product entity, ProductDto dto) throws Throwable {
        try {
            BeanUtils.copyProperties(dto, entity);
            entity.setUpdatedDate(new Date());
            return productRepository.save(entity);
        } catch (Throwable var5) {
            Throwable $ex = var5;
            throw $ex;
        }
    }

    public void delete(Product e) throws Throwable {
        try {
            productRepository.delete(e);
        } catch (Throwable var3) {
            Throwable $ex = var3;
            throw $ex;
        }
    }
}
