package com.aegis.laporan.penjualan.model;

import com.aegis.laporan.penjualan.constant.Group;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "role")
public class Role extends CrudEntity{
    private Group role;
    private String description;
}
