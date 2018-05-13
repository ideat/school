package mindware.com.view;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.DateRenderer;
import com.vaadin.ui.themes.ValoTheme;
import mindware.com.model.ClassPeriod;
import mindware.com.model.Parents;
import mindware.com.model.Student;
import mindware.com.model.TypeFee;
import mindware.com.service.ClassPeriodService;
import mindware.com.service.StudentService;
import mindware.com.service.TypeFeeService;
import mindware.com.utilities.Util;
import mindware.com.utilities.UtilJson;
import org.vaadin.gridutil.cell.GridCellFilter;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class StudentForm extends CustomComponent implements View {

    private GridLayout gridMainLayoutStudent;
    private GridLayout gridMainLayoutParents;
    private Grid<Student> gridStudent;
    private ComboBox<ClassPeriod> cmbClassPeriod;
    private ComboBox<TypeFee> cmbTypeFee;
    private TextField txtStudentId;
    private TextField txtNameStudent;
    private TextField txtLastNameStudent;
    private TextField txtPhoneNumber;
    private TextField txtCellNumber;
    private TextField txtAddress;
    private DateField dfRegistrationDate;
    private DateField dfBornDate;
    private DateField dfRetirementDate;
    private TextField txtClassRoom;
    private Grid<Parents> gridParents;
    private TextField txtParentId;
    private TextField txtNameParent;
    private TextField txtLastNameParent;
    private TextField txtPhoneNumberParent;
    private TextField txtCellNumberParent;
    private TextField txtPhoneOffice;
    private TextField txtAddressParent;
    private DateField dfBornDateParent;
    private ComboBox cmbTypeRelationShip;
    private TabSheet mainTabSheet;
    private Button btnSaveStudent;
    private Button btnNewStudent;
    private Button btnAddParent;
    private Button btnRemoveParent;
    private StudentService studentService;
    private List<Parents> parentsList;
    private GridCellFilter<Student> filterStudent;

    public StudentForm(){
        Panel panelMain = new Panel();
        studentService = new StudentService();
        parentsList = new ArrayList<>();
        panelMain.setStyleName(ValoTheme.PANEL_WELL);
        panelMain.setContent(buildMainTabSheet());

        setCompositionRoot(panelMain);
        ClassPeriodService classPeriodService = new ClassPeriodService();
        fillStudentsPerido(classPeriodService.findClassPeriodByState("ACTIVO").get(0).getYear());
        headerGridParent();
        fillCombos();
        postBuild();
    }


    private void postBuild(){

        btnSaveStudent.addClickListener(clickEvent -> {
           try{
               saveStudent();
               clearFieldsStudent();
               clearFieldsParent();
           }catch (Exception e){
               Notification.show("Error",
                       " Al registrar los datos " + e.getMessage(),
                       Notification.Type.ERROR_MESSAGE);
           }
        });

        btnAddParent.addClickListener(clickEvent -> {
            if (validateDataParent()) {
                addParents(prepareDataParents());
                clearFieldsParent();
            }else{
                Notification.show("Error",
                        " Complete los datos de los padres/tutores ",
                        Notification.Type.ERROR_MESSAGE);
            }
        });

        gridStudent.addItemClickListener(itemClick -> {
            fillStudentSelected(itemClick.getItem());
            fillParentList(itemClick.getItem().getParents());
        });

        gridParents.addItemClickListener(itemClick -> {
            fillParentSelected(itemClick.getItem());
        });

        btnRemoveParent.addClickListener(clickEvent -> {
            parentsList = new UtilJson().deleteParentFromList(parentsList,Integer.parseInt(txtParentId.getValue()));
            List<Parents> auxParentList= new ArrayList<>();
            for(Parents parents:parentsList){
                auxParentList.add(parents);
            }

            parentsList.clear();
            for(Parents parents:auxParentList){
                addParents(parents);
            }
            headerGridParent();
            gridParents.setItems(parentsList);
            clearFieldsParent();

        });
    }

    private void clearFieldsStudent(){
        cmbClassPeriod.clear();
        cmbTypeFee.clear();
        txtStudentId.clear();
        txtNameStudent.clear();
        txtLastNameStudent.clear();
        txtPhoneNumber.clear();
        txtCellNumber.clear();
        txtAddress.clear();
        dfRegistrationDate.clear();
        dfBornDate.clear();
        txtClassRoom.clear();
    }

    private void clearFieldsParent(){
        txtParentId.clear();
        txtNameParent.clear();
        txtLastNameParent.clear();
        txtPhoneNumberParent.clear();
        txtCellNumberParent.clear();
        txtPhoneOffice.clear();
        txtAddressParent.clear();
        dfBornDateParent.clear();
        cmbTypeRelationShip.clear();
    }

    private void fillParentSelected(Parents parents){
        Util util = new Util();
        txtParentId.setValue(parents.getParentId().toString());
        txtNameParent.setValue(parents.getNameParent());
        txtLastNameParent.setValue(parents.getLastNameParent());
        txtPhoneNumberParent.setValue(parents.getPhoneNumberParent());
        txtCellNumberParent.setValue(parents.getCellNumberParent());
        txtPhoneOffice.setValue(parents.getPhoneOffice());
        txtAddressParent.setValue(parents.getAddressParent());
        dfBornDateParent.setValue(util.dateToLocalDate(util.stringToDate(parents.getBornDateParent(),"dd-MM-yyyy")));
        cmbTypeRelationShip.setValue(parents.getTypeRelationShip());
    }

    private void fillStudentSelected(Student student){
        txtStudentId.setValue(student.getStudentId().toString());
        txtNameStudent.setValue(student.getNameStudent());
        txtLastNameStudent.setValue(student.getLastNameStudent());
        txtPhoneNumber.setValue(student.getPhoneNumber());
        txtCellNumber.setValue(student.getCellNumber());
        txtAddress.setValue(student.getAddress());
        dfRegistrationDate.setValue(new Util().dateToLocalDate(student.getRegistrationDate()));
        dfBornDate.setValue(new Util().dateToLocalDate(student.getBornDate()));
        txtClassRoom.setValue(student.getClassRoom());
        cmbClassPeriod.setValue(student.getClassPeriod());
        cmbTypeFee.setValue(student.getTypeFee());

    }

    private void fillParentList(String parentsJson){
        Gson gson = new Gson();
        Type type = new TypeToken<List<Parents>>(){}.getType();
        parentsList = gson.fromJson(parentsJson, type);
        headerGridParent();
        gridParents.setItems(parentsList);

    }

    private void fillStudentsPerido(String year){
        List<Student> studentList = studentService.findStudentByPeriod(year);
        gridStudent.setItems(studentList);
        headerGridStudent();
        filterGridStudent(gridStudent);
    }

    private void filterGridStudent(final Grid grid){
        this.filterStudent = new GridCellFilter(grid);
        this.filterStudent.setDateFilter("bornDate",  new SimpleDateFormat("dd-MM-yyyy"),true);
        this.filterStudent.setDateFilter("registrationDate",  new SimpleDateFormat("dd-MM-yyyy"),true);
        this.filterStudent.setTextFilter("nameStudent", true,false);
        this.filterStudent.setTextFilter("lastNameStudent", true,false);
        this.filterStudent.setTextFilter("classRoom", true,false);
    }

    private void headerGridStudent(){
        gridStudent.removeAllColumns();
        gridStudent.addColumn(Student::getStudentId).setCaption("ID").setId("studentId");
        gridStudent.addColumn(Student::getLastNameStudent).setCaption("Apellidos").setId("lastNameStudent");
        gridStudent.addColumn(Student::getNameStudent).setCaption("Nombres").setId("nameStudent");
        gridStudent.addColumn(Student::getPhoneNumber).setCaption("Telf. domicilio").setId("phoneNumber");
        gridStudent.addColumn(Student::getCellNumber).setCaption("Celular").setId("cellNumber");
        gridStudent.addColumn(Student::getAddress).setCaption("Dirección").setId("address");
        gridStudent.addColumn(Student::getRegistrationDate).setCaption("Fecha inscripción").setId("registrationDate").setRenderer(new DateRenderer(("%1$td-%1$tm-%1$tY")));
        gridStudent.addColumn(Student::getBornDate).setCaption("Fecha nacimiento").setId("bornDate").setRenderer(new DateRenderer(("%1$td-%1$tm-%1$tY")));
        gridStudent.addColumn(Student::getClassPeriodId).setCaption("Gestión").setId("classPeriodId");
        gridStudent.addColumn(Student::getTypeFeeId).setCaption("Tipo cuota").setId("typeFeeId");
        gridStudent.addColumn(Student::getClassRoom).setCaption("Aula").setId("classRoom");

    }

    private void headerGridParent(){
        gridParents.removeAllColumns();
        gridParents.addColumn(Parents::getParentId).setCaption("ID");
        gridParents.addColumn(Parents::getLastNameParent).setCaption("Apellidos");
        gridParents.addColumn(Parents::getNameParent).setCaption("Nombres");
        gridParents.addColumn(Parents::getAddressParent).setCaption("Dirección");
        gridParents.addColumn(Parents::getCellNumberParent).setCaption("Celular");
        gridParents.addColumn(Parents::getPhoneNumberParent).setCaption("Telf. domicilio");
        gridParents.addColumn(Parents::getPhoneOffice).setCaption("Telf. oficina");
        gridParents.addColumn(Parents::getBornDateParent).setCaption("Fecha Nac.");
        gridParents.addColumn(Parents::getTypeRelationShip).setCaption("Tipo relación");
    }

    private void addParents(Parents parents){
        parents.setParentId(parentsList.size()+1);
        parentsList.add(parents);
        headerGridParent();
        gridParents.setItems(parentsList);
    }

    private Parents prepareDataParents(){
        Parents parents = new Parents();
        parents.setNameParent(txtNameParent.getValue());
        parents.setLastNameParent(txtLastNameParent.getValue());
        parents.setPhoneNumberParent(txtPhoneNumberParent.getValue());
        parents.setCellNumberParent(txtCellNumberParent.getValue());
        parents.setPhoneOffice(txtPhoneOffice.getValue());
        parents.setAddressParent(txtAddressParent.getValue());
        parents.setBornDateParent(new Util().localDateToString(dfBornDateParent.getValue()));
        parents.setTypeRelationShip(cmbTypeRelationShip.getValue().toString());
        return parents;
    }
    private void saveStudent(){
        if (validateDataStudent()){
            if (txtStudentId.isEmpty()) {
                Student student = prepareDataStudent();
                if (dataParentsToJsonString().equals("[]")){
                    Notification.show("ERROR",
                            "Registre los Padres o Tutores del estudiante" ,
                            Notification.Type.WARNING_MESSAGE);
                    mainTabSheet.setSelectedTab(1);
                }else {

                    student.setParents(dataParentsToJsonString());
                    studentService.insertStudent(student);
                    Notification.show("Estudiante",
                            " Se registraron los datos ",
                            Notification.Type.HUMANIZED_MESSAGE);
                }
            }else {

                ObjectMapper mapper = new ObjectMapper();
                String jsonParents = null;
                try {
                    jsonParents = mapper.writeValueAsString(parentsList);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                Student student = prepareDataStudent();
//                student.setParents(dataParentsToJsonString());
                student.setParents(jsonParents);
                student.setStudentId(Integer.parseInt(txtStudentId.getValue()));
                studentService.updateStudent(student);
                Notification.show("Estudiante",
                        " Datos actualizados ",
                        Notification.Type.HUMANIZED_MESSAGE);
            }

        }else{
            Notification.show("ERROR",
                    "Complete los datos del studiante" ,
                    Notification.Type.WARNING_MESSAGE);
        }
    }

    private Student prepareDataStudent(){
        Student student = new Student();
        student.setNameStudent(txtNameStudent.getValue());
        student.setLastNameStudent(txtLastNameStudent.getValue());
        student.setPhoneNumber(txtPhoneNumber.getValue());
        student.setCellNumber(txtCellNumber.getValue());
        student.setAddress(txtAddress.getValue());
        student.setRegistrationDate(new Util().localDateToDate(dfRegistrationDate.getValue()));
        student.setBornDate(new Util().localDateToDate(dfBornDate.getValue()));
        student.setClassRoom(txtClassRoom.getValue());
        student.setClassPeriodId(cmbClassPeriod.getValue().getClassPeriodId());
        student.setTypeFeeId(cmbTypeFee.getValue().getTypeFeeId());

        return student;
    }

    private String dataParentsToJsonString(){
        return new UtilJson().listParentsToJsonFormat(parentsList);
    }

    private boolean validateDataStudent(){
        if (cmbClassPeriod.isEmpty()) return false;
        if (cmbTypeFee.isEmpty()) return false;
        if (txtLastNameStudent.isEmpty()) return false;
        if (txtPhoneNumber.isEmpty()) txtPhoneNumber.setValue("-");
        if (txtCellNumber.isEmpty()) txtCellNumber.setValue("-");
        if (txtAddress.isEmpty()) return false;
        if (dfRegistrationDate.isEmpty()) return false;
        if (dfBornDate.isEmpty()) return false;
        if (txtClassRoom.isEmpty()) return false;

        return true;
    }

    private boolean validateDataParent(){
        if (txtNameParent.isEmpty()) return false;
        if (txtLastNameParent.isEmpty()) return false;
        if (txtPhoneNumberParent.isEmpty()) txtPhoneNumberParent.setValue("-");
        if (txtCellNumberParent.isEmpty()) txtCellNumberParent.setValue("-");
        if (txtPhoneOffice.isEmpty()) txtPhoneOffice.setValue("-");
        if (txtAddressParent.isEmpty()) return false;
        if (dfBornDateParent.isEmpty()) return false;
        if (cmbTypeRelationShip.isEmpty()) return false;

        return true;
    }

    private void fillCombos(){
        ClassPeriodService classPeriodService = new ClassPeriodService();
        List<ClassPeriod> classPeriodList = classPeriodService.findClassPeriodByState("ACTIVO");
        cmbClassPeriod.setItems(classPeriodList);
        cmbClassPeriod.setItemCaptionGenerator(ClassPeriod::getYear);
        String year = classPeriodList.get(0).getYear();

        cmbTypeFee.setItems(new TypeFeeService().findTypeFeeByClassPeriodYear(year));
        cmbTypeFee.setItemCaptionGenerator(TypeFee::getNameFee);
    }

    private TabSheet buildMainTabSheet(){
        mainTabSheet = new TabSheet();
        mainTabSheet.setStyleName(ValoTheme.TABSHEET_FRAMED);

        mainTabSheet.addTab(buildGridMainLayoutStudent(),"Estudiantes");
        mainTabSheet.addTab(buildGridMainLayoutParents(),"Padres/Apoderados");

        return mainTabSheet;
    }

    private GridLayout buildGridMainLayoutStudent(){
        gridMainLayoutStudent = new GridLayout();
        gridMainLayoutStudent.setColumns(7);
        gridMainLayoutStudent.setRows(5);
        gridMainLayoutStudent.setSpacing(true);
        gridMainLayoutStudent.setWidth("100%");

        txtStudentId = new TextField("ID");
        txtStudentId.setStyleName(ValoTheme.TEXTFIELD_TINY);
        txtStudentId.setEnabled(false);
        gridMainLayoutStudent.addComponent(txtStudentId,0,0);

        txtNameStudent = new TextField("Nombres");
        txtNameStudent.setStyleName(ValoTheme.TEXTFIELD_TINY);
        gridMainLayoutStudent.addComponent(txtNameStudent,0,1);

        txtLastNameStudent = new TextField("Apellidos Paterno/Materno");
        txtLastNameStudent.setStyleName(ValoTheme.TEXTFIELD_TINY);
        gridMainLayoutStudent.addComponent(txtLastNameStudent,1,1);

        txtAddress = new TextField("Dirección domicilio");
        txtAddress.setStyleName(ValoTheme.TEXTFIELD_TINY);
        gridMainLayoutStudent.addComponent(txtAddress,2,1);

        txtPhoneNumber = new TextField("Telf. domicilio");
        txtPhoneNumber.setStyleName(ValoTheme.TEXTFIELD_TINY);
        gridMainLayoutStudent.addComponent(txtPhoneNumber,3,1);

        txtCellNumber = new TextField("Nro. celular");
        txtCellNumber.setStyleName(ValoTheme.TEXTFIELD_TINY);
        gridMainLayoutStudent.addComponent(txtCellNumber,4,1);

        dfBornDate = new DateField("Fecha nacimiento (dd-mm-yyyy)");
        dfBornDate.setStyleName(ValoTheme.DATEFIELD_TINY);
        dfBornDate.setDateFormat("dd-MM-yyyy");
        gridMainLayoutStudent.addComponent(dfBornDate,0,2);

        dfRegistrationDate = new DateField("Fecha inscripción (dd-mm-yyyy)");
        dfRegistrationDate.setStyleName(ValoTheme.DATEFIELD_TINY);
        gridMainLayoutStudent.addComponent(dfRegistrationDate,1,2);

        txtClassRoom = new TextField("Aula");
        txtClassRoom.setStyleName(ValoTheme.TEXTFIELD_TINY);
        gridMainLayoutStudent.addComponent(txtClassRoom,2,2);

        cmbClassPeriod = new ComboBox<>("Gestión");
        cmbClassPeriod.setStyleName(ValoTheme.COMBOBOX_TINY);
        cmbClassPeriod.setEmptySelectionAllowed(false);
        gridMainLayoutStudent.addComponent(cmbClassPeriod,3,2);

        cmbTypeFee = new ComboBox<>("Tipo cuota");
        cmbTypeFee.setStyleName(ValoTheme.COMBOBOX_TINY);
        cmbTypeFee.setEmptySelectionAllowed(false);
        gridMainLayoutStudent.addComponent(cmbTypeFee,4,2);

        btnSaveStudent = new Button("Guardar");
        btnSaveStudent.setStyleName(ValoTheme.BUTTON_PRIMARY);
        btnSaveStudent.setIcon(VaadinIcons.DATABASE);
        btnSaveStudent.setWidth("130px");
        gridMainLayoutStudent.addComponent(btnSaveStudent,5,0);
        gridMainLayoutStudent.setComponentAlignment(btnSaveStudent,Alignment.BOTTOM_LEFT);

        btnNewStudent = new Button("Nuevo");
        btnNewStudent.setStyleName(ValoTheme.BUTTON_FRIENDLY);
        btnNewStudent.setIcon(VaadinIcons.PLUS_CIRCLE);
        btnNewStudent.setWidth("130px");
        gridMainLayoutStudent.addComponent(btnNewStudent,5,1);
        gridMainLayoutStudent.setComponentAlignment(btnNewStudent,Alignment.BOTTOM_LEFT);

        gridMainLayoutStudent.addComponent(buildPanelGridStudent(),0,3,5,4);

        return gridMainLayoutStudent;
    }

    private Panel buildPanelGridStudent(){
        Panel panelGridStudent = new Panel();
        panelGridStudent.setStyleName(ValoTheme.PANEL_WELL);
//        panelGridStudent.setSizeFull();
        gridStudent = new Grid<>(Student.class);
        gridStudent.setStyleName(ValoTheme.TABLE_COMPACT);
        gridStudent.setWidth("100%");
        panelGridStudent.setContent(gridStudent);
        return panelGridStudent;
    }

    private GridLayout buildGridMainLayoutParents(){
        gridMainLayoutParents = new GridLayout();
        gridMainLayoutParents.setColumns(7);
        gridMainLayoutParents.setRows(5);
//        gridMainLayoutParents.setSizeFull();
        gridMainLayoutParents.setWidth("100%");
        gridMainLayoutParents.setSpacing(true);

        txtParentId = new TextField("ID");
        txtParentId.setStyleName(ValoTheme.TEXTFIELD_TINY);
        txtParentId.setEnabled(false);
        gridMainLayoutParents.addComponent(txtParentId,0,0);

        txtNameParent = new TextField("Nombres");
        txtNameParent.setStyleName(ValoTheme.TEXTFIELD_TINY);
        gridMainLayoutParents.addComponent(txtNameParent,1,0);

        txtLastNameParent = new TextField("Apellidos");
        txtLastNameParent.setStyleName(ValoTheme.TEXTFIELD_TINY);
        gridMainLayoutParents.addComponent(txtLastNameParent,2,0);

        txtPhoneNumberParent = new TextField("Telf. domicilio");
        txtPhoneNumberParent.setStyleName(ValoTheme.TEXTFIELD_TINY);
        gridMainLayoutParents.addComponent(txtPhoneNumberParent,3,0);

        txtCellNumberParent = new TextField("Celular");
        txtCellNumberParent.setStyleName(ValoTheme.TEXTFIELD_TINY);
        gridMainLayoutParents.addComponent(txtCellNumberParent,0,1);

        txtPhoneOffice = new TextField("Telf. oficina");
        txtPhoneOffice.setStyleName(ValoTheme.TEXTFIELD_TINY);
        gridMainLayoutParents.addComponent(txtPhoneOffice,1,1);

        txtAddressParent = new TextField("Dirección domicilio");
        txtAddressParent.setStyleName(ValoTheme.TEXTFIELD_TINY);
        gridMainLayoutParents.addComponent(txtAddressParent,2,1);

        dfBornDateParent = new DateField("Fecha nacimiento (dd-mm-yyyy)");
        dfBornDateParent.setStyleName(ValoTheme.DATEFIELD_TINY);
        dfBornDateParent.setDateFormat("dd-MM-yyyy");
        gridMainLayoutParents.addComponent(dfBornDateParent,3,1);

        cmbTypeRelationShip = new ComboBox("Tipo relación");
        cmbTypeRelationShip.setStyleName(ValoTheme.COMBOBOX_TINY);
        cmbTypeRelationShip.setEmptySelectionAllowed(false);
        cmbTypeRelationShip.setItems("PADRE","MADRE","ABUELO/A","TIO/A","APODERADO");
        gridMainLayoutParents.addComponent(cmbTypeRelationShip,4,1);

        btnAddParent = new Button("Agregar");
        btnAddParent.setStyleName(ValoTheme.BUTTON_PRIMARY);
        btnAddParent.setIcon(VaadinIcons.PLUS_CIRCLE);
        btnAddParent.setWidth("100px");
        gridMainLayoutParents.addComponent(btnAddParent,5,0);
        gridMainLayoutParents.setComponentAlignment(btnAddParent,Alignment.BOTTOM_LEFT);

        btnRemoveParent = new Button("Borrar");
        btnRemoveParent.setStyleName(ValoTheme.BUTTON_DANGER);
        btnRemoveParent.setIcon(VaadinIcons.TRASH);
        btnRemoveParent.setWidth("100px");
        gridMainLayoutParents.addComponent(btnRemoveParent,5,1);
        gridMainLayoutParents.setComponentAlignment(btnRemoveParent,Alignment.BOTTOM_LEFT);

       gridMainLayoutParents.addComponent(buildPanelGridParents(),0,2,5,3);

        return gridMainLayoutParents;
    }

    private Panel buildPanelGridParents(){
        Panel panelGridParents = new Panel();
        panelGridParents.setStyleName(ValoTheme.PANEL_WELL);
        panelGridParents.setSizeFull();

        gridParents = new Grid<>();
        gridParents.setStyleName(ValoTheme.TABLE_SMALL);
        gridParents.setWidth("100%");
        panelGridParents.setContent(gridParents);

        return panelGridParents;

    }

}
