package mindware.com.view;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import mindware.com.model.ClassPeriod;
import mindware.com.model.Parents;
import mindware.com.model.Student;
import mindware.com.model.TypeFee;

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

    public StudentForm(){
        setCompositionRoot(buildMainTabSheet());
    }


    private TabSheet buildMainTabSheet(){
        mainTabSheet = new TabSheet();
        mainTabSheet.setStyleName(ValoTheme.TABSHEET_FRAMED);
        mainTabSheet.setSizeFull();

        mainTabSheet.addTab(buildGridMainLayoutStudent(),"Estudiantes");
        mainTabSheet.addTab(buildGridMainLayoutParents(),"Padres/Opoderados");

        return mainTabSheet;
    }

    private GridLayout buildGridMainLayoutStudent(){
        gridMainLayoutStudent = new GridLayout();
        gridMainLayoutStudent.setColumns(7);
        gridMainLayoutStudent.setRows(5);
        gridMainLayoutStudent.setSpacing(true);
        gridMainLayoutStudent.setSizeFull();

        txtStudentId = new TextField("ID");
        txtStudentId.setStyleName(ValoTheme.TEXTFIELD_TINY);
        txtStudentId.setEnabled(false);
        gridMainLayoutStudent.addComponent(txtStudentId,0,0);

        txtNameStudent = new TextField("Nombres");
        txtNameStudent.setStyleName(ValoTheme.TEXTFIELD_TINY);
        gridMainLayoutStudent.addComponent(txtNameStudent,1,0);

        txtLastNameStudent = new TextField("Apellidos Paterno/Materno");
        txtLastNameStudent.setStyleName(ValoTheme.TEXTFIELD_TINY);
        gridMainLayoutStudent.addComponent(txtLastNameStudent,2,0);

        txtAddress = new TextField("Dirección domicilio");
        txtAddress.setStyleName(ValoTheme.TEXTFIELD_TINY);
        gridMainLayoutStudent.addComponent(txtAddress,3,0);

        txtPhoneNumber = new TextField("Telf. domicilio");
        txtPhoneNumber.setStyleName(ValoTheme.TEXTFIELD_TINY);
        gridMainLayoutStudent.addComponent(txtPhoneNumber,4,0);

        txtCellNumber = new TextField("Nro. celular");
        txtCellNumber.setStyleName(ValoTheme.TEXTFIELD_TINY);
        gridMainLayoutStudent.addComponent(txtCellNumber,0,1);

        dfBornDate = new DateField("Fecha nacimiento");
        dfBornDate.setStyleName(ValoTheme.DATEFIELD_TINY);
        dfBornDate.setDateFormat("dd-MM-yyyy");
        gridMainLayoutStudent.addComponent(dfBornDate,1,1);

        dfRegistrationDate = new DateField("Fecha inscripcion");
        dfRegistrationDate.setStyleName(ValoTheme.DATEFIELD_TINY);
        gridMainLayoutStudent.addComponent(dfRegistrationDate,2,1);

        txtClassRoom = new TextField("Aula");
        txtClassRoom.setStyleName(ValoTheme.TEXTFIELD_TINY);
        gridMainLayoutStudent.addComponent(txtClassRoom,3,1);

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

        gridMainLayoutStudent.addComponent(buildPanelGridStudent(),0,2,5,4);

        return gridMainLayoutStudent;
    }

    private Panel buildPanelGridStudent(){
        Panel panelGridStudent = new Panel();
        panelGridStudent.setStyleName(ValoTheme.PANEL_WELL);
        panelGridStudent.setSizeFull();
        gridStudent = new Grid<>();
        gridStudent.setStyleName(ValoTheme.TABLE_COMPACT);
        gridStudent.setWidth("100%");
        panelGridStudent.setContent(gridStudent);
        return panelGridStudent;
    }

    private GridLayout buildGridMainLayoutParents(){
        gridMainLayoutParents = new GridLayout();
        gridMainLayoutParents.setColumns(7);
        gridMainLayoutParents.setRows(5);
        gridMainLayoutParents.setSizeFull();
        gridMainLayoutParents.setSpacing(true);

        txtNameParent = new TextField("Nombres");
        txtNameParent.setStyleName(ValoTheme.TEXTFIELD_TINY);
        gridMainLayoutParents.addComponent(txtNameParent,0,0);

        txtLastNameParent = new TextField("Apellidos");
        txtLastNameParent.setStyleName(ValoTheme.TEXTFIELD_TINY);
        gridMainLayoutParents.addComponent(txtLastNameParent,1,0);

        txtPhoneNumberParent = new TextField("Telf. domicilio");
        txtPhoneNumberParent.setStyleName(ValoTheme.TEXTFIELD_TINY);
        gridMainLayoutParents.addComponent(txtPhoneNumberParent,2,0);

        txtCellNumberParent = new TextField("Celular");
        txtCellNumberParent.setStyleName(ValoTheme.TEXTFIELD_TINY);
        gridMainLayoutParents.addComponent(txtCellNumberParent,3,0);

        txtPhoneOffice = new TextField("Telf. oficina");
        txtPhoneOffice.setStyleName(ValoTheme.TEXTFIELD_TINY);
        gridMainLayoutParents.addComponent(txtPhoneOffice,0,1);

        txtAddressParent = new TextField("Dirección domicilio");
        txtAddressParent.setStyleName(ValoTheme.TEXTFIELD_TINY);
        gridMainLayoutParents.addComponent(txtAddressParent,1,1);

        dfBornDateParent = new DateField("Fecha nacimiento");
        dfBornDateParent.setStyleName(ValoTheme.DATEFIELD_TINY);
        dfBornDateParent.setDateFormat("dd-MM-yyyy");
        gridMainLayoutParents.addComponent(dfBornDateParent,2,1);

        cmbTypeRelationShip = new ComboBox("Tipo relación");
        cmbTypeRelationShip.setStyleName(ValoTheme.COMBOBOX_TINY);
        cmbTypeRelationShip.setEmptySelectionAllowed(false);
        cmbTypeRelationShip.setItems("PADRE","MADRE","ABUELO/A","TIO/A","APODERADO");
        gridMainLayoutParents.addComponent(cmbTypeRelationShip,3,1);

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
