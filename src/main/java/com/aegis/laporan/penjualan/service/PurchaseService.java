package com.aegis.laporan.penjualan.service;

import com.aegis.laporan.penjualan.model.Purchase;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class PurchaseService {

    @Transactional
    public Purchase createPurchase(Long productId, Integer quantity) {
        return new Purchase();
    }
}
