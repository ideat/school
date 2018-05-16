package mindware.com.utilities;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;

public class OpenExcel {
    public OpenExcel() {}

    public ArrayList<String[]> readExcelFile(File excelFile) throws IOException {
        ArrayList<String[]> arrayDatos = new ArrayList<>();
        InputStream excelStream = null;
        try {
            excelStream = new FileInputStream(excelFile);
            // High level representation of a workbook.
            // Representación del más alto nivel de la hoja excel.
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(excelStream);

            // We chose the sheet is passed as parameter.
            // Elegimos la hoja que se pasa por parámetro.
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
            // An object that allows us to read a row of the excel sheet, and extract from it the cell contents.
            // Objeto que nos permite leer un fila de la hoja excel, y de aquí extraer el contenido de las celdas.
            XSSFRow xssfRow = xssfSheet.getRow(xssfSheet.getTopRow());
            String [] datos = new String[xssfRow.getLastCellNum()];
            // For this example we'll loop through the rows getting the data we want
            // Para este ejemplo vamos a recorrer las filas obteniendo los datos que queremos
            for (Row row: xssfSheet) {
                for (Cell cell : row) {
                /*
                    We have those cell types (tenemos estos tipos de celda):
                        CELL_TYPE_BLANK, CELL_TYPE_NUMERIC, CELL_TYPE_BLANK, CELL_TYPE_FORMULA, CELL_TYPE_BOOLEAN, CELL_TYPE_ERROR
                */
                    datos[cell.getColumnIndex()] =
                            (cell.getCellTypeEnum() == CellType.STRING)?cell.getStringCellValue():
                                    (cell.getCellTypeEnum() == CellType.NUMERIC)?"" + cell.getNumericCellValue():
                                            (cell.getCellTypeEnum() == CellType.BOOLEAN)?"" + cell.getBooleanCellValue():
                                                    (cell.getCellTypeEnum() == CellType.BLANK)?"BLANK":
                                                            (cell.getCellTypeEnum() == CellType.FORMULA)?"FORMULA":
                                                                    (cell.getCellTypeEnum() == CellType.ERROR)?"ERROR":"";
                }
                arrayDatos.add(datos);
                datos = new String[xssfRow.getLastCellNum()];
            }
        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println("The file not exists (No se encontró el fichero): " + fileNotFoundException);
        } catch (IOException ex) {
            System.out.println("Error in file procesing (Error al procesar el fichero): " + ex);
        } finally {
            try {
                excelStream.close();
            } catch (IOException ex) {
                System.out.println("Error in file processing after close it (Error al procesar el fichero después de cerrarlo): " + ex);
            }
        }
        return arrayDatos;

    }
    public ArrayList<String[]> readExcelFileXls(File excelFile) throws IOException {
        ArrayList<String[]> arrayDatos = new ArrayList<>();
        InputStream excelStream = null;
        try {
            excelStream = new FileInputStream(excelFile);
            // High level representation of a workbook.
            // Representación del más alto nivel de la hoja excel.
            HSSFWorkbook hssfWorkbook = new HSSFWorkbook(excelStream);

            // We chose the sheet is passed as parameter.
            // Elegimos la hoja que se pasa por parámetro.
            HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
            // An object that allows us to read a row of the excel sheet, and extract from it the cell contents.
            // Objeto que nos permite leer un fila de la hoja excel, y de aquí extraer el contenido de las celdas.
            HSSFRow hssfRow = hssfSheet.getRow(hssfSheet.getTopRow());
            String [] datos = new String[hssfRow.getLastCellNum()];
            // For this example we'll loop through the rows getting the data we want
            // Para este ejemplo vamos a recorrer las filas obteniendo los datos que queremos
            for (Row row: hssfSheet) {
                for (Cell cell : row) {
                /*
                    We have those cell types (tenemos estos tipos de celda):
                        CELL_TYPE_BLANK, CELL_TYPE_NUMERIC, CELL_TYPE_BLANK, CELL_TYPE_FORMULA, CELL_TYPE_BOOLEAN, CELL_TYPE_ERROR
                */
                    datos[cell.getColumnIndex()] =
                            (cell.getCellTypeEnum() == CellType.STRING)?cell.getStringCellValue():
                                    (cell.getCellTypeEnum() == CellType.NUMERIC)?"" + cell.getNumericCellValue():
                                            (cell.getCellTypeEnum() == CellType.BOOLEAN)?"" + cell.getBooleanCellValue():
                                                    (cell.getCellTypeEnum() == CellType.BLANK)?"BLANK":
                                                            (cell.getCellTypeEnum() == CellType.FORMULA)?"FORMULA":
                                                                    (cell.getCellTypeEnum() == CellType.ERROR)?"ERROR":"";
                }
                arrayDatos.add(datos);
                datos = new String[hssfRow.getLastCellNum()];
            }
        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println("The file not exists (No se encontró el fichero): " + fileNotFoundException);
        } catch (IOException ex) {
            System.out.println("Error in file procesing (Error al procesar el fichero): " + ex);
        } finally {
            try {
                excelStream.close();
            } catch (IOException ex) {
                System.out.println("Error in file processing after close it (Error al procesar el fichero después de cerrarlo): " + ex);
            }
        }
        return arrayDatos;

    }
}
