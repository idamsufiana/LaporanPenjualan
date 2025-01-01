package com.aegis.laporan.penjualan.controller;

import com.aegis.laporan.penjualan.model.Purchase;
import com.aegis.laporan.penjualan.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/v1/Report")
public class ReportController extends BaseController{

    @Autowired
    PurchaseService purchaseService;

    @GetMapping("/transaction")
    public ResponseEntity<Object> getLaporan(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<Purchase> laporan = purchaseService.getReportByDateRange(startDate, endDate);
        return success(laporan);
    }


}
