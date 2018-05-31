package mindware.com.view;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import mindware.com.model.Parameter;
import mindware.com.model.Payments;
import mindware.com.model.Student;
import mindware.com.service.ParameterService;
import mindware.com.service.PaymentsService;
import mindware.com.service.StudentService;
import mindware.com.utilities.ReportUtility;
import mindware.com.utilities.Util;
import org.vaadin.ui.NumberField;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PaymentCashForm extends CustomComponent implements View {
    private GridLayout gridMainLayout;
    private TextField txtPaymentId;
    private TextField txtStudentId;
    private TextField txtFullNameStudent;
    private ComboBox cmbConceptPayment;
    private TextField txtDescription;
    private TextField txtInvoiceNumber;
    private NumberField nfPaymentMount;
    private DateField dfPaymentDate;
    private TextField txtCourseLevel;
    private Button btnSavePayment;
    private Button btnNewPayment;
    private Button btnAddPayment;
    private Panel panelGridPayments;
    private Grid<Payments> gridPayments;
    private List<Payments> paymentsList;

    public PaymentCashForm(){
        setCompositionRoot(buildGridMainLayout());
        paymentsList = new ArrayList<>();
        fillConcept();
        postBuild();
    }

    private void postBuild(){
        btnAddPayment.addClickListener(clickEvent -> {
            if (validateData()) {
                paymentsList.add(prepareData());
                fillGridPayments();
                clearFields2();
            }else{
                Notification.show("Error","Complete los datos",
                        Notification.Type.ERROR_MESSAGE);
            }
        });

        btnSavePayment.addClickListener(clickEvent -> {

                PaymentsService paymentsService = new PaymentsService();
                if (paymentsList.size()>0) {
                    paymentsService.insertPayments(paymentsList);
                    String[] parameter = new String[3];
                    parameter[0] = txtStudentId.getValue();
                    parameter[1] = txtInvoiceNumber.getValue();
                    parameter[2] = new Util().localDateToString(dfPaymentDate.getValue());


                    new ReportUtility().printReport(parameter,"rptpayments_cash.prpt","payment");
                    gridPayments.removeAllColumns();
                    paymentsList.removeAll(paymentsList);
                    Notification.show("Pagos","Se guardaron los pagos",
                            Notification.Type.HUMANIZED_MESSAGE);
                    clearFields();

                }else{
                    Notification.show("Error","No existen pagos a guardar",
                            Notification.Type.ERROR_MESSAGE);
                }

         });

        txtStudentId.addBlurListener(blurEvent -> {
           if (!txtStudentId.isEmpty())
           if (!searchStudent(Integer.parseInt(txtStudentId.getValue()))){
               Notification.show("Error","Estudiante no encontrado",Notification.Type.ERROR_MESSAGE);
               txtStudentId.focus();
           }
        });

        btnNewPayment.addClickListener(clickEvent -> clearFields());
    }

    private boolean validateData(){
        if (txtStudentId.isEmpty()) return false;
        if (txtFullNameStudent.isEmpty()) return false;
        if (cmbConceptPayment.isEmpty()) return false;
        if (txtDescription.isEmpty()) return false;
        if (nfPaymentMount.isEmpty()) return false;
        if (txtInvoiceNumber.isEmpty()) return false;
        return true;
    }

    private void clearFields2(){
        cmbConceptPayment.clear();
        txtDescription.clear();
        nfPaymentMount.clear();

    }

    private void clearFields(){
        txtPaymentId.clear();
        txtStudentId.clear();
        txtFullNameStudent.clear();
        dfPaymentDate.clear();
        cmbConceptPayment.clear();
        txtCourseLevel.clear();
        txtDescription.clear();
        nfPaymentMount.clear();
        txtInvoiceNumber.clear();
        txtStudentId.focus();
    }

    private boolean searchStudent(int studentId){
        StudentService studentService = new StudentService();
        Student student = studentService.findStudentById(studentId);
        if (student==null)
            return false;
        else {
            txtFullNameStudent.setValue(student.getLastNameStudent() + " " + student.getNameStudent());
            txtCourseLevel.setValue(student.getCourseLevel());
            dfPaymentDate.setValue(new Util().dateToLocalDate(new Date()));
            return true;
        }
    }

    private Payments prepareData(){
        Payments payments = new Payments();
        payments.setStudentId(Integer.parseInt(txtStudentId.getValue()));
        payments.setFullNameStudent(txtFullNameStudent.getValue());
        payments.setDescription(txtDescription.getValue());
        payments.setPaymentMount(Double.parseDouble(nfPaymentMount.getValue()));
        payments.setInvoiceNumber(txtInvoiceNumber.getValue());
        payments.setPaymentDate(new Util().localDateToDate(dfPaymentDate.getValue()));
        payments.setPaymentConcept(cmbConceptPayment.getValue().toString());
        payments.setCourseLevel(txtCourseLevel.getValue());
        payments.setPaymentPeriod(new Util().localDateToString(dfPaymentDate.getValue()));
        payments.setPaymentType("Caja efectivo");

        return payments;
    }

    private void fillConcept(){
        ParameterService parameterService = new ParameterService();
        List<Parameter> parameterList = parameterService.findParameterByType("CONCEPTO_PAGOS");
        List<String> conceptList = new ArrayList<>();
        for(Parameter parameter : parameterList){
            conceptList.add(parameter.getValueParameter());
        }
        cmbConceptPayment.setItems(conceptList);
    }

    private void fillGridPayments(){
        if (gridPayments.getHeaderRowCount()>1)
            gridPayments.removeHeaderRow(1);

        gridPayments.removeAllColumns();
        gridPayments.setItems(paymentsList);
        gridPayments.addColumn(Payments::getStudentId).setCaption("Codigo").setId("studentId");
        gridPayments.addColumn(Payments::getPaymentConcept).setCaption("Concepto").setId("paymentConcept");
        gridPayments.addColumn(Payments::getDescription).setCaption("Descripcion").setId("description");
        gridPayments.addColumn(Payments::getPaymentMount).setCaption("Monto").setId("paymentMount");
        gridPayments.addComponentColumn(payments -> {
            Button button = new Button();
            button.setIcon(VaadinIcons.TRASH);
            button.setStyleName(ValoTheme.BUTTON_PRIMARY);
            button.addClickListener(click ->{
                paymentsList.remove(payments);
                gridPayments.getDataProvider().refreshAll();
            });

            return button;

        }).setWidth(60);

    }

    private GridLayout buildGridMainLayout(){
        gridMainLayout = new GridLayout();
        gridMainLayout.setColumns(5);
        gridMainLayout.setRows(5);
        gridMainLayout.setSizeFull();
        gridMainLayout.setSpacing(true);

        txtPaymentId = new TextField("ID");
        txtPaymentId.setStyleName(ValoTheme.TEXTFIELD_TINY);
        txtPaymentId.setEnabled(false);
        gridMainLayout.addComponent(txtPaymentId,0,0);

        txtStudentId = new TextField("Codigo estudiante");
        txtStudentId.setStyleName(ValoTheme.TEXTFIELD_TINY);
        gridMainLayout.addComponent(txtStudentId,1,0);

        txtFullNameStudent = new TextField("Nombre completo");
        txtFullNameStudent.setStyleName(ValoTheme.TEXTFIELD_TINY);
        txtFullNameStudent.setEnabled(false);
        txtFullNameStudent.setWidth("100%");
        gridMainLayout.addComponent(txtFullNameStudent,2,0,3,0);

        dfPaymentDate = new DateField("Fecha pago");
        dfPaymentDate.setStyleName(ValoTheme.DATEFIELD_TINY);
        dfPaymentDate.setEnabled(false);
        dfPaymentDate.setDateFormat("dd-MM-yyyy");
        gridMainLayout.addComponent(dfPaymentDate,0,1);

        txtCourseLevel = new TextField("Nivel");
        txtCourseLevel.setStyleName(ValoTheme.TEXTFIELD_TINY);
        txtCourseLevel.setEnabled(false);
        gridMainLayout.addComponent(txtCourseLevel,1,1);

        cmbConceptPayment = new ComboBox("Concepto");
        cmbConceptPayment.setStyleName(ValoTheme.COMBOBOX_TINY);
        cmbConceptPayment.setEmptySelectionAllowed(false);
        gridMainLayout.addComponent(cmbConceptPayment,2,1);

        txtDescription = new TextField("Descripcion");
        txtDescription.setStyleName(ValoTheme.TEXTFIELD_TINY);
        txtDescription.setWidth("220px");
        gridMainLayout.addComponent(txtDescription,3,1);
//        gridMainLayout.setComponentAlignment(txtDescription,Alignment.BOTTOM_LEFT);

        nfPaymentMount = new NumberField("Monto");
        nfPaymentMount.setStyleName(ValoTheme.TEXTFIELD_TINY);
        nfPaymentMount.setDecimalSeparator('.');
        nfPaymentMount.setMinValue(1);
        nfPaymentMount.setMaxValue(1000000);
        nfPaymentMount.setNegativeAllowed(false);
        nfPaymentMount.setErrorText("Monto no valido");
        gridMainLayout.addComponent(nfPaymentMount,0,2);

        txtInvoiceNumber = new TextField("Nro factura");
        txtInvoiceNumber.setStyleName(ValoTheme.TEXTFIELD_TINY);
        gridMainLayout.addComponent(txtInvoiceNumber,1,2);

        btnSavePayment = new Button("Guardar");
        btnSavePayment.setStyleName(ValoTheme.BUTTON_PRIMARY);
        btnSavePayment.setIcon(VaadinIcons.DATABASE);
        btnSavePayment.setWidth("110px");
        gridMainLayout.addComponent(btnSavePayment,4,0);
        gridMainLayout.setComponentAlignment(btnSavePayment,Alignment.BOTTOM_LEFT);

        btnNewPayment = new Button("Nuevo");
        btnNewPayment.setStyleName(ValoTheme.BUTTON_FRIENDLY);
        btnNewPayment.setIcon(VaadinIcons.MONEY_WITHDRAW);
        btnNewPayment.setWidth("110px");
        gridMainLayout.addComponent(btnNewPayment,4,1);
        gridMainLayout.setComponentAlignment(btnNewPayment,Alignment.BOTTOM_LEFT);

        btnAddPayment = new Button("Agregar");
        btnAddPayment.setStyleName(ValoTheme.BUTTON_PRIMARY);
        btnAddPayment.setIcon(VaadinIcons.ADD_DOCK);
        gridMainLayout.addComponent(btnAddPayment,4,2);
        gridMainLayout.setComponentAlignment(btnAddPayment,Alignment.BOTTOM_LEFT);

        gridMainLayout.addComponent(buildPanelGridPayments(),0,3,4,4);

        return gridMainLayout;
    }

    private Panel buildPanelGridPayments(){
        panelGridPayments = new Panel();
        panelGridPayments.setStyleName(ValoTheme.PANEL_WELL);
        panelGridPayments.setWidth("100%");

        gridPayments = new Grid<>(Payments.class);
        gridPayments.setStyleName(ValoTheme.TABLE_COMPACT);
        gridPayments.setWidth("100%");
        panelGridPayments.setContent(gridPayments);

        return panelGridPayments;

    }
}
