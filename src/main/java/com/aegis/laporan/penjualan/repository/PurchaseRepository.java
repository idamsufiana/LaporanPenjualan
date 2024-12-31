package com.aegis.laporan.penjualan.repository;

import com.aegis.laporan.penjualan.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PurchaseRepository extends JpaRepository<Purchase, UUID> {
}
