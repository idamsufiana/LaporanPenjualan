package com.aegis.laporan.penjualan.common;

import com.aegis.laporan.penjualan.common.ExcelWritable;
import com.aegis.laporan.penjualan.common.StringValue;
import org.apache.poi.ss.formula.eval.NotImplementedException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;

public abstract class ExcelWriter<E> {
    private static final Logger log = LoggerFactory.getLogger(ExcelWriter.class);
    private static final String ERROR = "Error : ";
    protected List<E> entities;

    protected ExcelWriter(List<E> entities) {
        this.entities = entities;
    }

    protected ExcelWriter() {
    }

    public abstract List<String> getHeaders();

    public void getRows(Row row, E entity) {
        throw new NotImplementedException("Need to be implemented for normal functions");
    }

    public void getRows(Workbook workbook, Sheet sheet, Row row, E entity) {
        throw new NotImplementedException("Needs to be implemented for functions that have a sheetNames parameter.");
    }

    public OutputStream export(OutputStream outputStream) {
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();

            OutputStream var19;
            try {
                Sheet sheet = workbook.createSheet(this.getClass().getSimpleName());
                sheet.setColumnWidth(0, 6000);
                sheet.setColumnWidth(1, 4000);
                Row header = sheet.createRow(0);
                CellStyle headerStyle = workbook.createCellStyle();
                headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
                headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                int i = 0;
                Iterator var7 = this.getHeaders().iterator();

                while(var7.hasNext()) {
                    String headerTitle = (String)var7.next();
                    Cell headerCell = header.createCell(i++);
                    headerCell.setCellValue(headerTitle);
                    headerCell.setCellStyle(headerStyle);
                }

                CellStyle style = workbook.createCellStyle();
                style.setWrapText(true);
                int j = 0;
                Iterator var18 = this.entities.iterator();

                while(true) {
                    if (!var18.hasNext()) {
                        workbook.write(outputStream);
                        var19 = outputStream;
                        break;
                    }

                    E entity = (E) var18.next();
                    Row row = sheet.createRow(2 + j++);
                    this.getRows(row, entity);
                }
            } catch (Throwable var13) {
                try {
                    workbook.close();
                } catch (Throwable var12) {
                    var13.addSuppressed(var12);
                }

                throw var13;
            }

            workbook.close();
            return var19;
        } catch (IOException var14) {
            IOException e = var14;
            log.error("Error : ", e);
            return null;
        }
    }

    public OutputStream export(OutputStream outputStream, String... sheetNames) {
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();

            OutputStream var21;
            try {
                String[] var4 = sheetNames;
                int var5 = sheetNames.length;
                int var6 = 0;

                while(true) {
                    if (var6 >= var5) {
                        workbook.write(outputStream);
                        var21 = outputStream;
                        break;
                    }

                    String sheetName = var4[var6];
                    Sheet sheet = workbook.createSheet(sheetName);
                    sheet.setColumnWidth(0, 6000);
                    sheet.setColumnWidth(1, 4000);
                    Row header = sheet.createRow(0);
                    CellStyle headerStyle = workbook.createCellStyle();
                    headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
                    headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                    int i = 0;
                    Iterator var12 = this.getHeaders().iterator();

                    while(var12.hasNext()) {
                        String headerTitle = (String)var12.next();
                        Cell headerCell = header.createCell(i++);
                        headerCell.setCellValue(headerTitle);
                        headerCell.setCellStyle(headerStyle);
                    }

                    CellStyle style = workbook.createCellStyle();
                    style.setWrapText(true);
                    int j = 0;
                    Iterator var24 = this.entities.iterator();

                    while(var24.hasNext()) {
                        E entity = (E) var24.next();
                        Row row = sheet.createRow(2 + j++);
                        this.getRows(workbook, sheet, row, entity);
                    }

                    ++var6;
                }
            } catch (Throwable var18) {
                try {
                    workbook.close();
                } catch (Throwable var17) {
                    var18.addSuppressed(var17);
                }

                throw var18;
            }

            workbook.close();
            return var21;
        } catch (IOException var19) {
            IOException e = var19;
            log.error("Error : ", e);
            return null;
        }
    }

    public byte[] exportByte() {
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();

            byte[] var18;
            try {
                Sheet sheet = workbook.createSheet(this.getClass().getSimpleName());
                sheet.setColumnWidth(0, 6000);
                sheet.setColumnWidth(1, 4000);
                Row header = sheet.createRow(0);
                int i = 0;
                Iterator var5 = this.getHeaders().iterator();

                while(var5.hasNext()) {
                    String headerTitle = (String)var5.next();
                    Cell headerCell = header.createCell(i++);
                    headerCell.setCellValue(headerTitle);
                }

                CellStyle style = workbook.createCellStyle();
                style.setWrapText(true);
                int j = 0;
                Iterator var16 = this.entities.iterator();

                while(true) {
                    if (!var16.hasNext()) {
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        workbook.write(bos);
                        var18 = bos.toByteArray();
                        break;
                    }

                    E entity = (E) var16.next();
                    Row row = sheet.createRow(1 + j++);
                    this.getRows(row, entity);
                }
            } catch (Throwable var11) {
                try {
                    workbook.close();
                } catch (Throwable var10) {
                    var11.addSuppressed(var10);
                }

                throw var11;
            }

            workbook.close();
            return var18;
        } catch (IOException var12) {
            IOException e = var12;
            log.error("Error : ", e);
            return new byte[0];
        }
    }

    public byte[] exportByte(String... sheetNames) {
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();

            byte[] var20;
            try {
                String[] var3 = sheetNames;
                int var4 = sheetNames.length;
                int var5 = 0;

                while(true) {
                    if (var5 >= var4) {
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        workbook.write(bos);
                        var20 = bos.toByteArray();
                        break;
                    }

                    String sheetName = var3[var5];
                    Sheet sheet = workbook.createSheet(sheetName);
                    sheet.setColumnWidth(0, 6000);
                    sheet.setColumnWidth(1, 4000);
                    Row header = sheet.createRow(0);
                    int i = 0;
                    Iterator var10 = this.getHeaders().iterator();

                    while(var10.hasNext()) {
                        String headerTitle = (String)var10.next();
                        Cell headerCell = header.createCell(i++);
                        headerCell.setCellValue(headerTitle);
                    }

                    CellStyle style = workbook.createCellStyle();
                    style.setWrapText(true);
                    int j = 0;
                    Iterator var23 = this.entities.iterator();

                    while(var23.hasNext()) {
                        E entity = (E) var23.next();
                        Row row = sheet.createRow(1 + j++);
                        this.getRows(workbook, sheet, row, entity);
                    }

                    ++var5;
                }
            } catch (Throwable var16) {
                try {
                    workbook.close();
                } catch (Throwable var15) {
                    var16.addSuppressed(var15);
                }

                throw var16;
            }

            workbook.close();
            return var20;
        } catch (IOException var17) {
            IOException e = var17;
            log.error("Error : ", e);
            return new byte[0];
        }
    }

    public OutputStream exportXlsx(OutputStream outputStream) {
        try {
            SXSSFWorkbook workbook = new SXSSFWorkbook();

            OutputStream var19;
            try {
                Sheet sheet = workbook.createSheet(this.getClass().getSimpleName());
                sheet.setColumnWidth(0, 6000);
                sheet.setColumnWidth(1, 4000);
                Row header = sheet.createRow(0);
                CellStyle headerStyle = workbook.createCellStyle();
                headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
                headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                int i = 0;
                Iterator var7 = this.getHeaders().iterator();

                while(var7.hasNext()) {
                    String headerTitle = (String)var7.next();
                    Cell headerCell = header.createCell(i++);
                    headerCell.setCellValue(headerTitle);
                    headerCell.setCellStyle(headerStyle);
                }

                CellStyle style = workbook.createCellStyle();
                style.setWrapText(true);
                int j = 0;
                Iterator var18 = this.entities.iterator();

                while(true) {
                    if (!var18.hasNext()) {
                        workbook.write(outputStream);
                        var19 = outputStream;
                        break;
                    }

                    E entity = (E) var18.next();
                    Row row = sheet.createRow(2 + j++);
                    this.getRows(row, entity);
                }
            } catch (Throwable var13) {
                try {
                    workbook.close();
                } catch (Throwable var12) {
                    var13.addSuppressed(var12);
                }

                throw var13;
            }

            workbook.close();
            return var19;
        } catch (IOException var14) {
            IOException e = var14;
            log.error("Error : ", e);
            return null;
        }
    }

    public OutputStream exportXlsx(OutputStream outputStream, String... sheetNames) {
        try {
            SXSSFWorkbook workbook = new SXSSFWorkbook();

            OutputStream var21;
            try {
                String[] var4 = sheetNames;
                int var5 = sheetNames.length;
                int var6 = 0;

                while(true) {
                    if (var6 >= var5) {
                        workbook.write(outputStream);
                        var21 = outputStream;
                        break;
                    }

                    String sheetName = var4[var6];
                    Sheet sheet = workbook.createSheet(sheetName);
                    sheet.setColumnWidth(0, 6000);
                    sheet.setColumnWidth(1, 4000);
                    Row header = sheet.createRow(0);
                    CellStyle headerStyle = workbook.createCellStyle();
                    headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
                    headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                    int i = 0;
                    Iterator var12 = this.getHeaders().iterator();

                    while(var12.hasNext()) {
                        String headerTitle = (String)var12.next();
                        Cell headerCell = header.createCell(i++);
                        headerCell.setCellValue(headerTitle);
                        headerCell.setCellStyle(headerStyle);
                    }

                    CellStyle style = workbook.createCellStyle();
                    style.setWrapText(true);
                    int j = 0;
                    Iterator var24 = this.entities.iterator();

                    while(var24.hasNext()) {
                        E entity = (E) var24.next();
                        Row row = sheet.createRow(2 + j++);
                        this.getRows(workbook, sheet, row, entity);
                    }

                    ++var6;
                }
            } catch (Throwable var18) {
                try {
                    workbook.close();
                } catch (Throwable var17) {
                    var18.addSuppressed(var17);
                }

                throw var18;
            }

            workbook.close();
            return var21;
        } catch (IOException var19) {
            IOException e = var19;
            log.error("Error : ", e);
            return null;
        }
    }

    public byte[] exportXlsxByte() {
        try {
            SXSSFWorkbook workbook = new SXSSFWorkbook();

            byte[] var18;
            try {
                Sheet sheet = workbook.createSheet(this.getClass().getSimpleName());
                sheet.setColumnWidth(0, 6000);
                sheet.setColumnWidth(1, 4000);
                Row header = sheet.createRow(0);
                int i = 0;
                Iterator var5 = this.getHeaders().iterator();

                while(var5.hasNext()) {
                    String headerTitle = (String)var5.next();
                    Cell headerCell = header.createCell(i++);
                    headerCell.setCellValue(headerTitle);
                }

                CellStyle style = workbook.createCellStyle();
                style.setWrapText(true);
                int j = 0;
                Iterator var16 = this.entities.iterator();

                while(true) {
                    if (!var16.hasNext()) {
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        workbook.write(bos);
                        var18 = bos.toByteArray();
                        break;
                    }

                    E entity = (E) var16.next();
                    Row row = sheet.createRow(1 + j++);
                    this.getRows(row, entity);
                }
            } catch (Throwable var11) {
                try {
                    workbook.close();
                } catch (Throwable var10) {
                    var11.addSuppressed(var10);
                }

                throw var11;
            }

            workbook.close();
            return var18;
        } catch (IOException var12) {
            IOException e = var12;
            log.error("Error : ", e);
            return new byte[0];
        }
    }

    public byte[] exportXlsxByte(String... sheetNames) {
        try {
            SXSSFWorkbook workbook = new SXSSFWorkbook();

            byte[] var20;
            try {
                String[] var3 = sheetNames;
                int var4 = sheetNames.length;
                int var5 = 0;

                while(true) {
                    if (var5 >= var4) {
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        workbook.write(bos);
                        var20 = bos.toByteArray();
                        break;
                    }

                    String sheetName = var3[var5];
                    Sheet sheet = workbook.createSheet(sheetName);
                    sheet.setColumnWidth(0, 6000);
                    sheet.setColumnWidth(1, 4000);
                    Row header = sheet.createRow(0);
                    int i = 0;
                    Iterator var10 = this.getHeaders().iterator();

                    while(var10.hasNext()) {
                        String headerTitle = (String)var10.next();
                        Cell headerCell = header.createCell(i++);
                        headerCell.setCellValue(headerTitle);
                    }

                    CellStyle style = workbook.createCellStyle();
                    style.setWrapText(true);
                    int j = 0;
                    Iterator var23 = this.entities.iterator();

                    while(var23.hasNext()) {
                        E entity = (E) var23.next();
                        Row row = sheet.createRow(1 + j++);
                        this.getRows(workbook, sheet, row, entity);
                    }

                    ++var5;
                }
            } catch (Throwable var16) {
                try {
                    workbook.close();
                } catch (Throwable var15) {
                    var16.addSuppressed(var15);
                }

                throw var16;
            }

            workbook.close();
            return var20;
        } catch (IOException var17) {
            IOException e = var17;
            log.error("Error : ", e);
            return new byte[0];
        }
    }

    public ExcelWriter<E> setCell(Row row, int index, ExcelWritable value) {
        this.setCell(row, index, value, (CellStyle)null);
        return this;
    }

    public ExcelWriter<E> setCell(Row row, int index, ExcelWritable value, CellStyle cellStyle) {
        if (value == null) {
            value = new StringValue("", "");
        }

        Cell cell = row.createCell(index);
        if (cellStyle != null) {
            cell.setCellStyle(cellStyle);
        }

        ((ExcelWritable)value).writeCellValue(cell);
        return this;
    }
}
