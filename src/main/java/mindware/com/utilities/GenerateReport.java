package mindware.com.utilities;

import org.pentaho.reporting.engine.classic.core.ClassicEngineBoot;
import org.pentaho.reporting.engine.classic.core.MasterReport;
import org.pentaho.reporting.engine.classic.core.ReportProcessingException;
import org.pentaho.reporting.engine.classic.core.layout.output.AbstractReportProcessor;
import org.pentaho.reporting.engine.classic.core.modules.output.pageable.base.PageableReportProcessor;
import org.pentaho.reporting.engine.classic.core.modules.output.pageable.pdf.PdfOutputProcessor;
import org.pentaho.reporting.engine.classic.core.modules.output.pageable.pdf.PdfReportUtil;
import org.pentaho.reporting.libraries.resourceloader.Resource;
import org.pentaho.reporting.libraries.resourceloader.ResourceManager;

import java.io.ByteArrayOutputStream;
import java.util.Map;

public class GenerateReport {
    public byte[] generateReport(byte[] templateBytes, Map<String, Object> params) throws Exception {
        ClassicEngineBoot.getInstance().start();

        MasterReport reportData = loadTemplateDefinition(templateBytes);
        addParametersToReport(params, reportData);
        byte[] reportBytes = generateReport(reportData);


        return reportBytes;
    }

    public String generateReportPdf(byte[] templateBytes, Map<String, Object> params) throws Exception {
        ClassicEngineBoot.getInstance().start();

        MasterReport reportData = loadTemplateDefinition(templateBytes);
        addParametersToReport(params, reportData);

        PdfReportUtil.createPDF(reportData,"test.pdf");

        return "test.pdf";
    }
    private MasterReport loadTemplateDefinition(byte[] templateBytes) throws Exception {
        ResourceManager resourceManager = new ResourceManager();
        Resource templateResource = resourceManager.createDirectly(templateBytes, MasterReport.class);
        return (MasterReport) templateResource.getResource();
    }

    private void addParametersToReport(Map<String, Object> params, MasterReport reportData) {
        if (params != null) {
            for (String key : params.keySet()) {
                reportData.getParameterValues().put(key, params.get(key));
            }
        }
    }

    private byte[] generateReport(MasterReport reportData) throws ReportProcessingException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfOutputProcessor outputProcessor = new PdfOutputProcessor(reportData.getConfiguration(), outputStream, reportData.getResourceManager());

        AbstractReportProcessor reportProcessor = null;
        try {
            reportProcessor = new PageableReportProcessor(reportData, outputProcessor);
            reportProcessor.processReport();
        } finally {
            if (reportProcessor != null) {
                reportProcessor.close();
            }
        }

        return outputStream.toByteArray();
    }
}
