package com.aegis.laporan.penjualan.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "user")
public class User extends CrudEntity{
    private String UserName;
    private String Password;
    private String phone;
    private String email;
    @ManyToOne
    private Role role;
}
