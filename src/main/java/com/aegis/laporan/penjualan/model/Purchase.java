package com.aegis.laporan.penjualan.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "purchase")
public class Purchase extends CrudEntity{
    private Integer quantity;
    private Double total;
    private LocalDateTime transactionDate;
    @ManyToOne
    private Product product;
}
