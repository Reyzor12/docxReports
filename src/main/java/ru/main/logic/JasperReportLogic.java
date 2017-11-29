package ru.main.logic;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.*;
import net.sf.jasperreports.engine.export.oasis.JROdsExporter;
import net.sf.jasperreports.engine.export.oasis.JROdtExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRPptxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.view.JasperViewer;
import ru.main.items.Report;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JasperReportLogic {

    public static void formJasperReport(String fileTemplate, String fileSave){
        try {
            List<Report> reports = new ArrayList<>();
            reports.add(new Report("Alex","Madagaskar"));
            reports.add(new Report("Pinokio","TroubleShoot"));
            JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(reports);

            Map<String,Object> parameters = new HashMap<>();
            parameters.put("ItemDataSource",itemsJRBean);
            JasperPrint print = JasperFillManager.fillReport(fileTemplate,parameters,new JREmptyDataSource());
            OutputStream outputStream = new FileOutputStream(new File(fileSave));
            JasperExportManager.exportReportToPdfStream(print,outputStream);
            System.out.println("File PDF Generated");
        } catch (JRException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void formJasperReportDoc(String fileTemplate, String fileSave){
        try{
            List<Report> reports = new ArrayList<>();
            reports.add(new Report("Pinnokio","Where are you?"));
            reports.add(new Report("Grizzly","Cold heart"));
            JRBeanCollectionDataSource items = new JRBeanCollectionDataSource(reports);
            Map<String,Object> parameters = new HashMap<>();
            parameters.put("ItemDataSource",items);
            JasperPrint print = JasperFillManager.fillReport(fileTemplate,parameters,new JREmptyDataSource());

            JRDocxExporter exporter = new JRDocxExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT,print);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, new FileOutputStream(new File(fileSave)));
            exporter.exportReport();
            System.out.println("Docx document generated");
        } catch (JRException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void formJRDoc(String fileTemplate, String fileSave){

        List<Report> reports = new ArrayList<>();
        reports.add(new Report("Pinnokio","Where are you?"));
        reports.add(new Report("Grizzly","Cold heart"));
        JRBeanCollectionDataSource items = new JRBeanCollectionDataSource(reports);
        Map<String,Object> parameters = new HashMap<>();
        parameters.put("ItemDataSource",items);
        try {
            JasperPrint print = JasperFillManager.fillReport(fileTemplate,parameters,new JREmptyDataSource());
            export("application/docx",print,new FileOutputStream(new File(fileSave)));
        } catch (JRException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void showReport(String tempPath, String savePath){
        List<Report> reports = new ArrayList<>();
        reports.add(new Report("1","hey"));
        reports.add(new Report("2","value"));
        JRBeanCollectionDataSource items = new JRBeanCollectionDataSource(reports);
        Map<String,Object> parameters = new HashMap<>();
        parameters.put("ItemDataSource",items);
        try{
            JasperPrint print = JasperFillManager.fillReport(tempPath, parameters,new JREmptyDataSource());
            JasperViewer.viewReport(print);

            JasperViewer v = new JasperViewer(print);

            JFrame f = new JFrame("hehey");

            Component[] components = v.getRootPane().getComponents();
            for(Component c : components){
//                f.add(c);
            }
            f.add(components[1]);
            f.show();


        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    public static void export(String mimeType, JasperPrint jp, OutputStream os) throws JRException {
        if ("application/pdf".equalsIgnoreCase(mimeType)) {
            exportReport(new JRPdfExporter(), jp, os);

        } else if ("text/xml".equalsIgnoreCase(mimeType)) {
            exportReport(new HtmlExporter(), jp, os);

        } else if ("application/rtf".equalsIgnoreCase(mimeType)) {
            exportReport(new JRRtfExporter(), jp, os);

        } else if ("application/xls".equalsIgnoreCase(mimeType)) {
            exportReport(new JRXlsExporter(), jp, os);

        } else if ("application/odt".equalsIgnoreCase(mimeType)) {
            exportReport(new JROdtExporter(), jp, os);

        } else if ("application/ods".equalsIgnoreCase(mimeType)) {
            exportReport(new JROdsExporter(), jp, os);

        } else if ("application/docx".equalsIgnoreCase(mimeType)) {
            exportReport(new JRDocxExporter(), jp, os);

        } else if ("application/xlsx".equalsIgnoreCase(mimeType)) {
            exportReport(new JRXlsxExporter(), jp, os);

        } else if ("application/pptx".equalsIgnoreCase(mimeType)) {
            exportReport(new JRPptxExporter(), jp, os);

        } else if ("text/xhmtl".equalsIgnoreCase(mimeType)) {
            exportReport(new JRXhtmlExporter(), jp, os);
        } else {
            throw new IllegalArgumentException("JasperRenderer does not support " + mimeType + " MIME type.");
        }
    }

    public static void exportReport(JRExporter exporter, JasperPrint jp, OutputStream os){
        exporter.setParameter(JRExporterParameter.JASPER_PRINT,jp);
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,os);
        try{
            exporter.exportReport();
        } catch (JRException e) {
            e.printStackTrace();
        }
    }
}
