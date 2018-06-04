package mindware.com.utilities;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import mindware.com.model.DetailLatePayment;
import org.apache.poi.hssf.usermodel.HSSFHeader;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ExcelReport {

    private CellStyle cs = null;
    private CellStyle csBold = null;
    private CellStyle csTop = null;
    private CellStyle csRight = null;
    private CellStyle csBottom = null;
    private CellStyle csLeft = null;
    private CellStyle csTopLeft = null;
    private CellStyle csTopRight = null;
    private CellStyle csBottomLeft = null;
    private CellStyle csBottomRight = null;
    private CellStyle csDateFormat = null;


    public String createExcel(List<DetailLatePayment> detailLatePaymentList) {

        try{

            XSSFWorkbook wb = new XSSFWorkbook();
            XSSFSheet sheet = wb.createSheet("Extracto pagos");

            //Setup some styles that we need for the Cells
            setCellStyles(wb);

            //Get current Date and Time
            Date date = new Date(System.currentTimeMillis());
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

            //Set Column Widths
            sheet.setColumnWidth(0, 2300);
            sheet.setColumnWidth(1, 2300);
            sheet.setColumnWidth(2, 11000);
            sheet.setColumnWidth(3, 2100);
            sheet.setColumnWidth(4, 2100);

            //Setup the Page margins - Left, Right, Top and Bottom
            sheet.setMargin(Sheet.LeftMargin, 0.25);
            sheet.setMargin(Sheet.RightMargin, 0.25);
            sheet.setMargin(Sheet.TopMargin, 0.75);
            sheet.setMargin(Sheet.BottomMargin, 0.75);

            //Setup the Header and Footer Margins
            sheet.setMargin(Sheet.HeaderMargin, 0.25);
            sheet.setMargin(Sheet.FooterMargin, 0.25);

            //If you are using HSSFWorkbook() instead of XSSFWorkbook()
            //HSSFPrintSetup ps = (HSSFPrintSetup) sheet.getPrintSetup();
            //ps.setHeaderMargin((double) .25);
            //ps.setFooterMargin((double) .25);

            //Set Header Information
            Header header = sheet.getHeader();
            header.setLeft("*** COPIA ORIGINAL ***");
            header.setCenter(HSSFHeader.font("Arial", "Bold") +
                    HSSFHeader.fontSize((short) 12) + "EXTRACTO PAGOS ");
            header.setRight(df.format(date));

            //Set Footer Information with Page Numbers
//            Footer footer = sheet.getFooter();
//            footer.setRight( "Pagina " + HSSFFooter.page() + " de " +
//                    HSSFFooter.numPages() );


            int rowIndex = 0;
            rowIndex = insertHeaderInfo(sheet, rowIndex,detailLatePaymentList.get(1));
            rowIndex = insertDetailInfo(sheet, rowIndex,detailLatePaymentList);

            sheet.autoSizeColumn(2);
            //Write the Excel file
            FileOutputStream fileOut = null;
            fileOut = new FileOutputStream( this.getClass().getClassLoader().getResource("/template/").
                    getPath().replaceAll("%20"," ")+"extract_"+detailLatePaymentList.get(1).getStudentId().toString()+".xlsx");
            wb.write(fileOut);

            fileOut.close();
            return "extract_"+detailLatePaymentList.get(1).getStudentId().toString()+".xlsx";

        }
        catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }



    private int insertHeaderInfo(XSSFSheet sheet, int index, DetailLatePayment detailLatePayment){

        int rowIndex = index;
        Row row = null;
        Cell c = null;

        rowIndex++;
        row = sheet.createRow(rowIndex);
//        sheet.addMergedRegion(new CellRangeAddress(rowIndex,rowIndex,1,2));
        c = row.createCell(0);
        c.setCellValue("CODIGO:");
//        c.setCellStyle(csLeft);
        c = row.createCell(1);

//        c.setCellStyle(csLeft);
//        c = row.createCell(2);
        c.setCellValue(detailLatePayment.getStudentId());
        c.setCellType(CellType.NUMERIC);


        rowIndex++;
        row = sheet.createRow(rowIndex);
        sheet.addMergedRegion(new CellRangeAddress(rowIndex,rowIndex,1,2));
        c = row.createCell(0);
        c.setCellValue("NOMBRE:");
//        c.setCellStyle(csLeft);
        c = row.createCell(1);
        c.setCellValue(detailLatePayment.getFullNameStudent());
//        c.setCellStyle(csLeft);


        rowIndex++;
        row = sheet.createRow(rowIndex);
        sheet.addMergedRegion(new CellRangeAddress(rowIndex,rowIndex,1,2));
        c = row.createCell(0);
        c.setCellValue("CURSO:");
//        c.setCellStyle(csLeft);
        c = row.createCell(1);
        c.setCellValue(detailLatePayment.getCourseLevel());
//        c.setCellStyle(csRight);

        rowIndex = rowIndex + 3;
        row = sheet.createRow(rowIndex);
        c = row.createCell(0);
        c.setCellValue("FECHA DE PAGO");
        c.setCellStyle(csBold);
        c = row.createCell(1);
        c.setCellValue("NO. FACTURA BAUCHER");
        c.setCellStyle(csBold);
        c = row.createCell(2);
        c.setCellValue("DETALLE");
        c.setCellStyle(csBold);
        c = row.createCell(3);
        c.setCellValue("IMPORTE PAGADO");
        c.setCellStyle(csBold);
        c = row.createCell(4);
        c.setCellValue("SALDO");
        c.setCellStyle(csBold);

        return rowIndex;

    }

    private int insertDetailInfo(XSSFSheet sheet, int index, List<DetailLatePayment> detailLatePaymentList){

        int rowIndex = 0;
        Row row = null;
        Cell c = null;
        int i = 0;
        for(DetailLatePayment detailLatePayment:detailLatePaymentList){
            i++;
            rowIndex = index + i;
            row = sheet.createRow(rowIndex);
            if (i==1) {

                c = row.createCell(2);
                c.setCellValue(detailLatePayment.getDescription());
                c.setCellStyle(cs);
                c = row.createCell(4);
                c.setCellValue(detailLatePayment.getBalance());
                c.setCellStyle(cs);
            }else {
                c = row.createCell(0);
                c.setCellValue(detailLatePayment.getPaymentDate());
                c.setCellStyle(csDateFormat);
                c = row.createCell(1);
                c.setCellValue(detailLatePayment.getInvoiceNumber());
                c.setCellStyle(cs);
                c = row.createCell(2);
                c.setCellValue(detailLatePayment.getDescription());
                c.setCellStyle(cs);
                c = row.createCell(3);
                c.setCellValue(detailLatePayment.getPaymentMount());
                c.setCellStyle(cs);
                c = row.createCell(4);
                c.setCellValue(detailLatePayment.getBalance());
                c.setCellStyle(cs);
            }

        }

        return rowIndex;

    }

    private void setCellStyles(XSSFWorkbook wb) {

        //font size 10
        XSSFFont f = wb.createFont();
        f.setFontHeightInPoints((short) 10);

        //Simple style
        cs = wb.createCellStyle();
        cs.setFont(f);

        //Bold Fond
        XSSFFont bold = wb.createFont();
        bold.setBold(true);
        bold.setFontHeightInPoints((short) 10);

        //Date format
        csDateFormat = wb.createCellStyle();
        CreationHelper createHelper = wb.getCreationHelper();
        csDateFormat.setDataFormat(
                createHelper.createDataFormat().getFormat("dd-MM-yyyy"));
        csDateFormat.setFont(f);
        //Bold style
        csBold = wb.createCellStyle();
        csBold.setBorderBottom(BorderStyle.THIN);
        csBold.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        csBold.setFont(bold);
        csBold.setWrapText(true);

        //Setup style for Top Border Line
        csTop = wb.createCellStyle();
        csTop.setBorderTop(BorderStyle.THIN);
        csTop.setTopBorderColor(IndexedColors.BLACK.getIndex());
        csTop.setFont(f);

        //Setup style for Right Border Line
        csRight = wb.createCellStyle();
        csRight.setBorderRight(BorderStyle.THIN);
        csRight.setRightBorderColor(IndexedColors.BLACK.getIndex());
        csRight.setFont(f);

        //Setup style for Bottom Border Line
        csBottom = wb.createCellStyle();
        csBottom.setBorderBottom(BorderStyle.THIN);
        csBottom.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        csBottom.setFont(f);

        //Setup style for Left Border Line
        csLeft = wb.createCellStyle();
        csLeft.setBorderLeft(BorderStyle.THIN);
        csLeft.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        csLeft.setFont(f);

        //Setup style for Top/Left corner cell Border Lines
        csTopLeft = wb.createCellStyle();
        csTopLeft.setBorderTop(BorderStyle.THIN);
        csTopLeft.setTopBorderColor(IndexedColors.BLACK.getIndex());
        csTopLeft.setBorderLeft(BorderStyle.THIN);
        csTopLeft.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        csTopLeft.setFont(f);

        //Setup style for Top/Right corner cell Border Lines
        csTopRight = wb.createCellStyle();
        csTopRight.setBorderTop(BorderStyle.THIN);
        csTopRight.setTopBorderColor(IndexedColors.BLACK.getIndex());
        csTopRight.setBorderRight(BorderStyle.THIN);
        csTopRight.setRightBorderColor(IndexedColors.BLACK.getIndex());
        csTopRight.setFont(f);

        //Setup style for Bottom/Left corner cell Border Lines
        csBottomLeft = wb.createCellStyle();
        csBottomLeft.setBorderBottom(BorderStyle.THIN);
        csBottomLeft.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        csBottomLeft.setBorderLeft(BorderStyle.THIN);
        csBottomLeft.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        csBottomLeft.setFont(f);

        //Setup style for Bottom/Right corner cell Border Lines
        csBottomRight = wb.createCellStyle();
        csBottomRight.setBorderBottom(BorderStyle.THIN);
        csBottomRight.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        csBottomRight.setBorderRight(BorderStyle.THIN);
        csBottomRight.setRightBorderColor(IndexedColors.BLACK.getIndex());
        csBottomRight.setFont(f);

        //Wrap text

    }

    public void excelToPdf(String fileName) throws IOException, DocumentException {
        //First we read the Excel file in binary format into FileInputStream
        FileInputStream input_document = new FileInputStream( this.getClass().getClassLoader().getResource("/template/").
                getPath()+fileName);
        // Read workbook into HSSFWorkbook
        XSSFWorkbook my_xls_workbook = new XSSFWorkbook(input_document);
        // Read worksheet into HSSFSheet
        XSSFSheet my_worksheet = my_xls_workbook.getSheetAt(0);
        // To iterate over the rows
        Iterator<Row> rowIterator = my_worksheet.iterator();
        //We will create output PDF document objects at this point
        Document iText_xls_2_pdf = new Document();
        PdfWriter.getInstance(iText_xls_2_pdf, new FileOutputStream(this.getClass().getClassLoader().getResource("/template/").
                getPath()+fileName+".pdf"));
        iText_xls_2_pdf.open();
        //we have two columns in the Excel sheet, so we create a PDF table with two columns
        //Note: There are ways to make this dynamic in nature, if you want to.
        PdfPTable my_table = new PdfPTable(5);
        //We will use the object below to dynamically add new data to the table
        PdfPCell table_cell;
        //Loop through rows.
        while(rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            while(cellIterator.hasNext()) {
                Cell cell = cellIterator.next(); //Fetch CELL
                switch(cell.getCellType()) { //Identify CELL type
                    //you need to add more code here based on
                    //your requirement / transformations
                    case Cell.CELL_TYPE_STRING:
                        //Push the data from Excel to PDF Cell
                        table_cell=new PdfPCell(new Phrase(cell.getStringCellValue()));
                        //feel free to move the code below to suit to your needs
                        my_table.addCell(table_cell);
                        break;
                    case Cell.CELL_TYPE_NUMERIC:

                        break;
                }
                //next line
            }

        }
        //Finally add the table to PDF document
        iText_xls_2_pdf.add(my_table);
        iText_xls_2_pdf.close();
        //we created our pdf file..
        input_document.close(); //close xls
    }

}
