package com.aegis.laporan.penjualan.common;

import org.apache.poi.ss.usermodel.Cell;

public class StringValue implements ExcelWritable {
    private final String value;
    private final String defaultValue;

    public StringValue(String value, String defaultValue) {
        this.value = value;
        this.defaultValue = defaultValue == null ? "" : defaultValue;
    }

    public void writeCellValue(Cell cell) {
        cell.setCellValue(this.value == null ? this.defaultValue : this.value);
    }
}
