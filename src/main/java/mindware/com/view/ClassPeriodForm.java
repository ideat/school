package mindware.com.view;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import mindware.com.model.ClassPeriod;
import mindware.com.service.ClassPeriodService;
import org.vaadin.ui.NumberField;

public class ClassPeriodForm extends CustomComponent implements View {
    private GridLayout gridMainLayout;
    private FormLayout formLayout;
    private NumberField txtYear;
    private TextField txtClassPeriodId;
    private Button btnSave;
    private ComboBox cmbState;
    private Grid<ClassPeriod> gridYears;
    private ClassPeriodService classPeriodService;

    public ClassPeriodForm(){
        setCompositionRoot(buildGridMainLayout());
        classPeriodService = new ClassPeriodService();
        fillGridYear();
        postBuild();
    }

    private void postBuild(){
        btnSave.addClickListener(clickEvent -> {
           if (validateData()){
               if (txtClassPeriodId.isEmpty()) {
                   if (searchYear(txtYear.getValue())) {
                       insertClassPeriod();
                       clearFields();
                       fillGridYear();
                       Notification.show("Nueva Gestion",
                               " Se registro la nueva gestion ",
                               Notification.Type.HUMANIZED_MESSAGE);
                   } else {
                       Notification.show("ERROR",
                               "Gestion ya registrada ",
                               Notification.Type.ERROR_MESSAGE);
                       txtYear.focus();
                   }
               }else {
                   updateClassPeriod();
                   clearFields();
                   fillGridYear();
                   Notification.show("Actualizacion",
                           " Se actualizaron los datos ",
                           Notification.Type.HUMANIZED_MESSAGE);
               }

           }else {
               Notification.show("ERROR",
                       "Complete los datos " ,
                       Notification.Type.WARNING_MESSAGE);
               txtYear.focus();
           }


        });

        gridYears.addItemClickListener(itemClick -> {

            ClassPeriod classPeriod = itemClick.getItem();
            fillClassPeriod(classPeriod);
        });
    }

    private void clearFields(){
        txtClassPeriodId.clear();
        txtYear.clear();
        cmbState.clear();
    }

    private void insertClassPeriod() {
        try {
            ClassPeriod classPeriod = new ClassPeriod();
            classPeriod.setYear(txtYear.getValue());
            classPeriod.setState(cmbState.getValue().toString());
            classPeriodService.insertClassPeriod(classPeriod);
        }catch (Exception e){
            Notification.show("ERROR",
                    " Al insertar los datos " +e.getMessage() ,
                    Notification.Type.ERROR_MESSAGE);
        }
    }

    private void updateClassPeriod(){
        try{
            ClassPeriod classPeriod = new ClassPeriod();

            classPeriod.setClassPeriodId(Integer.parseInt(txtClassPeriodId.getValue()));
            classPeriod.setYear(txtYear.getValue());
            classPeriod.setState(cmbState.getValue().toString());

            classPeriodService.updateClassPeriod(classPeriod);
        }catch (Exception e){
            Notification.show("ERROR",
                    " Al actualizar los datos " +e.getMessage() ,
                    Notification.Type.ERROR_MESSAGE);
        }
    }

    private void fillClassPeriod(ClassPeriod classPeriod){
        txtClassPeriodId.setValue(classPeriod.getClassPeriodId().toString());
        txtYear.setValue(classPeriod.getYear());
        cmbState.setValue(classPeriod.getState());
    }

    private boolean validateData(){
        if (txtYear.isEmpty()) return false;
        return true;
    }

    private boolean searchYear(String year){
        if (classPeriodService.getCountClassPeriodByYear(year)>0) return false;
        return true;
    }


    private void fillGridYear(){
        gridYears.removeAllColumns();
        gridYears.setItems(classPeriodService.findAllClassPeriod());
        gridYears.addColumn(ClassPeriod::getClassPeriodId).setCaption("ID");
        gridYears.addColumn(ClassPeriod::getYear).setCaption("Gestion");
        gridYears.addColumn(ClassPeriod::getState).setCaption("Estado");
    }

    private GridLayout buildGridMainLayout(){
        gridMainLayout = new GridLayout();
        gridMainLayout.setColumns(3);
        gridMainLayout.setRows(3);
        gridMainLayout.setSizeFull();
        gridMainLayout.setSpacing(true);

        gridMainLayout.addComponent(buildFormLayout(),1,1);

        gridYears = new Grid<>();
        gridYears.setStyleName(ValoTheme.TABLE_SMALL);
        gridYears.setWidth("100%");
        gridYears.setHeight("320px");

        Panel panelGridYear = new Panel();
        panelGridYear.setStyleName(ValoTheme.PANEL_WELL);
        panelGridYear.setContent(gridYears);
        panelGridYear.setHeight("320px");
        gridMainLayout.addComponent(panelGridYear,0,2,2,2);

        return gridMainLayout;
    }

    private FormLayout buildFormLayout(){
        formLayout = new FormLayout();
        formLayout.setSpacing(true);

        txtClassPeriodId = new TextField("ID:");
        txtClassPeriodId.setStyleName(ValoTheme.TEXTFIELD_TINY);
        txtClassPeriodId.setEnabled(false);
        formLayout.addComponent(txtClassPeriodId);

        txtYear = new NumberField("Gestión:");
        txtYear.setStyleName(ValoTheme.TEXTFIELD_TINY);
        txtYear.setDecimalAllowed(false);
        txtYear.setNegativeAllowed(false);
        txtYear.setDecimalPrecision(0);
        txtYear.setMinValue(2018);
        txtYear.setMaxValue(2030);

        formLayout.addComponent(txtYear);

        cmbState = new ComboBox("Estado:");
        cmbState.setStyleName(ValoTheme.COMBOBOX_TINY);
        cmbState.setItems("ACTIVO","INACTIVO");
        cmbState.setEmptySelectionAllowed(false);
        formLayout.addComponent(cmbState);

        btnSave = new Button("Guardar");
        btnSave.setStyleName(ValoTheme.BUTTON_PRIMARY);
        btnSave.setIcon(VaadinIcons.DATABASE);
        formLayout.addComponent(btnSave);

        return formLayout;
    }
}
