package com.example.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

public class ExcelUtils {
    private static final String FILE_PATH = "src/test/resources/testdata.xlsx";

    public static String[] getCredentials(int rowIndex) {
        try (FileInputStream fis = new FileInputStream(FILE_PATH);
             Workbook workbook = new XSSFWorkbook(fis)) {
            
            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(rowIndex);

            if (row != null) {
                String username = row.getCell(0).getStringCellValue();
                String password = row.getCell(1).getStringCellValue();
                return new String[]{username, password};
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String[]{"", ""};
    }
}
