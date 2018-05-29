package mindware.com.view;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import mindware.com.model.Parameter;
import mindware.com.service.ParameterService;

import java.util.List;

public class ParameterForm extends CustomComponent implements View {
    private GridLayout gridMainLayout;
    private Panel panelGridParameter;
    private Grid<Parameter> gridParameter;
    private TextField txtParameterId;
    private ComboBox cmbTypeParameter;
    private TextField txtValueParameter;
    private TextField txtDescriptionParameter;
    private ComboBox cmbState;
    private Button btnSaveParameter;
    private Button btnNewParameter;

    private ParameterService parameterService;

    public ParameterForm(){
        setCompositionRoot(buildGridMainLayout());
        parameterService = new ParameterService();
        postBuild();
    }

    private void postBuild(){
        cmbTypeParameter.addValueChangeListener(valueChangeEvent -> {
           fillGridParameter(parameterService.findParameterByType(valueChangeEvent.getValue().toString()));
        });

        btnSaveParameter.addClickListener(clickEvent -> {
            if (validateData()) {
                if (txtParameterId.isEmpty()) {
                    if (validateTypeValue(0))
                        parameterService.insertParameter(prepareData());
                    else {
                        Notification.show("Error",
                                "Ya existen los datos",
                                Notification.Type.ERROR_MESSAGE);
                    }
                } else {

                    parameterService.updateParameter(prepareData());
                }
                Notification.show("Parametros ",
                        "Datos registrados",
                        Notification.Type.HUMANIZED_MESSAGE);
            }else{
                Notification.show("Error",
                        "Llene todos los datos",
                        Notification.Type.ERROR_MESSAGE);
            }
            fillGridParameter(parameterService.findParameterByType(cmbTypeParameter.getValue().toString()));
            clearFields();
        });

        btnNewParameter.addClickListener(clickEvent -> {
           clearFields();
           try {
               cmbTypeParameter.clear();
           }catch (Exception e){

           }
        });

        gridParameter.addItemClickListener(itemClick -> {
            fillDataParameterSelected(itemClick.getItem());
        });
    }

    private void fillDataParameterSelected(Parameter parameter){
        txtParameterId.setValue(parameter.getParameterId().toString());
        txtDescriptionParameter.setValue(parameter.getDescriptionParameter());
        txtValueParameter.setValue(parameter.getValueParameter());
        cmbTypeParameter.setValue(parameter.getTypeParameter());
        cmbState.setValue(parameter.getState());
    }

    private boolean validateTypeValue(int control){
        if (parameterService.countParameterByNameAndType(cmbTypeParameter.getValue().toString(),txtValueParameter.getValue())<=control)
            return true;
        else return false;
    }

    private void clearFields(){
        txtParameterId.clear();
        txtValueParameter.clear();
        txtDescriptionParameter.clear();
        txtValueParameter.setEnabled(true);
        cmbState.clear();
    }

    private boolean validateData(){
        if (cmbTypeParameter.isEmpty()) return false;
        if (txtDescriptionParameter.isEmpty()) return false;
        if (txtValueParameter.isEmpty()) return false;
        if (cmbState.isEmpty()) return false;
        return true;
    }

    private Parameter prepareData(){
        Parameter parameter = new Parameter();
        if (!txtParameterId.isEmpty())
            parameter.setParameterId(Integer.parseInt(txtParameterId.getValue()));
        parameter.setTypeParameter(cmbTypeParameter.getValue().toString());
        parameter.setDescriptionParameter(txtDescriptionParameter.getValue());
        parameter.setValueParameter(txtValueParameter.getValue());
        parameter.setState(cmbState.getValue().toString());
        return parameter;
    }

    private void fillGridParameter(List<Parameter> parameterList){
        gridParameter.removeAllColumns();
        gridParameter.setItems(parameterList);
        gridParameter.addColumn(Parameter::getParameterId).setCaption("ID").setId("parameterId");
        gridParameter.addColumn(Parameter::getDescriptionParameter).setCaption("Nombre").setId("descriptionParameter");
        gridParameter.addColumn(Parameter::getValueParameter).setCaption("Valor").setId("valueParameter");
        gridParameter.addColumn(Parameter::getTypeParameter).setCaption("Tipo").setId("typeParameter");
        gridParameter.addColumn(Parameter::getState).setCaption("Estado").setId("state");
    }

    private GridLayout buildGridMainLayout(){
        gridMainLayout = new GridLayout();
        gridMainLayout.setRows(5);
        gridMainLayout.setColumns(5);
        gridMainLayout.setSpacing(true);
        gridMainLayout.setWidth("100%");

        txtParameterId = new TextField("ID");
        txtParameterId.setStyleName(ValoTheme.TEXTFIELD_TINY);
        txtParameterId.setEnabled(false);
        gridMainLayout.addComponent(txtParameterId,0,0);

        cmbTypeParameter = new ComboBox("Tipo parametro");
        cmbTypeParameter.setStyleName(ValoTheme.COMBOBOX_TINY);
        cmbTypeParameter.setEmptySelectionAllowed(false);
        cmbTypeParameter.setItems("NIVEL_CURSOS","CURSOS","CONCEPTO_PAGOS","COBRO_CUOTAS","COMPUTACION");
        gridMainLayout.addComponent(cmbTypeParameter,1,0);

        txtDescriptionParameter = new TextField("Nombre parametro");
        txtDescriptionParameter.setStyleName(ValoTheme.TEXTFIELD_TINY);
        gridMainLayout.addComponent(txtDescriptionParameter,0,1);

        txtValueParameter = new TextField("Valor parametro");
        txtValueParameter.setStyleName(ValoTheme.TEXTFIELD_TINY);
        gridMainLayout.addComponent(txtValueParameter,1,1);

        cmbState = new ComboBox("Estado");
        cmbState.setStyleName(ValoTheme.COMBOBOX_TINY);
        cmbState.setEmptySelectionAllowed(false);
        cmbState.setItems("ACTIVO","INACTIVO");
        gridMainLayout.addComponent(cmbState,2,1);

        btnSaveParameter = new Button("Guardar");
        btnSaveParameter.setStyleName(ValoTheme.BUTTON_PRIMARY);
        btnSaveParameter.setIcon(VaadinIcons.DATABASE);
        btnSaveParameter.setWidth("110px");
        gridMainLayout.addComponent(btnSaveParameter,3,0);
        gridMainLayout.setComponentAlignment(btnSaveParameter,Alignment.BOTTOM_LEFT);

        btnNewParameter = new Button("Nuevo");
        btnNewParameter.setStyleName(ValoTheme.BUTTON_FRIENDLY);
        btnNewParameter.setIcon(VaadinIcons.PLUS_CIRCLE);
        btnNewParameter.setWidth("110px");
        gridMainLayout.addComponent(btnNewParameter,3,1);
        gridMainLayout.setComponentAlignment(btnNewParameter,Alignment.BOTTOM_LEFT);

        gridMainLayout.addComponent(buildPanelGridParameter(),0,2,3,3);

        return gridMainLayout;
    }

    private Panel buildPanelGridParameter(){
        panelGridParameter = new Panel();
        panelGridParameter.setStyleName(ValoTheme.PANEL_WELL);
        panelGridParameter.setWidth("100%");

        gridParameter = new Grid<>();
        gridParameter.setStyleName(ValoTheme.TABLE_SMALL);
        gridParameter.setWidth("100%");
        panelGridParameter.setContent(gridParameter);

        return panelGridParameter;
    }
}
