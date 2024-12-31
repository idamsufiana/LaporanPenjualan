package com.aegis.laporan.penjualan.repository;

import com.aegis.laporan.penjualan.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
}
