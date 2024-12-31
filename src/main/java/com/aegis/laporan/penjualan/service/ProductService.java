package com.aegis.laporan.penjualan.service;

import com.aegis.laporan.penjualan.dto.ProductDto;
import com.aegis.laporan.penjualan.exception.AegisException;
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

    public Product update(UUID id, ProductDto dto) throws Throwable {
        try {
            Product entity = new Product();
            if(get(id).isPresent()){
                entity = productRepository.findById(id).get();
                BeanUtils.copyProperties(dto, entity);
                entity.setUpdatedDate(new Date());
                return productRepository.save(entity);
            }else{
                throw new AegisException("not found");
            }
        } catch (Throwable var5) {
            Throwable $ex = var5;
            throw $ex;
        }
    }

    public void delete(UUID id) throws Throwable {
        try {
            Product entity = new Product();
            if(get(id).isPresent()) {
                entity = productRepository.findById(id).get();
                productRepository.delete(entity);
            }else {
                throw new AegisException("data not found");
            }

        } catch (Throwable var3) {
            Throwable $ex = var3;
            throw $ex;
        }
    }
}
