package com.aegis.laporan.penjualan.repository;

import com.aegis.laporan.penjualan.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, UUID> {
    List<Purchase> findByDateBetween(LocalDate startDate, LocalDate endDate);
}
