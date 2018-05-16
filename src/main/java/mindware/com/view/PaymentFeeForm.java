package mindware.com.view;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.DateRenderer;
import com.vaadin.ui.themes.ValoTheme;
import de.steinwedel.messagebox.MessageBox;
import mindware.com.model.Payments;
import mindware.com.service.PaymentsService;
import mindware.com.utilities.ProcessExcel;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class PaymentFeeForm extends CustomComponent implements View {
    private Upload upload;
    private Panel panelGridPaymentFee;
    private Grid<Payments> gridPaymentFee;
    private File tempFile;
    private Button btnImportData;
    List<Payments> paymentsList;
    private VerticalLayout verticalLayout;

    public PaymentFeeForm(){
        setCompositionRoot(buildVerticalLayout());
        paymentsList = new ArrayList<>();
        postBuild();
    }

    private void postBuild(){
        btnImportData.addClickListener(clickEvent -> {
           insertPayments();
        });
    }

    private void insertPayments(){
        PaymentsService paymentsService = new PaymentsService();
        paymentsService.insertPayments(paymentsList);
    }

    private void fillGridPayments(List<Payments> paymentsList){
        gridPaymentFee.setItems(paymentsList);
        gridPaymentFee.removeAllColumns();
        gridPaymentFee.addColumn(Payments::getPaymentDate).setCaption("Fecha").setId("paymentDate").setRenderer(new DateRenderer(("%1$td-%1$tm-%1$tY")));
        gridPaymentFee.addColumn(Payments::getFullNameStudent).setCaption("Nombre estudiante").setId("fullNameStudent");
        gridPaymentFee.addColumn(Payments::getCourseLevel).setCaption("Nivel").setId("courseLevel");
        gridPaymentFee.addColumn(Payments::getInvoiceNumber).setCaption("Factura").setId("invoiceNumber");
        gridPaymentFee.addColumn(Payments::getPaymentMount).setCaption("Monto").setId("paymentMount");
        gridPaymentFee.addColumn(Payments::getPaymentConcept).setCaption("Concepto").setId("paymentConcept");
        gridPaymentFee.addColumn(Payments::getPaymentType).setCaption("Tipo pago").setId("paymentType");

    }

    private void loadExcel(Upload.FinishedEvent event) throws IOException {

        ProcessExcel processExcel = new ProcessExcel();
        paymentsList = processExcel.paymentsFromExcelFile(tempFile);

        fillGridPayments(paymentsList);

        tempFile.delete();
    }

    private VerticalLayout buildVerticalLayout(){
        verticalLayout = new VerticalLayout();
        verticalLayout.setWidth("100%");
        verticalLayout.setSpacing(true);


        upload = new Upload("Cargar archivo", new Upload.Receiver() {
            @Override
            public OutputStream receiveUpload(String filename, String mimeType) {
                // TODO Auto-generated method stub
                if (!filename.isEmpty()) {
                    try {
                        tempFile = File.createTempFile(filename, "xlsx");
                        tempFile = new File(System.getProperty("java.io.tmpdir"), filename);
                        tempFile.deleteOnExit();
                        return new FileOutputStream(tempFile);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                return null;
            }
        });

        upload.addFinishedListener(new Upload.FinishedListener() {

            @Override
            public void uploadFinished(Upload.FinishedEvent event) {

                        if (tempFile == null) {
                            MessageBox.createError()
                                    .withCaption("Error")
                                    .withMessage("Error: Seleccione un archivo ")
                                    .withAbortButton()
                                    .open();
                        } else {

                            try {
                                loadExcel(event);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }


                        }



            }
        });

        upload.addFailedListener(new Upload.FailedListener() {
            @Override
            public void uploadFailed(Upload.FailedEvent event) {
                MessageBox.createError()
                        .withCaption("Error2")
                        .withMessage("Seleccione todos los parametros ")
                        .withAbortButton()
                        .open();
            }
        });
        upload.setImmediateMode(false);
        upload.setButtonCaption("Cargar archivo");
        verticalLayout.addComponent(upload);

        btnImportData = new Button("Importar datos");
        btnImportData.setStyleName(ValoTheme.BUTTON_PRIMARY);
        btnImportData.setIcon(VaadinIcons.UPLOAD);
        verticalLayout.addComponent(btnImportData);

        gridPaymentFee = new Grid<>();
        gridPaymentFee.setStyleName(ValoTheme.TABLE_SMALL);
        gridPaymentFee.setWidth("100%");

        panelGridPaymentFee = new Panel();
        panelGridPaymentFee.setStyleName(ValoTheme.PANEL_WELL);
        panelGridPaymentFee.setWidth("100%");
        panelGridPaymentFee.setContent(gridPaymentFee);



        verticalLayout.addComponent(panelGridPaymentFee);

        return verticalLayout;

    }

}
