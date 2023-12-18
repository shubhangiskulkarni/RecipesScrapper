package utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
public class excelTemp {



    public void WriteToExcelFile() {

            String filePath = "path_to_your_existing_excel_file.xlsx";
            String[] headers = {"Column1", "Column2", "Column3"}; // Your specific column headers

            try (FileInputStream file = new FileInputStream(filePath)) {
                Workbook workbook = new XSSFWorkbook(file);
                Sheet sheet = workbook.getSheetAt(0); // Assuming you're working with the first sheet

                // Find the row index where headers are located
                int headerRowIndex = findHeaderRowIndex(sheet, headers);

                // If headers are found, proceed to write data
                if (headerRowIndex != -1) {
                    Row dataRow = sheet.createRow(headerRowIndex + 1); // Create a row for data

                    // Write data under specific columns based on header name
                    writeDataUnderHeaders(dataRow, headers, "Data1", "Data2", "Data3"); // Replace with your data

                    // Save changes back to the Excel file
                    try (FileOutputStream outFile = new FileOutputStream(filePath)) {
                        workbook.write(outFile);
                        System.out.println("Data written successfully!");
                    }
                } else {
                    System.out.println("Headers not found in the specified sheet!");
                }

                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Helper method to find the row index where headers are located
        private static int findHeaderRowIndex(Sheet sheet, String[] headers) {
            for (Row row : sheet) {
                for (Cell cell : row) {
                    if (cell.getCellType() == CellType.STRING) {
                        for (String header : headers) {
                            if (header.equals(cell.getStringCellValue().trim())) {
                                return row.getRowNum();
                            }
                        }
                    }
                }
            }
            return -1;
        }

        // Helper method to write data under specific columns based on header name
        private static void writeDataUnderHeaders(Row dataRow, String[] headers, String... data) {
            for (String datum : data) {
                int columnIndex = getColumnIndexForHeader(dataRow.getSheet(), headers[dataRow.getLastCellNum()]);
                if (columnIndex != -1) {
                    Cell cell = dataRow.createCell(columnIndex);
                    cell.setCellValue(datum);
                }
            }
        }

        // Helper method to get the column index for a given header name
        private static int getColumnIndexForHeader(Sheet sheet, String header) {
            Row headerRow = sheet.getRow(0); // Assuming headers are in the first row
            if (headerRow != null) {
                for (Cell cell : headerRow) {
                    if (cell.getCellType() == CellType.STRING && header.equalsIgnoreCase(cell.getStringCellValue().trim())) {
                        return cell.getColumnIndex();
                    }
                }
            }
            return -1;
        }


}
