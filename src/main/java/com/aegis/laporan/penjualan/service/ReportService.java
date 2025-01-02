package com.aegis.laporan.penjualan.service;

import com.aegis.laporan.penjualan.common.AutomationToExcel;
import com.aegis.laporan.penjualan.dto.TransactionDto;
import com.aegis.laporan.penjualan.exception.AegisException;
import com.aegis.laporan.penjualan.model.Purchase;
import com.aegis.laporan.penjualan.repository.PurchaseRepository;
import com.aegis.laporan.penjualan.utils.GenUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Service
public class ReportService {

    @Autowired
    PurchaseRepository purchaseRepository;

    public List<Purchase> getReportByDateRange(LocalDate startDate, LocalDate endDate) {
        return purchaseRepository.findByTransactionDateBetween(startDate, endDate);
    }

    public byte[] exportBulk (List<Purchase> purchaseList) throws AegisException {
        List<TransactionDto> transactionDtos = new ArrayList<>();

        for (Purchase purchase : purchaseList) {
            TransactionDto transactionDto = new TransactionDto();
            transactionDto.setProductName(purchase.getProduct().getName());

            transactionDtos.add(transactionDto);

        }
        AutomationToExcel dataToExcel = new AutomationToExcel(transactionDtos);
        XSSFWorkbook workbook = new XSSFWorkbook();
        workbook = dataToExcel.setWorkBook(workbook, "report");

        return GenUtils.exportByte(workbook);
    }

}
