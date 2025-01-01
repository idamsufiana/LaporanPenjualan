package com.aegis.laporan.penjualan.controller;

import com.aegis.laporan.penjualan.constant.ApplicationEnum;
import com.aegis.laporan.penjualan.constant.Secured;
import com.aegis.laporan.penjualan.dto.ProductDto;
import com.aegis.laporan.penjualan.model.Purchase;
import com.aegis.laporan.penjualan.model.request.PurchaseRequest;
import com.aegis.laporan.penjualan.service.PurchaseService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/v1/Purchase")
public class PurchaseController extends BaseController{

    @Autowired
    PurchaseService purchaseService;

    @Secured({ApplicationEnum.Group.Kasir, ApplicationEnum.Group.Admin})
    @PostMapping("/add")
    public Object Purchase(@RequestBody PurchaseRequest data) throws Throwable {
        return success(purchaseService.createPurchase(data.getId(), data.getQuatity()));
    }
}
