package com.aegis.laporan.penjualan.repository;

import com.aegis.laporan.penjualan.model.Purchase;
import com.aegis.laporan.penjualan.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    User findFirstByUserName(String name);
}