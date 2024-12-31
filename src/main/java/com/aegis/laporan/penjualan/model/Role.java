package com.aegis.laporan.penjualan.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Role extends CrudEntity{
    private String role;
    private String description;
}
