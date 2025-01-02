package com.aegis.laporan.penjualan.utils;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class GenUtils {

    public static byte[] exportByte(XSSFWorkbook workbook) {
        try  {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            try {
                workbook.write(bos);
            }
            finally {
                bos.close();
            }

            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }
}
