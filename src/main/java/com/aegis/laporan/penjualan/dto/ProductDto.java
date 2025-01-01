package com.aegis.laporan.penjualan.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto {
    private String name;
    private Integer stock;
    private Double price;
}
