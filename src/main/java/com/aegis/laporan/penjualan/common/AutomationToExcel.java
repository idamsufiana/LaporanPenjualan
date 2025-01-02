package com.aegis.laporan.penjualan.common;

import com.aegis.laporan.penjualan.dto.TransactionDto;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.ArrayList;
import java.util.List;

public class AutomationToExcel extends AutomationExcelWriter<TransactionDto> {

    public AutomationToExcel(List<TransactionDto> TransactionDtos) {
        super(TransactionDtos);
    }

    @Override
    public List<String> getHeaders() {
        List<String> headers = new ArrayList<>();

        headers.add("Product Name");
        headers.add("Quantity");
        headers.add("Total");
        headers.add("TransactionDate");

        return headers;
    }

    @Override
    public void getRows(Row row, TransactionDto entity) {

    }

    @Override
    public void getRows(Row row, TransactionDto entity, XSSFWorkbook workbook) {
        setCell(row, 0, entity.getProductName() != null ? entity.getProductName() : null);
        setCell(row, 1, entity.getQuantity() != null ? entity.getQuantity() : null);
        setCell(row, 2, entity.getTotal() != null ? entity.getTotal() : null);
        setCell(row, 3, String.valueOf(entity.getTransactionDate() != null ? entity.getTransactionDate() : null));
    }
}