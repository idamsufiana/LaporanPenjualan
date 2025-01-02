package com.aegis.laporan.penjualan.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class TransactionDto {
    private String ProductName;
    private Integer quantity;
    private Double total;
    private LocalDateTime transactionDate;
}
