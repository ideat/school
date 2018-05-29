package mindware.com.utilities;

import mindware.com.model.Payments;
import mindware.com.service.PaymentsService;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ProcessExcel {

    public boolean verifyPaymentPeriod(File reader){
        boolean period = true;
        try{
            FileInputStream stream = new FileInputStream(reader);
            XSSFWorkbook wb = new XSSFWorkbook(stream);
            XSSFSheet sheet = wb.getSheetAt(0);
            XSSFRow row;
            XSSFCell cell;
            String periodFile = null;
            Iterator rows = sheet.rowIterator();
            PaymentsService paymentsService = new PaymentsService();

            int countRow = 0;
            int rowPeriod=0;

            while (rows.hasNext()) {
                rowPeriod++;
                countRow++;
                int cellIndicator = 0;

                row = (XSSFRow) rows.next();
                Iterator cells = row.cellIterator();

                while (cells.hasNext()) {
                    cell = (XSSFCell) cells.next();

                    if (rowPeriod == 2) {
                        if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING)
                            if (cell.getStringCellValue().contains("Del")) {
                                periodFile = cell.getStringCellValue().substring(4, 14) + " - "
                                        + cell.getStringCellValue().substring(18, 28);
                                int nroPayments = paymentsService.countPaymentsPeriod(periodFile);
                                if (nroPayments>0) period = false;

                            }
                    }
                }
            }


        }catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return period;
    }

    public List<Payments> paymentsFromExcelFile(File reader){
        List<Payments> paymentsList = new ArrayList<>();
        try{
            FileInputStream stream = new FileInputStream(reader);
            XSSFWorkbook wb = new XSSFWorkbook(stream);
            XSSFSheet sheet = wb.getSheetAt(0);
            XSSFRow row;
            XSSFCell cell;

            Iterator rows = sheet.rowIterator();
            int countRow = 0;
            int rowPeriod=0;

            String dateTransaction=null;
            String periodFile = null;
            while (rows.hasNext()){
                rowPeriod++;
                countRow++;
                int cellIndicator= 0;

                row = (XSSFRow) rows.next();
                Iterator cells = row.cellIterator();

                while (cells.hasNext()){
                    cell = (XSSFCell) cells.next();

                    if (rowPeriod==2){
                        if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING)
                            if (cell.getStringCellValue().contains("Del")){
                                periodFile = cell.getStringCellValue().substring(4,14) +" - "
                                + cell.getStringCellValue().substring(18,28);
                            }
                    }


                    if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING)
                    if (cell.getStringCellValue().contains("FECHA:")){
                        dateTransaction = cell.getStringCellValue().substring(7,17);
                        countRow = 0;

                    }

                    if (countRow>=2 && dateTransaction!=null){
                        cellIndicator++;
                        if (cellIndicator==2 && cell.getCellType()==XSSFCell.CELL_TYPE_NUMERIC) { //has number value number transaction
                            Payments payments = new Payments();
                            cell = (XSSFCell) cells.next(); //move next cell Nro fact
                            payments.setInvoiceNumber(cell.toString());
                            cell = (XSSFCell) cells.next(); //move next cell Usr
                            cell = (XSSFCell) cells.next(); //move next cell Curso
                            payments.setCourseLevel(cell.toString());
                            cell = (XSSFCell) cells.next(); //move next cell Concepto
                            payments.setPaymentConcept(cell.toString());
                            cell = (XSSFCell) cells.next(); //move next cell Codigo alumno
                            payments.setStudentId( (int) cell.getNumericCellValue());
                            cell = (XSSFCell) cells.next(); //move next cell Nombre estudiante
                            payments.setFullNameStudent(cell.toString());
                            cell = (XSSFCell) cells.next(); //move next cell Nro cuota
                            cell = (XSSFCell) cells.next(); //move next cell Monto
                            payments.setPaymentMount(cell.getNumericCellValue());
                            payments.setPaymentDate(new Util().stringToDate(dateTransaction,"dd/MM/yyyy"));
                            payments.setPaymentType("Deposito bancario");
                            payments.setPaymentPeriod(periodFile);
                            paymentsList.add(payments);
                            cells.next();
                      } else {
                            if (cellIndicator > 2)
                                dateTransaction = null;
                        }

                    }


                }
            }

        }catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return paymentsList;

    }
}
