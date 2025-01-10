package com.aegis.laporan.penjualan.controller;

import com.aegis.laporan.penjualan.exception.AegisException;
import com.aegis.laporan.penjualan.model.Purchase;
import com.aegis.laporan.penjualan.service.PurchaseService;
import com.aegis.laporan.penjualan.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @Autowired
    ReportService reportService;

    @PreAuthorize("hasRole('KASIR','ADMIN')")
    @GetMapping("/transaction")
    public ResponseEntity<Resource> getLaporan(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) throws AegisException {
        List<Purchase> laporan = reportService.getReportByDateRange(startDate, endDate);
        return okDownload("report.xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                reportService.exportBulk(laporan));
    }


}
