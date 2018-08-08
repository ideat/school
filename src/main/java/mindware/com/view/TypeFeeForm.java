package mindware.com.view;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import mindware.com.model.ClassPeriod;
import mindware.com.model.TypeFee;
import mindware.com.service.ClassPeriodService;
import mindware.com.service.TypeFeeService;
import org.vaadin.ui.NumberField;


public class TypeFeeForm extends CustomComponent implements View {

    private GridLayout gridMainLayout;
    private TextField txtTypeFeeId;
    private NumberField txtMountFee;
    private NumberField txtDiscountPercentaje;
    private TextField txtNameTypeFee;
    private ComboBox<ClassPeriod> cmbClassPeriod;
    private Grid<TypeFee> gridTypeFee;
    private Button btnSaveTypeFee;
    private Button btnNewTypeFee;
    private TypeFeeService typeFeeService;
    private String year;
    public TypeFeeForm(){
        setCompositionRoot(buildGridMainLayout());
        typeFeeService = new TypeFeeService();
        ClassPeriodService classPeriodService = new ClassPeriodService();
        year = classPeriodService.findClassPeriodByState("ACTIVO").get(0).getYear();
        fillGridTypeFee(year);
        fillClassPeriod();
        postBuild();
    }

    private void postBuild(){
        btnSaveTypeFee.addClickListener(clickEvent -> {
           if (validateData()){
               if(txtTypeFeeId.isEmpty()){
                   insertTypeFee();
                   fillGridTypeFee(year);
                   clearFields();
               }else{
                    updateTypeFee();
                    fillGridTypeFee(year);
                    clearFields();
               }
           }else {
               Notification.show("ERROR",
                       " Complete la informacion a registrar ",
                       Notification.Type.ERROR_MESSAGE);
           }
        });

        btnNewTypeFee.addClickListener(clickEvent -> clearFields());

        gridTypeFee.addItemClickListener(itemClick -> {
            fillTypeFee(itemClick.getItem());
        });
    }

    private void fillTypeFee(TypeFee typeFee){
        txtTypeFeeId.setValue(typeFee.getTypeFeeId().toString());
        txtNameTypeFee.setValue(typeFee.getNameFee());
        txtMountFee.setValue(typeFee.getMountFee());
        txtDiscountPercentaje.setValue(typeFee.getDiscountPercentaje());
        cmbClassPeriod.setValue(typeFee.getClassPeriod());
    }

    private void clearFields(){
        txtTypeFeeId.clear();
        txtDiscountPercentaje.clear();
        txtMountFee.clear();
        txtNameTypeFee.clear();
        cmbClassPeriod.clear();
    }

    private boolean validateData(){
        if(txtNameTypeFee.isEmpty()) return false;
        if(txtMountFee.isEmpty()) return false;
        if(txtDiscountPercentaje.isEmpty()) return false;
        if(cmbClassPeriod.isEmpty()) return false;

        return true;
    }

    private void fillGridTypeFee(String year){
        gridTypeFee.removeAllColumns();
        gridTypeFee.setItems(typeFeeService.findTypeFeeByClassPeriodYear(year));
        gridTypeFee.addColumn(TypeFee::getTypeFeeId).setCaption("ID");
        gridTypeFee.addColumn(TypeFee::getNameFee).setCaption("Tipo cuota");
        gridTypeFee.addColumn(TypeFee::getMountFee).setCaption("Monto cuota");
        gridTypeFee.addColumn(TypeFee::getDiscountPercentaje).setCaption("Porcentaje descuento");

    }

    private void updateTypeFee(){
        try {
            TypeFee typeFee = prepareTypeFee();
            typeFee.setTypeFeeId(Integer.parseInt(txtTypeFeeId.getValue()));
            typeFeeService.updateTypeFee(typeFee);
            Notification.show("Tipo de pago",
                    " Se actualizaron los datos ",
                    Notification.Type.HUMANIZED_MESSAGE);
        }catch (Exception e){
            Notification.show("ERROR",
                    " Al actualizar " + e.getMessage(),
                    Notification.Type.ERROR_MESSAGE);
        }
    }

    private void insertTypeFee(){
        try {
            typeFeeService.insertTypeFee(prepareTypeFee());
            Notification.show("Tipo de pago",
                    " Se registro correctamente ",
                    Notification.Type.HUMANIZED_MESSAGE);
        }catch (Exception e){
            Notification.show("ERROR",
                    " Al registrar " + e.getMessage(),
                    Notification.Type.ERROR_MESSAGE);
        }

    }

    private TypeFee prepareTypeFee(){
        TypeFee typeFee = new TypeFee();
        typeFee.setClassPeriodId(cmbClassPeriod.getValue().getClassPeriodId());
        typeFee.setMountFee(Double.parseDouble(txtMountFee.getValue()));
        typeFee.setDiscountPercentaje(txtDiscountPercentaje.getDoubleValueDoNotThrow());
        typeFee.setNameFee(txtNameTypeFee.getValue());
        return typeFee;
    }

    private void fillClassPeriod(){
        ClassPeriodService classPeriodService = new ClassPeriodService();
        cmbClassPeriod.setItems(classPeriodService.findClassPeriodByState("ACTIVO"));
        cmbClassPeriod.setItemCaptionGenerator(ClassPeriod::getYear);
    }

    private GridLayout buildGridMainLayout(){
        gridMainLayout = new GridLayout();
        gridMainLayout.setSpacing(true);
        gridMainLayout.setColumns(5);
        gridMainLayout.setRows(5);

        txtTypeFeeId = new TextField("ID");
        txtTypeFeeId.setStyleName(ValoTheme.TEXTFIELD_TINY);
        txtTypeFeeId.setEnabled(false);
        gridMainLayout.addComponent(txtTypeFeeId,0,0);

        cmbClassPeriod = new ComboBox<>("Gestion");
        cmbClassPeriod.setStyleName(ValoTheme.COMBOBOX_TINY);
        cmbClassPeriod.setEmptySelectionAllowed(false);
        gridMainLayout.addComponent(cmbClassPeriod,1,0);

        txtNameTypeFee = new TextField("Nombre tipo cuota");
        txtNameTypeFee.setStyleName(ValoTheme.TEXTFIELD_TINY);
        gridMainLayout.addComponent(txtNameTypeFee,0,1);

        txtMountFee = new NumberField("Monto cuota");
        txtMountFee.setStyleName(ValoTheme.TEXTFIELD_TINY);
        txtMountFee.setDecimalPrecision(2);
//        txtMountFee.setDecimalSeparator('.');
        txtMountFee.setNegativeAllowed(false);
        txtMountFee.setErrorText("Monto invalido");
        txtMountFee.setMinValue(0.0);
        gridMainLayout.addComponent(txtMountFee,1,1);

        txtDiscountPercentaje = new NumberField("Porcentaje descuento");
        txtDiscountPercentaje.setStyleName(ValoTheme.TEXTFIELD_TINY);
        txtDiscountPercentaje.setDecimalPrecision(2);
        txtDiscountPercentaje.setDecimalSeparator('.');
        txtDiscountPercentaje.setNegativeAllowed(false);
        txtDiscountPercentaje.setMinValue(0.0);
        txtDiscountPercentaje.setMaxValue(100.0);
        txtDiscountPercentaje.setErrorText("Porcentaje incorrecto");
        gridMainLayout.addComponent(txtDiscountPercentaje,2,1);

        btnSaveTypeFee = new Button("Guardar");
        btnSaveTypeFee.setStyleName(ValoTheme.BUTTON_PRIMARY);
        btnSaveTypeFee.setIcon(VaadinIcons.DATABASE);
        btnSaveTypeFee.setWidth("120px");
        gridMainLayout.addComponent(btnSaveTypeFee,3,0);
        gridMainLayout.setComponentAlignment(btnSaveTypeFee,Alignment.BOTTOM_LEFT);

        btnNewTypeFee = new Button("Nuevo");
        btnNewTypeFee.setStyleName(ValoTheme.BUTTON_FRIENDLY);
        btnNewTypeFee.setIcon(VaadinIcons.PLUS_CIRCLE);
        btnNewTypeFee.setWidth("120px");
        gridMainLayout.addComponent(btnNewTypeFee,3,1);
        gridMainLayout.setComponentAlignment(btnNewTypeFee,Alignment.BOTTOM_LEFT);

        gridMainLayout.addComponent(buildPanelGridTypeFee(),0,2,3,3);
        return gridMainLayout;
    }

    private Panel buildPanelGridTypeFee(){
        Panel panelGridTypeFee = new Panel();
        panelGridTypeFee.setStyleName(ValoTheme.PANEL_WELL);
        panelGridTypeFee.setWidth("100%");

        gridTypeFee = new Grid<>();
        gridTypeFee.setStyleName(ValoTheme.TABLE_COMPACT);
        gridTypeFee.setWidth("100%");

        panelGridTypeFee.setContent(gridTypeFee);

        return panelGridTypeFee;
    }



}
