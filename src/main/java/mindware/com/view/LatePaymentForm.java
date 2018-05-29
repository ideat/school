package mindware.com.view;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.server.*;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import mindware.com.model.DetailLatePayment;
import mindware.com.model.LatePayment;
import mindware.com.service.LatePaymentService;
import mindware.com.utilities.DetailLatePaymentUtil;
import mindware.com.utilities.ExcelReport;
import mindware.com.utilities.Util;
import org.vaadin.gridutil.cell.GridCellFilter;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class LatePaymentForm extends CustomComponent implements View {
    private GridLayout gridMainLayout;
    private Grid<LatePayment> gridLatePayment;
    private Panel panelGridLatePayment;
    private DateField dfCutoffDate;
    private Button btnSearch;
    private GridCellFilter<LatePayment> filterLatePayment;
    private Button btnExportExcel;
    private LatePayment latePayment = new LatePayment();
    public LatePaymentForm(){
        setCompositionRoot(buildGridMailLayout());
        headerGrid();
        postBuildt();
    }

    private void postBuildt(){

        btnSearch.addClickListener(clickEvent -> {
            if (!dfCutoffDate.isEmpty()) {
                LatePaymentService latePaymentService = new LatePaymentService();
                gridLatePayment.setItems(latePaymentService.findLatePaymenActiveByCutoffDate(new Util().localDateToDate(dfCutoffDate.getValue())));
                headerGrid();
                filterGrid(gridLatePayment);
            }else {
                Notification.show("Error",
                        "Ingrese una fecha de corte",
                        Notification.Type.ERROR_MESSAGE);
            }
        });

//        btnExportExcel.addClickListener(clickEvent -> {
//            if (latePayment.getStudentId()!=null) {
////                DetailLatePaymentUtil detailLatePaymentUtil = new DetailLatePaymentUtil();
////                List<DetailLatePayment> detailLatePaymentList = detailLatePaymentUtil.getDetailLatePayment(latePayment.getStudentId());
////                if (detailLatePaymentList.get(1).getPaymentMount()!=null) {
////                    ExcelReport excelReport = new ExcelReport();
////                    excelReport.createExcel(detailLatePaymentList);
////
////                    final Resource res = new FileResource(new File(this.getClass().getClassLoader().getResource("/template/").getPath() + "extract_" + latePayment.getStudentId().toString() + ".xlsx"));
////                    ((FileResource) res).setCacheTime(0);
////
////                    FileDownloader fd = new FileDownloader(res) {
////                        @Override
////                        public boolean handleConnectorRequest(VaadinRequest request,
////                                                              VaadinResponse response, String path) throws IOException {
////
////                            boolean result = super.handleConnectorRequest(request, response, path);
////
////                            // Now the accept can be processed
////                            // close the dialog here
////
////                            return result;
////                        }
////                    };
////
////                    fd.extend(btnExportExcel);
////                }
////                else {
////                    Notification.show("Notificacion",
////                            "No realizo ningun pago",
////                            Notification.Type.WARNING_MESSAGE);
////                }
//            } else {
//                Notification.show("Error",
//                        "Seleccione un estudiante",
//                        Notification.Type.ERROR_MESSAGE);
//            }
//
//
//        });

        gridLatePayment.addItemClickListener(itemClick -> {
            latePayment = itemClick.getItem();
        });
    }

    private String getTemplate(String templateName) {
        URL file = getClass().getClassLoader().getResource("template/" + templateName);
        if (file != null)
            return file.getPath();
        return null;
    }

    private String getOutput(String fileName) {
        URL file = getClass().getClassLoader().getResource("template/" + fileName);
        return  file.getPath();
    }


    private void headerGrid(){
        if (gridLatePayment.getHeaderRowCount()>1)
            gridLatePayment.removeHeaderRow(1);
        gridLatePayment.removeAllColumns();
        gridLatePayment.addColumn(LatePayment::getStudentId).setCaption("ID estudiante").setId("studentId").setWidth(100);
        gridLatePayment.addColumn(LatePayment::getNameStudent).setCaption("Nombres").setId("nameStudent");
        gridLatePayment.addColumn(LatePayment::getLastNameStudent).setCaption("Apellidos").setId("lastNameStudent");
        gridLatePayment.addColumn(LatePayment::getCourseLevel).setCaption("Nivel").setId("courseLevel").setWidth(100);
        gridLatePayment.addColumn(LatePayment::getTurn).setCaption("Turno").setId("turn").setWidth(100);
        gridLatePayment.addColumn(LatePayment::getSumPaymentPlanAmount).setCaption("Total a pagar").setId("sumPaymentPlanAmount");
        gridLatePayment.addColumn(LatePayment::getSumPaymentMount).setCaption("Monto pagado");
        gridLatePayment.addColumn(LatePayment::getDelayedAmount).setCaption("Monto adeudado").setId("delayedAmount");
        gridLatePayment.addComponentColumn(payment -> {
            Button button = new Button("XLS");
//            button.setIcon(VaadinIcons.FILE_PRESENTATION);
            button.setStyleName(ValoTheme.BUTTON_FRIENDLY);
//            button.setWidth("90px");
            button.addClickListener(click ->{
                DetailLatePaymentUtil detailLatePaymentUtil = new DetailLatePaymentUtil();
                List<DetailLatePayment> detailLatePaymentList = detailLatePaymentUtil.getDetailLatePayment(payment.getStudentId());
                if (detailLatePaymentList.get(1).getPaymentMount()!=null) {
                    ExcelReport excelReport = new ExcelReport();
                    excelReport.createExcel(detailLatePaymentList);

                    final Resource res = new FileResource(new File(this.getClass().getClassLoader().getResource("/template/").getPath() + "extract_" + payment.getStudentId().toString() + ".xlsx"));
                    ((FileResource) res).setCacheTime(0);

                    FileDownloader fd = new FileDownloader(res) {
                        @Override
                        public boolean handleConnectorRequest(VaadinRequest request,
                                                              VaadinResponse response, String path) throws IOException {

                            boolean result = super.handleConnectorRequest(request, response, path);

                            // Now the accept can be processed
                            // close the dialog here

                            return result;
                        }
                    };

                    fd.extend(button);
                } else {
                    Notification.show("Notificacion",
                            "No realizo ningun pago",
                            Notification.Type.WARNING_MESSAGE);
                }
            });

            return button;
        }).setWidth(90);
        gridLatePayment.setStyleGenerator(t -> {
            if (t.getSumPaymentMount().equals(0.0) ) {
                return "error_row";
            } else {
                return null;
            }
        });

//        gridLatePayment.getColumn("delayedAmount").setRenderer(new ViewButtonValueRenderer(rendererClickEvent -> {
//
//            DetailLatePaymentUtil detailLatePaymentUtil = new DetailLatePaymentUtil();
//            List<DetailLatePayment> detailLatePaymentList = detailLatePaymentUtil.getDetailLatePayment(((LatePayment) rendererClickEvent.getItem()).getStudentId());
//
//            ExcelReport excelReport = new ExcelReport();
//            try {
//                excelReport.excelToPdf(excelReport.createExcel(detailLatePaymentList));
//
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (DocumentException e) {
//                e.printStackTrace();
//            }
//
//
//        }));


    }

    private void filterGrid(final Grid grid){
        this.filterLatePayment = new GridCellFilter(grid);
        this.filterLatePayment.setTextFilter("nameStudent", true, false);
        this.filterLatePayment.setTextFilter("lastNameStudent", true, false);
        this.filterLatePayment.setTextFilter("courseLevel", true, false);
        this.filterLatePayment.setTextFilter("turn", true, false);
        this.filterLatePayment.setNumberFilter("studentId", Integer.class);
    }

    private GridLayout buildGridMailLayout(){
        gridMainLayout = new GridLayout();
        gridMainLayout.setColumns(5);
        gridMainLayout.setRows(5);
        gridMainLayout.setSpacing(true);
        gridMainLayout.setSizeFull();

        dfCutoffDate = new DateField("Fecha corte");
        dfCutoffDate.setStyleName(ValoTheme.DATEFIELD_TINY);
        dfCutoffDate.setDateFormat("dd-MM-yyyy");
        gridMainLayout.addComponent(dfCutoffDate,0,0);

        btnSearch = new Button("Buscar");
        btnSearch.setStyleName(ValoTheme.BUTTON_PRIMARY);
        btnSearch.setIcon(VaadinIcons.SEARCH);
        gridMainLayout.addComponent(btnSearch,1,0);
        gridMainLayout.setComponentAlignment(btnSearch,Alignment.BOTTOM_LEFT);

//        btnExportExcel = new Button("Excel");
//        btnExportExcel.setStyleName(ValoTheme.BUTTON_FRIENDLY);
//        btnExportExcel.setIcon(VaadinIcons.TABLE);
//        gridMainLayout.addComponent(btnExportExcel,2,0);
//        gridMainLayout.setComponentAlignment(btnExportExcel,Alignment.BOTTOM_LEFT);

        gridMainLayout.addComponent(buildPanelGridLatePayment(),0,1,4,4);


        return gridMainLayout;
    }

    private Panel buildPanelGridLatePayment(){
        panelGridLatePayment = new Panel();
        panelGridLatePayment.setStyleName(ValoTheme.PANEL_WELL);
        panelGridLatePayment.setWidth("100%");

        gridLatePayment = new Grid<>(LatePayment.class);
        gridLatePayment.setStyleName(ValoTheme.TABLE_COMPACT);
        gridLatePayment.setWidth("100%");
        panelGridLatePayment.setContent(gridLatePayment);

        return panelGridLatePayment;
    }

}
