package com.aegis.laporan.penjualan.model.request;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class PurchaseRequest {
    private UUID id;
    private Integer quatity;
}
