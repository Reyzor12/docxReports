package ru.main.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.springframework.beans.factory.annotation.Autowired;
import ru.main.logic.ApachPoiWord;
import ru.main.logic.JasperReportLogic;
import ru.main.logic.TestTime;

import java.util.*;

public class Main {

    @FXML
    private Button btnApache;

    @FXML
    private Button btnJasper;

    @Autowired
    private ApachPoiWord apachPoiWord;

    @FXML
    private void initialize(){
        System.out.println("Hello World");
    }

    @FXML
    private void btnClick(ActionEvent event){
        if(event.getSource() == btnApache){
            System.out.println("Apache");
            apachPoiWord = new ApachPoiWord();
            String fullPath = Main.class.getClassLoader().getResource("docs/Readme.txt").getPath();
            String templatePath = Main.class.getClassLoader().getResource("docs/templates.docx").getPath();
            String path = fullPath.substring(0,fullPath.length()-10);

            Map<String,String> replacer = new HashMap<String,String>();
            String newPath = path+"test5"+".docx";
            replacer.put("$name","Billy");
            replacer.put("$age","23");
            replacer.put("$main","Iam");

            String newPath1 = path + "test6" + ".docx";
            String newPath2 = path + "test7" + ".docx";
            List<String> list = new ArrayList<String>(Arrays.asList("1","2","3"));
            List<String> replacer1 = new ArrayList(Arrays.asList("Billy","23","Iam"));

//            apachPoiWord.createEmptyWordDoc(path,"test");
//            apachPoiWord.createFillWordDoc(path,"test1",fullPath);
//            apachPoiWord.createFillWordDocWithBorder(path,"test2",fullPath);
//            apachPoiWord.createWordDocWithTable(path,"test3");
//            apachPoiWord.createDocWithStyle(path,"test4");
//            apachPoiWord.fillReportDoc(templatePath,newPath,replacer);
//            apachPoiWord.insertParagraphIntoDoc(templatePath,newPath1,list);
//            apachPoiWord.insertParagrathInMiddle(templatePath,newPath1,list);
//            apachPoiWord.fillParagraphsInMid(templatePath,newPath1,list);
            /*long start = System.nanoTime();
            apachPoiWord.reportActual(templatePath,newPath1,replacer,list);
            long end = System.nanoTime();
            System.out.println(end-start);
            start = System.nanoTime();
            apachPoiWord.actionReport1(newPath2,replacer1,list);
            end = System.nanoTime();
            System.out.println(end-start);*/
            Class[] cl = new Class[]{String.class,List.class,List.class};
            Class[] cl1 = new Class[]{String.class,String.class,Map.class,List.class};
            Object[] ol = new Object[]{newPath2,replacer1,list};
            Object[] ol1 = new Object[]{templatePath,newPath1,replacer,list};
            System.out.println(TestTime.evaluationTime("ru.main.logic.ApachPoiWord","actionReport1",cl,ol));
            System.out.println(TestTime.evaluationTime("ru.main.logic.ApachPoiWord","reportActual",cl1,ol1));
        } else if(event.getSource() == btnJasper){
            System.out.println("Jasper");
            String fullPath = Main.class.getClassLoader().getResource("docs/Readme.txt").getPath();
            String templatePath = Main.class.getClassLoader().getResource("docs/jasperTemplates.jasper").getPath();
            String templatePath1 = Main.class.getClassLoader().getResource("docs/ReportLook.jasper").getPath();
            String path = fullPath.substring(0,fullPath.length()-10);
            String newPath = path + "JasperPDF" + ".pdf";
            String newPathDoc = path + "JasperDOCX" + ".docx";
            String newPathDoc1 = path + "JasperDOCX1" + ".docx";
            String newPathDoc2 = path + "JasperDOCX2" + ".docx";

//            JasperReportLogic.formJasperReport(templatePath,newPath);
//            JasperReportLogic.formJasperReportDoc(templatePath,newPathDoc);
//            JasperReportLogic.formJRDoc(templatePath,newPathDoc1);
//            JasperReportLogic.formJRDoc(templatePath1,newPathDoc2);
            JasperReportLogic.showReport(templatePath,newPathDoc);
        }
    }
}
