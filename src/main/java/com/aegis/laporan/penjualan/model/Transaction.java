package com.aegis.laporan.penjualan.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Transaction extends CrudEntity{
    private Integer quantity;
    private Double total;
    @ManyToOne
    private Product product;
}
