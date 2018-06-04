package mindware.com.utilities;

import com.vaadin.ui.UI;
import de.steinwedel.messagebox.MessageBox;
import pl.pdfviewer.PdfViewer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.util.HashMap;

public class ReportUtility {
    public void printReport(String[] param, String nameReport, String nameOutPut) {
        try{
            URL url = getClass().getResource("../reports/");
            String separador = System.getProperty("file.separator");
            byte[] byteFile = Files.readAllBytes(new File(url.getPath()+ separador +nameReport).toPath());
            GenerateReport generarReporte = new GenerateReport();
            HashMap<String,Object> parameters = new HashMap();
            URL urlLogo = getClass().getResource("../image");
            byte[] byteFileLogo = Files.readAllBytes(new File(urlLogo.getPath()+ separador + "logo.png").toPath());

            if(nameReport.equals("rptpayments_cash.prpt")){
                parameters.put("studentId", Integer.parseInt(param[0]));
                parameters.put("invoiceNumber",param[1]);
                parameters.put("paymentDate", new Util().stringToDate(param[2],"dd-MM-yyyy"));
                parameters.put("path_logo", urlLogo.getPath() + separador + "logo.png");
            }else if (nameReport.equals("rptpayments_bank.prpt")){
                parameters.put("dateInit", new Util().stringToDate(param[0],"dd-MM-yyyy"));
                parameters.put("dateEnd", new Util().stringToDate(param[1],"dd-MM-yyyy"));
                parameters.put("path_logo", urlLogo.getPath() + separador + "logo.png");
            }else if (nameReport.equals("rptlatepayment.prpt")){
                parameters.put("date_cutoff", new Util().stringToDate(param[0],"dd-MM-yyyy"));
                parameters.put("path_logo", urlLogo.getPath() + separador + "logo.png");
            }else if (nameReport.equals("rptregistered_students.prpt")){
                parameters.put("path_logo", urlLogo.getPath() + separador + "logo.png");
            }else if (nameReport.equals("rptpayments_cash_list.prpt")){
                parameters.put("dateInit", new Util().stringToDate(param[0],"dd-MM-yyyy"));
                parameters.put("dateEnd", new Util().stringToDate(param[1],"dd-MM-yyyy"));
                parameters.put("path_logo", urlLogo.getPath() + separador + "logo.png");
            }else if (nameReport.equals("rptstudents_typefee.prpt")){
                parameters.put("path_logo", urlLogo.getPath() + separador + "logo.png");
            }

            byte[] report = generarReporte.generateReport(byteFile,parameters);
            String fileName=System.getProperty("java.io.tmpdir") + separador + nameOutPut+String.valueOf(param[0])+".pdf".replaceAll("%20"," ");
            OutputStream out = new FileOutputStream(fileName.replaceAll("%20"," "));
            out.write(report);
            out.flush();
            out.close();

            previewPdfFile(fileName,"75%","100%");

        }catch (Exception e){
            e.printStackTrace();
            MessageBox.createError()
                    .withCaption("Error")
                    .withMessage("Error: " + e)
                    .open();
        }
    }

    private void previewPdfFile(String fileName, String width, String height) throws FileNotFoundException {
        com.vaadin.ui.Window window = new com.vaadin.ui.Window();
        window.setWidth(width); //50
        window.setHeight(height); //70
        window.center();
        window.setModal(true);

        PdfViewer c = new PdfViewer(new File(fileName));
        c.addPageChangeListener(integer -> {

            c.setPage(integer);
        });
        c.setSizeFull();

        window.setContent(c);
        UI.getCurrent().addWindow(window);

    }

}
