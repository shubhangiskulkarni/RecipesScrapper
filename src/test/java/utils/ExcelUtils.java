package utils;

import base.TestBase;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class ExcelUtils extends TestBase {

     public void createFile(String sheetName, String fileLoc) {

         try (Workbook workbook = new XSSFWorkbook()) {

             // Create the folder if it doesn't exist
             createFolderIfNotExists(fileLoc);


            Sheet sheet = workbook.createSheet(sheetName);

            // Create a row for headers
            Row headerRow = sheet.createRow(0);

//            Recipe ID, Recipe Name, Recipe Category(Breakfast/lunch/snack/dinner), Food Category(Veg/non-veg/vegan/Jain)
//            Ingredients, Preparation Time, Cooking Time, Preparation method, Nutrient values, Targetted morbid conditions (Diabeties/Hypertension/Hypothyroidism)
//            Recipe URL

            // Specify column headers
            String[] headers = {"Recipe ID", "Recipe Name", "Recipe Category", "Food Category", "Ingredients", "Preparation Time", "Cooking Time", "Preparation method", "Nutrient values", "Targetted morbid conditions", "Recipe URL"};

            // Create cells for headers
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }

            // Save the workbook to a file
            try (FileOutputStream fileOut = new FileOutputStream(fileLoc + sheetName + ".xlsx")) {
                workbook.write(fileOut);
                System.out.println("Excel file created successfully!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void createFolderIfNotExists(String folderPath) {

        Path path = Paths.get(folderPath);
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
                System.out.println("Folder created: " + folderPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    public void WriteToExcelFile(String sheetName, String fileLoc, String[] data, int rowNumber) {

        // Create the folder if it doesn't exist
        createFolderIfNotExists(fileLoc);

        String filePath = fileLoc + sheetName + ".xlsx";

        // Specify column headers
        String[] headers = {"Recipe ID", "Recipe Name", "Recipe Category", "Food Category", "Ingredients", "Preparation Time", "Cooking Time", "Preparation method", "Nutrient values", "Targetted morbid conditions", "Recipe URL"};
//        String[] data = {"Data1", "Data2", "Data3"};


        try (FileInputStream file = new FileInputStream(filePath)) {
            Workbook workbook = new XSSFWorkbook(file);

            Sheet sheet = workbook.getSheet(sheetName);

            if(sheet == null) {
                sheet = workbook.createSheet(sheetName);
            }

            // Find the row index where headers are located
            int headerRowIndex = findHeaderRowIndex(sheet, headers);

            // If headers are found, proceed to write data
            if (headerRowIndex != -1) {

                int lastRowNum = sheet.getLastRowNum();

                Row dataRow = sheet.createRow(headerRowIndex + rowNumber); // Create a row for data

                System.out.println("Writting to file: " + filePath + " Row num is " + rowNumber);

                // Write data under specific columns based on header name
                writeDataUnderHeaders(dataRow, headers, data); // Replace with your data

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
    // Inside the findHeaderRowIndex method
    private static int findHeaderRowIndex(Sheet sheet, String[] headers) {
        for (Row row : sheet) {
            for (Cell cell : row) {
                if (cell.getCellType() == CellType.STRING) {
                    for (String header : headers) {
                        if (header.equals(cell.getStringCellValue().trim())) {
                            System.out.println("Header found: " + header + " at row " + row.getRowNum());
                            return row.getRowNum();
                        }
                    }
                }
            }
        }
        System.out.println("Headers not found in the specified sheet!");
        return -1;
    }


    // Helper method to write data under specific columns based on header name
    private static void writeDataUnderHeaders(Row dataRow, String[] headers, String[] data) {

        Sheet sheet = dataRow.getSheet();
        for (int i = 0; i < headers.length && i < data.length; i++) {
            int columnIndex = getColumnIndexForHeader(sheet, headers[i]);
            if (columnIndex != -1) {
                Cell cell = dataRow.createCell(columnIndex);
                cell.setCellValue(data[i]);
            }
        }
    }

    // Helper method to get the column index for a given header name
    // Inside the getColumnIndexForHeader method
    private static int getColumnIndexForHeader(Sheet sheet, String header) {

        Row headerRow = sheet.getRow(0); // Assuming headers are in the first row

        if (headerRow != null) {
            for (Cell cell : headerRow) {
                if (cell.getCellType() == CellType.STRING) {
                    String cellValue = cell.getStringCellValue().trim();
                    if (header.equalsIgnoreCase(cellValue)) {
//                        System.out.println("Header '" + header + "' found at column index: " + cell.getColumnIndex());
                        return cell.getColumnIndex();
                    }
                }
            }
        }

//        System.out.println("Header '" + header + "' not found in the specified sheet!");
        return -1;
    }










}
