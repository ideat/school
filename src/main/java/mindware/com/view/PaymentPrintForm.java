package mindware.com.view;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.DateRenderer;
import com.vaadin.ui.renderers.NumberRenderer;
import com.vaadin.ui.themes.ValoTheme;
import mindware.com.model.Payments;
import mindware.com.service.PaymentsService;
import mindware.com.utilities.ReportUtility;
import mindware.com.utilities.Util;
import org.vaadin.gridutil.cell.GridCellFilter;

import java.text.SimpleDateFormat;
import java.util.List;

public class PaymentPrintForm extends CustomComponent implements View {
    private GridLayout gridMainLayout;
    private Panel panelGridPayment;
    private Grid<Payments> gridPayment;
    private Button btnPrintIvoice;
    private GridCellFilter<Payments> filterPayments;
    private DateField dfDateInit;
    private DateField dfDateEnd;
    private Button btnSearch;

    public PaymentPrintForm(){
        setCompositionRoot(buildGridMaintLayout());
//        gridPayment.removeAllColumns();
        PaymentsService paymentsService = new PaymentsService();
        fillGridPayment(paymentsService.findPaymentsGroupedByTypeDateStudent());
    }

    private void fillGridPayment(List<Payments> paymentsList){
        if (gridPayment.getHeaderRowCount()>1)
            gridPayment.removeHeaderRow(1);
        gridPayment.removeAllColumns();
        gridPayment.setItems(paymentsList);
        gridPayment.addColumn(Payments::getStudentId).setCaption("Codigo").setId("studentId").setWidth(100);
        gridPayment.addColumn(Payments::getFullNameStudent).setCaption("Nombre completo").setId("fullNameStudent");
        gridPayment.addColumn(Payments::getPaymentType).setCaption("Tipo pago").setId("paymentType").setWidth(200);
        gridPayment.addColumn(Payments::getPaymentDate).setCaption("Fecha").setId("paymentDate").setWidth(150)
                .setRenderer(new DateRenderer(("%1$td-%1$tm-%1$tY")));
        gridPayment.addColumn(Payments::getInvoiceNumber).setCaption("Factura").setId("invoiceNumber").setWidth(130);
        gridPayment.addColumn(Payments::getPaymentMount).setCaption("Total").setId("paymentMount")
                .setRenderer(new NumberRenderer());

        gridPayment.addComponentColumn(payments -> {
            Button button = new Button();
            button.setIcon(VaadinIcons.PRINT);
            button.setStyleName(ValoTheme.BUTTON_PRIMARY);
            button.addClickListener(click ->{

                String[] parameter = new String[3];
                parameter[0] = payments.getStudentId().toString();
                parameter[1] = payments.getInvoiceNumber();
                parameter[2] = new Util().dateToString(payments.getPaymentDate());

                new ReportUtility().printReport(parameter,"rptpayments_cash.prpt","payment");
            });

            return button;

        }).setWidth(90);

        filterGridPayments(gridPayment);
    }

    private void filterGridPayments(final Grid grid){
        this.filterPayments = new GridCellFilter(grid);
        this.filterPayments.setNumberFilter("studentId",Integer.class);
        this.filterPayments.setTextFilter("fullNameStudent",true,false);
        this.filterPayments.setTextFilter("paymentType",true,false);
        this.filterPayments.setDateFilter("paymentDate",new SimpleDateFormat("dd-MM-yyyy"),false);
        this.filterPayments.setTextFilter("invoiceNumber",true,false);
    }

    private GridLayout buildGridMaintLayout(){
        gridMainLayout = new GridLayout();
        gridMainLayout.setColumns(2);
        gridMainLayout.setRows(2);
        gridMainLayout.setSpacing(true);
        gridMainLayout.setWidth("100%");

        panelGridPayment = new Panel();
        panelGridPayment.setStyleName(ValoTheme.PANEL_WELL);
        panelGridPayment.setWidth("100%");

        gridPayment = new Grid<>(Payments.class);
        gridPayment.setStyleName(ValoTheme.TABLE_COMPACT);
        gridPayment.setWidth("100%");
        panelGridPayment.setContent(gridPayment);

        gridMainLayout.addComponent(panelGridPayment,0,0,1,1);
        return gridMainLayout;
    }



}
