package ru.main.logic;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import ru.main.items.Report;

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
            System.out.println("File Generated");
        } catch (JRException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
