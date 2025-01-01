package com.aegis.laporan.penjualan.service;

import com.aegis.laporan.penjualan.exception.ResourceNotFoundException;
import com.aegis.laporan.penjualan.model.Product;
import com.aegis.laporan.penjualan.model.Purchase;
import com.aegis.laporan.penjualan.repository.ProductRepository;
import com.aegis.laporan.penjualan.repository.PurchaseRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PurchaseService {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    PurchaseRepository purchaseRepository;

    @Transactional
    public Purchase createPurchase(UUID productId, Integer quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        if (product.getStock() < quantity) {
            throw new IllegalStateException("Insufficient stock");
        }

        product.setStock(product.getStock() - quantity);
        productRepository.save(product);

        Purchase purchase = new Purchase();
        purchase.setProduct(product);
        purchase.setQuantity(quantity);
        return purchaseRepository.save(purchase);

    }
}
