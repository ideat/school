package mindware.com.view;

import com.vaadin.data.Binder;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.DateRenderer;
import com.vaadin.ui.themes.ValoTheme;
import mindware.com.model.Payments;
import mindware.com.service.PaymentsService;
import mindware.com.utilities.Util;
import org.vaadin.gridutil.cell.GridCellFilter;

import java.util.List;

public class PaymentEditForm extends CustomComponent implements View {
    private GridLayout gridMainLayout;
    private Grid<Payments> gridPayments;
    private Panel panelGridPayments;
    private Button btnSearch;
    private DateField dfDateInit;
    private DateField dfDateEnd;
    private GridCellFilter<Payments> filterPayments;
    private PaymentsService paymentsService;
    private Util util;

    public PaymentEditForm(){
        setCompositionRoot(buildGridMainLayout());
        paymentsService = new PaymentsService();
        util = new Util();
        postBuild();

    }

    private void postBuild(){
        gridPayments.getEditor().addSaveListener(editorSaveEvent -> {
            try {
                paymentsService.updatePayments(editorSaveEvent.getBean());
                fillGridPayments(paymentsService.findPaymentsByDates(util.localDateToDate(dfDateInit.getValue()),
                        util.localDateToDate(dfDateEnd.getValue())));
                Notification.show("Actualizado",
                        "Descripcion",
                        Notification.Type.HUMANIZED_MESSAGE);
            } catch (Exception e) {
                Notification.show("Error",
                        " Actualizando descripcion",
                        Notification.Type.ERROR_MESSAGE);
            }
        }
        );

        btnSearch.addClickListener(clickEvent -> {
            fillGridPayments(paymentsService.findPaymentsByDates(util.localDateToDate(dfDateInit.getValue()),
                    util.localDateToDate(dfDateEnd.getValue())));
        });

    }

    private void fillGridPayments(List<Payments> paymentsList){
        if (gridPayments.getHeaderRowCount()>1)
            gridPayments.removeHeaderRow(1);
        gridPayments.setItems(paymentsList);
        gridPayments.removeAllColumns();
        gridPayments.addColumn(Payments::getPaymentId).setCaption("paymentid").setId("paymentId").setHidden(true);
        gridPayments.addColumn(Payments::getStudentId).setCaption("ID").setId("studentId").setWidth(80);
        gridPayments.addColumn(Payments::getFullNameStudent).setCaption("Nombre").setId("fullNameStudent");
        gridPayments.addColumn(Payments::getCourseLevel).setCaption("Nivel").setId("courseLevel").setWidth(90);
        gridPayments.addColumn(Payments::getInvoiceNumber).setCaption("Factura").setId("invoiceNumber").setWidth(90);
        gridPayments.addColumn(Payments::getPaymentDate).setCaption("Fecha").setId("paymentDate").setWidth(110)
                .setRenderer(new DateRenderer(("%1$td-%1$tm-%1$tY")));
        gridPayments.addColumn(Payments::getPaymentConcept).setCaption("Concepto").setId("paymentConcept").setWidth(100);
        gridPayments.addColumn(Payments::getPaymentMount).setCaption("Monto").setId("paymentMount").setWidth(90);
        Binder<Payments> binder = gridPayments.getEditor().getBinder();

        gridPayments.addColumn(Payments::getDescription)
                .setEditorBinding(binder
                    .forField(new TextField())
                        .bind(Payments::getDescription,Payments::setDescription)
                ).setCaption("Descripcion").setId("description");
        filterGridPayments(gridPayments);
    }

    private void filterGridPayments(final Grid grid){
        this.filterPayments = new GridCellFilter(grid);
        this.filterPayments.setNumberFilter("studentId",Integer.class);
        this.filterPayments.setTextFilter("fullNameStudent",true,false);
        this.filterPayments.setTextFilter("courseLevel",true,false);
        this.filterPayments.setTextFilter("paymentConcept",true,false);
    }


    private GridLayout buildGridMainLayout(){
        gridMainLayout = new GridLayout();
        gridMainLayout.setColumns(5);
        gridMainLayout.setRows(5);
        gridMainLayout.setSpacing(true);
        gridMainLayout.setSizeFull();

        dfDateInit = new DateField("Fecha inicial");
        dfDateInit.setStyleName(ValoTheme.DATEFIELD_TINY);
        dfDateInit.setDateFormat("dd-MM-yyyy");
        gridMainLayout.addComponent(dfDateInit,0,0);

        dfDateEnd = new DateField("Fecha final");
        dfDateEnd.setStyleName(ValoTheme.DATEFIELD_TINY);
        dfDateEnd.setDateFormat("dd-MM-yyyy");
        gridMainLayout.addComponent(dfDateEnd,1,0);

        btnSearch = new Button("Buscar");
        btnSearch.setStyleName(ValoTheme.BUTTON_PRIMARY);
        btnSearch.setIcon(VaadinIcons.SEARCH);
        gridMainLayout.addComponent(btnSearch,2,0);
        gridMainLayout.setComponentAlignment(btnSearch,Alignment.BOTTOM_LEFT);

        gridMainLayout.addComponent(buildPanelGridPayments(),0,1,4,3);

        return gridMainLayout;
    }

    private Panel buildPanelGridPayments(){
        panelGridPayments = new Panel();
        panelGridPayments.setStyleName(ValoTheme.PANEL_WELL);
        panelGridPayments.setWidth("100%");

        gridPayments = new Grid<>(Payments.class);
        gridPayments.setStyleName(ValoTheme.TABLE_COMPACT);
        gridPayments.setWidth("100%");
        gridPayments.getEditor().setEnabled(true);
        gridPayments.getEditor().setSaveCaption("Guardar");
        gridPayments.getEditor().setCancelCaption("Cancelar");

        panelGridPayments.setContent(gridPayments);

        return panelGridPayments;
    }

}
