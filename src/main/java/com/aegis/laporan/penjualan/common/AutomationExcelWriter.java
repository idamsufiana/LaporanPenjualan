package com.aegis.laporan.penjualan.common;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

public abstract class AutomationExcelWriter <E> extends ExcelWriter<E> {
    protected AutomationExcelWriter(List<E> entities) { super(entities); }

    protected AutomationExcelWriter() { super(); }

    public abstract void getRows(Row row, E entity);

    public abstract void getRows(Row row, E entity, XSSFWorkbook workbook);

    private static final Logger LOGGER = Logger.getLogger(AutomationExcelWriter.class.getName());

    public void setCell(Row row, int index, String value) {
        if (value == null)
            value = "";

        Cell cell = row.createCell(index);
        cell.setCellValue(value);
    }

    public void setCell(Row row, int index, Boolean value) {
        if (value == null)
            value = Boolean.valueOf("");

        Cell cell = row.createCell(index);
        cell.setCellValue(value);
    }

    public void setCell(Row row, int index, Double value) {
        Cell cell = row.createCell(index);
        if (value == null){
            cell.setCellValue("");
        } else {
            cell.setCellValue(value);
        }
    }

    public void setCell(Row row, int index, Long value) {
        Cell cell = row.createCell(index);
        if (value == null){
            cell.setCellValue("");
        } else {
            cell.setCellValue(value);
        }
    }

    public void setCell(Row row, int index, Date value) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Cell cell = row.createCell(index);
        if (value == null){
            cell.setCellValue("");
        }
        else {
            cell.setCellValue(simpleDateFormat.format(value));
        }

    }

    public void setCell(Row row, int index, Date value, Workbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();
        CreationHelper createHelper = workbook.getCreationHelper();
        cellStyle.setDataFormat(
                createHelper.createDataFormat().getFormat("dd-mm-yyyy"));
        Cell cell = row.createCell(index);
        if (value == null) {
            cell.setCellValue("");
            cell.setCellStyle(cellStyle);
        } else {
            cell.setCellValue(value);
            cell.setCellStyle(cellStyle);
        }
    }

    public void setCell(Row row, int index, Integer value) {
        if (value == null)
            value = 0;
        Cell cell = row.createCell(index);
        cell.setCellValue(value);
    }

    public XSSFWorkbook setWorkBook(XSSFWorkbook workbook, String sheetName) {
        Row header;
        Sheet sheet = workbook.createSheet(sheetName);
        sheet.setColumnWidth(0, 6000);
        sheet.setColumnWidth(1, 4000);

        header = sheet.createRow(0);

        int i = 0;
        for(String headerTitle : this.getHeaders()) {
            Cell headerCell = header.createCell(i++);
            headerCell.setCellValue(headerTitle);
        }
        CellStyle style = workbook.createCellStyle();
        style.setWrapText(true);

        int j = 0;
        for(E entity : this.entities) {
            Row row = sheet.createRow(1 + j++);
            this.getRows(row, entity, workbook);
        }
        return workbook;
    }
}