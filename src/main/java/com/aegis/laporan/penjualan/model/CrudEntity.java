package com.aegis.laporan.penjualan.model;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
public abstract class CrudEntity implements Serializable {
    @Id
    protected UUID id;
    protected Date createdDate;
    protected Date updatedDate;
    protected String createdBy;
    protected String updatedBy;

}
