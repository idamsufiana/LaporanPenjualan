package com.aegis.laporan.penjualan.controller;

import com.aegis.laporan.penjualan.dto.request.PurchaseRequest;
import com.aegis.laporan.penjualan.dto.request.RefundRequest;
import com.aegis.laporan.penjualan.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/Purchase")
public class PurchaseController extends BaseController{

    @Autowired
    PurchaseService purchaseService;

    @PreAuthorize("hasRole('KASIR','ADMIN')")
    @PostMapping("")
    public Object Purchase(@RequestBody PurchaseRequest data) throws Throwable {
        return success(purchaseService.createPurchase(data.getId(), data.getQuatity()));
    }

    @PreAuthorize("hasRole('KASIR','ADMIN')")
    @PostMapping("/refund")
    public Object Purchase(@RequestBody RefundRequest data) throws Throwable {
        return success(purchaseService.refund(data.getId(), data.getQuatity()));
    }
}
