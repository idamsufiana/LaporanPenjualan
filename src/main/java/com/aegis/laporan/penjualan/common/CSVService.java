package com.aegis.laporan.penjualan.common;
import org.apache.poi.util.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import org.apache.commons.beanutils.BeanUtils;

public abstract class CSVService<D> {

    public List<D> fromCSV(InputStream stream, String separator) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        IOUtils.copy(stream, byteArrayOutputStream);
        stream.close();
        List<D> result = new ArrayList();
        String content = new String(byteArrayOutputStream.toByteArray());
        String[] lines = content.split("\\r?\\n");

        for(int i = 1; i < lines.length; ++i) {
            String line = lines[i];
            D row = this.fromCSV(line.split(separator));
            if (row != null) {
                result.add(row);
            }
        }

        return result;
    }

    public D fromCSV(String[] lines) {
        return null;
    }

    public List<D> uploadFromCSV(InputStream stream, String separator) throws IOException {
        List<D> dtos = this.fromCSV(stream, separator);
        Iterator var4 = dtos.iterator();

        while(var4.hasNext()) {
            D e = (D) var4.next();
            this.createEntity(e);
        }

        return dtos;
    }

    public abstract D createEntity(D var1);

    public String convertToCSV(D dto) {
        List<String> result = new ArrayList();
        String[] var3 = this.getCSVColumns();
        int var4 = var3.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            String column = var3[var5];

            try {
                result.add(BeanUtils.getProperty(dto, column));
            } catch (Exception var8) {
                result.add("");
            }
        }

        return String.join(";", result);
    }

    public String[] getCSVColumns() {
        return new String[]{"id", "name"};
    }

    public byte[] toCSV(List<D> dtos) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintWriter pw = new PrintWriter(out);

        try {
            pw.println(this.getCSVHeader());
            Stream var10000 = dtos.stream().map(this::convertToCSV);
            Objects.requireNonNull(pw);
            var10000.forEach(pw::println);
        } catch (Throwable var7) {
            try {
                pw.close();
            } catch (Throwable var6) {
                var7.addSuppressed(var6);
            }

            throw var7;
        }

        pw.close();
        return out.toByteArray();
    }

    public byte[] startExport(List<D> dtos) {
        try {
            return this.toCSV(dtos);
        } catch (Exception var3) {
            Exception e = var3;
            e.printStackTrace();
            return null;
        }
    }

    public String getCSVHeader() {
        String[] header = new String[this.getCSVColumns().length];
        int i = 0;
        String[] var3 = this.getCSVColumns();
        int var4 = var3.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            String s = var3[var5];
            header[i++] = this.splitCamelCase(s).toUpperCase();
        }

        return String.join(";", header);
    }

    private String splitCamelCase(String s) {
        return s.replaceAll(String.format("%s|%s|%s", "(?<=[A-Z])(?=[A-Z][a-z])", "(?<=[^A-Z])(?=[A-Z])", "(?<=[A-Za-z])(?=[^A-Za-z])"), " ");
    }
}