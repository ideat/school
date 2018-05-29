package mindware.com.view;

import com.vaadin.data.Binder;
import com.vaadin.data.converter.StringToDoubleConverter;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.DateRenderer;
import com.vaadin.ui.renderers.NumberRenderer;
import com.vaadin.ui.themes.ValoTheme;
import de.steinwedel.messagebox.MessageBox;
import mindware.com.model.PaymentPlan;
import mindware.com.model.Student;
import mindware.com.service.ClassPeriodService;
import mindware.com.service.PaymentPlanService;
import mindware.com.service.StudentService;
import mindware.com.utilities.PaymentPlanUtils;
import org.vaadin.gridutil.cell.GridCellFilter;
import org.vaadin.ui.NumberField;

import java.util.List;

public class PaymentPlanForm extends CustomComponent implements View {
    private GridLayout gridMainLayout;
    private Panel panelGridStudent;
    private Grid<Student> gridStudent;
    private Panel panelGridPaymentPlan;
    private Grid<PaymentPlan> gridPaymentPlan;
    private Button btnCreatePaymentPlan;
    private GridCellFilter<Student> filterStudent;
    private PaymentPlanService paymentPlanService;
    private DateField dfInitDate;
    private DateField dfEndDate;
    private PaymentPlanUtils paymentPlanUtils;
    private Student student;

    public PaymentPlanForm(){
        setCompositionRoot(buildGridMainLayout());
        paymentPlanService = new PaymentPlanService();
        paymentPlanUtils = new PaymentPlanUtils();
        postBuild();
    }

    private void postBuild(){
        fillGridStudents();

        gridStudent.addItemClickListener(itemClick -> {
            student = itemClick.getItem();
            List<PaymentPlan> paymentPlanList = paymentPlanService.findPaymentPlanByStudentId(student.getStudentId());
            fillGridPaymentPlan(paymentPlanList);
        });

        btnCreatePaymentPlan.addClickListener(clickEvent -> {
            if (student!=null) {
                if (paymentPlanService.findPaymentPlanByStudentId(student.getStudentId()).size()<=0) {
                    List<PaymentPlan> paymentPlanList = paymentPlanUtils.createPaymentPlan(student.getStudentId(),
                            dfInitDate.getValue(), dfEndDate.getValue());
                    fillGridPaymentPlan(paymentPlanList);
                    paymentPlanService.insertPaymentPlanList(paymentPlanList);
                }else {
                    MessageBox.createQuestion()
                            .withCaption("Advertencia")
                            .withMessage("Ya tiene un plan de pagos, desea borrarlo? ")
                            .withYesButton(()->{
                                paymentPlanService.deletePaymentPlan(student.getStudentId());
                                List<PaymentPlan> paymentPlanList = paymentPlanUtils.createPaymentPlan(student.getStudentId(),
                                        dfInitDate.getValue(), dfEndDate.getValue());
                                fillGridPaymentPlan(paymentPlanList);
                                paymentPlanService.insertPaymentPlanList(paymentPlanList);

                            })
                            .withNoButton()
                            .open();
                }
            }else{
                Notification.show("Error",
                        "Seleccione un estudiante",
                        Notification.Type.ERROR_MESSAGE);
            }
        });

        gridPaymentPlan.getEditor().addSaveListener(editorSaveEvent -> {
            try {
                paymentPlanService.updatePaymentPlan(editorSaveEvent.getBean());
                Notification.show("Plan de pagos",
                        "Cuota actualizada",
                        Notification.Type.HUMANIZED_MESSAGE);
            }catch (Exception e){
                Notification.show("Error",
                        "Al actualizar la cuota " + e.getMessage(),
                        Notification.Type.ERROR_MESSAGE);
            }
        });
    }



    private void fillGridPaymentPlan(List<PaymentPlan> paymentPlanList){
        NumberField value = new NumberField();

        gridPaymentPlan.setItems(paymentPlanList);
        gridPaymentPlan.removeAllColumns();
        gridPaymentPlan.addColumn(PaymentPlan::getPaymentPlanNumber).setCaption("Nro");
        gridPaymentPlan.addColumn(PaymentPlan::getPaymentPlanDate).setCaption("Fecha").setRenderer(new DateRenderer(("%1$td-%1$tm-%1$tY")));
//        gridPaymentPlan.addColumn(PaymentPlan::getPaymentPlanAmount).setCaption("Cuota").
//                setId("paymentPlanAmount").setEditable(true);

        Binder<PaymentPlan> binder = gridPaymentPlan.getEditor().getBinder();
        gridPaymentPlan.addColumn(PaymentPlan::getPaymentPlanAmount,new NumberRenderer())
                .setEditorBinding(binder
                        .forField(new TextField())
                        .withConverter(new StringToDoubleConverter("No es valido"))
                        .bind(PaymentPlan::getPaymentPlanAmount, PaymentPlan::setPaymentPlanAmount)
                ).setCaption("Cuota");


    }

    private void fillGridStudents(){
        StudentService studentService = new StudentService();
        ClassPeriodService classPeriodService = new ClassPeriodService();
        String year = classPeriodService.findClassPeriodByState("ACTIVO").get(0).getYear();
        gridStudent.setItems(studentService.findStudentByPeriod(year));
        gridStudent.removeAllColumns();
        gridStudent.addColumn(Student::getStudentId).setCaption("ID").setId("studentId").setWidth(120);
        gridStudent.addColumn(Student::getNameStudent).setCaption("Nombres").setId("nameStudent");
        gridStudent.addColumn(Student::getLastNameStudent).setCaption("Apellidos").setId("lastNameStudent");
        gridStudent.addColumn(Student::getCourseLevel).setCaption("Nivel").setId("courseLevel");

        filterGridStudent(gridStudent);
    }

    private void filterGridStudent(final Grid grid){
        this.filterStudent = new GridCellFilter(grid);
        this.filterStudent.setNumberFilter("studentId", Integer.class);
        this.filterStudent.setTextFilter("nameStudent", true, false);
        this.filterStudent.setTextFilter("lastNameStudent", true, false);
        this.filterStudent.setTextFilter("courseLevel", true, false);
    }

    private GridLayout buildGridMainLayout(){
        gridMainLayout = new GridLayout();
        gridMainLayout.setColumns(6);
        gridMainLayout.setRows(15);
        gridMainLayout.setSpacing(true);
        gridMainLayout.setSizeFull();

        gridMainLayout.addComponent(buildPanelGridStudent(),0,0,4,5);
        gridMainLayout.addComponent(buildPanelGridPaymentPlan(),0,6,4,12);

        dfInitDate = new DateField("Fecha inicial");
        dfInitDate.setStyleName(ValoTheme.DATEFIELD_TINY);
        dfInitDate.setDateFormat("dd-MM-yyyy");
        gridMainLayout.addComponent(dfInitDate,5,0);
        gridMainLayout.setComponentAlignment(dfInitDate,Alignment.TOP_LEFT);

        dfEndDate = new DateField("Fecha final");
        dfEndDate.setStyleName(ValoTheme.DATEFIELD_TINY);
        dfEndDate.setDateFormat("dd-MM-yyyy");
        gridMainLayout.addComponent(dfEndDate,5,1);
        gridMainLayout.setComponentAlignment(dfEndDate,Alignment.TOP_LEFT);

        btnCreatePaymentPlan = new Button("Crear plan de pagos");
        btnCreatePaymentPlan.setStyleName(ValoTheme.BUTTON_PRIMARY);
        btnCreatePaymentPlan.setIcon(VaadinIcons.CALENDAR_BRIEFCASE);
        btnCreatePaymentPlan.setWidth("200px");
        gridMainLayout.addComponent(btnCreatePaymentPlan,5,2);
        gridMainLayout.setComponentAlignment(btnCreatePaymentPlan,Alignment.TOP_LEFT);

        return gridMainLayout;
    }

    private Panel buildPanelGridPaymentPlan(){
        panelGridPaymentPlan = new Panel();
        panelGridPaymentPlan.setStyleName(ValoTheme.PANEL_WELL);
        panelGridPaymentPlan.setHeight("250px");

        gridPaymentPlan = new Grid<>(PaymentPlan.class);
        gridPaymentPlan.setStyleName(ValoTheme.TABLE_SMALL);
        gridPaymentPlan.setWidth("100%");
        gridPaymentPlan.setHeight("250px");
        gridPaymentPlan.getEditor().setEnabled(true);
        gridPaymentPlan.getEditor().setSaveCaption("Guardar");
        gridPaymentPlan.getEditor().setCancelCaption("Cancelar");
        panelGridPaymentPlan.setContent(gridPaymentPlan);

        return panelGridPaymentPlan;
    }

    private Panel buildPanelGridStudent(){
        panelGridStudent = new Panel();
        panelGridStudent.setStyleName(ValoTheme.PANEL_WELL);
        panelGridStudent.setWidth("100%");
        panelGridStudent.setHeight("200px");

        gridStudent = new Grid<>(Student.class);
        gridStudent.setStyleName(ValoTheme.TABLE_SMALL);
        gridStudent.setWidth("100%");
        gridStudent.setHeight("200px");
        panelGridStudent.setContent(gridStudent);

        return panelGridStudent;
    }
}
