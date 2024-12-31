package com.aegis.laporan.penjualan.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "product")
public class Product extends CrudEntity{
    private String name;
    private Integer stock;
    private Double price;
}
