package mindware.com.view;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import mindware.com.model.Parameter;
import mindware.com.service.ParameterService;
import mindware.com.utilities.ReportUtility;
import mindware.com.utilities.Util;

import java.util.ArrayList;
import java.util.List;

public class ReportForm extends CustomComponent implements View {
    private GridLayout gridMainLayout;
    private Panel panelPaymentsBank;
    private DateField dfDateInitPaymentsBank;
    private DateField dfDateEndPaymentsBank;
    private Button btnPrinPaymentsBank;
    private Panel panelLatePayment;
    private DateField dfDatefLatePayment;
    private Button btnPrintLatePayment;
    private Panel panelRegisteredStudents;
    private Button btnPrintRegisteredStudents;
    private ComboBox<Parameter> cmbCourseLevel;
    private Panel panelPaymentsCashList;
    private DateField dfDateInitCashList;
    private DateField dfDateEndCashList;
    private Button btnPrintCashList;
    private Panel panelStudentByTypefee;
    private Button btnPrintStudentTypeFee;



    public ReportForm(){
        setCompositionRoot(buildGridMainLayout());
        postBuild();
    }


    private void postBuild(){
        fillComboCourseLevel();
        btnPrinPaymentsBank.addClickListener(clickEvent -> {
            String[] parameter = new String[2];
            parameter[0] = new Util().localDateToString(dfDateInitPaymentsBank.getValue());
            parameter[1] = new Util().localDateToString(dfDateEndPaymentsBank.getValue());

            new ReportUtility().printReport(parameter,"rptpayments_bank.prpt","paymentBank");
        });

        btnPrintLatePayment.addClickListener(clickEvent -> {
            String[] parameter = new String[1];
            parameter[0] = new Util().localDateToString(dfDatefLatePayment.getValue());
            new ReportUtility().printReport(parameter,"rptlatepayment.prpt","latepayment");
        });

        btnPrintRegisteredStudents.addClickListener(clickEvent -> {
            String[] parameter = new String[1];
            parameter[0] = cmbCourseLevel.getValue().getValueParameter();
            new ReportUtility().printReport(parameter,"rptregistered_students.prpt","registered_students");
        });

        btnPrintCashList.addClickListener(clickEvent -> {
            String[] parameter = new String[2];
            parameter[0] = new Util().localDateToString(dfDateInitCashList.getValue());
            parameter[1] = new Util().localDateToString(dfDateEndCashList.getValue());

            new ReportUtility().printReport(parameter,"rptpayments_cash_list.prpt","payment_cash_list");
        });

        btnPrintStudentTypeFee.addClickListener(clickEvent -> {
            String[] parameter = new String[1];
            parameter[0] = "print";
            new ReportUtility().printReport(parameter,"rptstudents_typefee.prpt","students_typefee");
        });
    }

    private void fillComboCourseLevel(){
        ParameterService parameterService = new ParameterService();
        List<Parameter> parameterList = new ArrayList<>();
        cmbCourseLevel.setItems(parameterService.findParameterByType("NIVEL_CURSOS"));
        cmbCourseLevel.setItemCaptionGenerator(Parameter::getDescriptionParameter);

    }

    private GridLayout buildGridMainLayout(){
        gridMainLayout = new GridLayout();
        gridMainLayout.setRows(10);
        gridMainLayout.setColumns(2);
        gridMainLayout.setSpacing(true);
        gridMainLayout.setWidth("100%");

        gridMainLayout.addComponent(buildPanelPaymentsBank(),0,0,1,0);
        gridMainLayout.addComponent(buildPanelLatePayment(),0,1,1,1);
        gridMainLayout.addComponent(buildPanelRegisteredStudents(),0,2,1,2);
        gridMainLayout.addComponent(buildPanelPaymentCashList(),0,3,1,3);
        gridMainLayout.addComponent(buildPanelStudentTypefee(),0,4,1,4);
        return gridMainLayout;
    }

    private Panel buildPanelStudentTypefee(){
        panelStudentByTypefee = new Panel("Lista estudiantes por Tipo de Cuota");
        panelStudentByTypefee.setStyleName(ValoTheme.PANEL_WELL);
        panelStudentByTypefee.setWidth("100%");

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setSpacing(true);
        horizontalLayout.setWidth("30%");

        btnPrintStudentTypeFee = new Button("Imprimir");
        btnPrintStudentTypeFee.setStyleName(ValoTheme.BUTTON_PRIMARY);
        btnPrintStudentTypeFee.setIcon(VaadinIcons.PRINT);
        horizontalLayout.addComponent(btnPrintStudentTypeFee);
        horizontalLayout.setComponentAlignment(btnPrintStudentTypeFee,Alignment.BOTTOM_LEFT);

        panelStudentByTypefee.setContent(horizontalLayout);
        return panelStudentByTypefee;
    }

    private Panel buildPanelPaymentCashList(){
        panelPaymentsCashList = new Panel("Pagos por caja");
        panelPaymentsCashList.setStyleName(ValoTheme.PANEL_WELL);
        panelPaymentsCashList.setWidth("100%");

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setSpacing(true);
        horizontalLayout.setWidth("60%");

        dfDateInitCashList = new DateField("Fecha inicial");
        dfDateInitCashList.setStyleName(ValoTheme.DATEFIELD_TINY);
        dfDateInitCashList.setDateFormat("dd-MM-yyyy");
        horizontalLayout.addComponent(dfDateInitCashList);

        dfDateEndCashList = new DateField("Fecha final");
        dfDateEndCashList.setStyleName(ValoTheme.DATEFIELD_TINY);
        dfDateEndCashList.setDateFormat("dd-MM-yyyy");
        horizontalLayout.addComponent(dfDateEndCashList);

        btnPrintCashList = new Button("Imprimir");
        btnPrintCashList.setStyleName(ValoTheme.BUTTON_PRIMARY);
        btnPrintCashList.setIcon(VaadinIcons.PRINT);
        horizontalLayout.addComponent(btnPrintCashList);
        horizontalLayout.setComponentAlignment(btnPrintCashList,Alignment.BOTTOM_LEFT);

        panelPaymentsCashList.setContent(horizontalLayout);
        return panelPaymentsCashList;
    }

    private Panel buildPanelRegisteredStudents(){
        panelRegisteredStudents = new Panel("Alumnos registrados");
        panelRegisteredStudents.setStyleName(ValoTheme.PANEL_WELL);
        panelRegisteredStudents.setWidth("100%");

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setSpacing(true);
        horizontalLayout.setWidth("30%");

        cmbCourseLevel = new ComboBox<>("Nivel de curso");
        cmbCourseLevel.setStyleName(ValoTheme.COMBOBOX_TINY);
        cmbCourseLevel.setEmptySelectionAllowed(false);
        horizontalLayout.addComponent(cmbCourseLevel);

        btnPrintRegisteredStudents = new Button("Imprimir");
        btnPrintRegisteredStudents.setStyleName(ValoTheme.BUTTON_PRIMARY);
        btnPrintRegisteredStudents.setIcon(VaadinIcons.PRINT);
        horizontalLayout.addComponent(btnPrintRegisteredStudents);
        horizontalLayout.setComponentAlignment(btnPrintRegisteredStudents,Alignment.BOTTOM_LEFT);

        panelRegisteredStudents.setContent(horizontalLayout);
        return panelRegisteredStudents;
    }

    private Panel buildPanelLatePayment(){
        panelLatePayment = new Panel("Listado de mora");
        panelLatePayment.setStyleName(ValoTheme.PANEL_WELL);
        panelLatePayment.setWidth("100%");

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setSpacing(true);
        horizontalLayout.setWidth("40%");

        dfDatefLatePayment = new DateField("Fecha corte");
        dfDatefLatePayment.setStyleName(ValoTheme.DATEFIELD_TINY);
        dfDatefLatePayment.setDateFormat("dd-MM-yyyy");
        horizontalLayout.addComponent(dfDatefLatePayment);

        btnPrintLatePayment = new Button("Imprimir");
        btnPrintLatePayment.setStyleName(ValoTheme.BUTTON_PRIMARY);
        btnPrintLatePayment.setIcon(VaadinIcons.PRINT);
        horizontalLayout.addComponent(btnPrintLatePayment);
        horizontalLayout.setComponentAlignment(btnPrintLatePayment,Alignment.BOTTOM_LEFT);

        panelLatePayment.setContent(horizontalLayout);

        return panelLatePayment;
    }

    private Panel buildPanelPaymentsBank(){
        panelPaymentsBank = new Panel("Pagos por banco");
        panelPaymentsBank.setStyleName(ValoTheme.PANEL_WELL);
        panelPaymentsBank.setWidth("100%");

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setSpacing(true);
        horizontalLayout.setWidth("60%");

        dfDateInitPaymentsBank = new DateField("Fecha inicial");
        dfDateInitPaymentsBank.setStyleName(ValoTheme.DATEFIELD_TINY);
        dfDateInitPaymentsBank.setDateFormat("dd-MM-yyyy");
        horizontalLayout.addComponent(dfDateInitPaymentsBank);

        dfDateEndPaymentsBank = new DateField("Fecha final");
        dfDateEndPaymentsBank.setStyleName(ValoTheme.DATEFIELD_TINY);
        dfDateEndPaymentsBank.setDateFormat("dd-MM-yyyy");
        horizontalLayout.addComponent(dfDateEndPaymentsBank);

        btnPrinPaymentsBank = new Button("Imprimir");
        btnPrinPaymentsBank.setStyleName(ValoTheme.BUTTON_PRIMARY);
        btnPrinPaymentsBank.setIcon(VaadinIcons.PRINT);
        horizontalLayout.addComponent(btnPrinPaymentsBank);
        horizontalLayout.setComponentAlignment(btnPrinPaymentsBank,Alignment.BOTTOM_LEFT);

        panelPaymentsBank.setContent(horizontalLayout);

        return panelPaymentsBank;
    }
}
