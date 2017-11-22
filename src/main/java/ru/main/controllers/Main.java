package ru.main.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.springframework.beans.factory.annotation.Autowired;
import ru.main.logic.ApachPoiWord;

import java.util.HashMap;
import java.util.Map;

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
            apachPoiWord.createEmptyWordDoc(path,"test");
            apachPoiWord.createFillWordDoc(path,"test1",fullPath);
            apachPoiWord.createFillWordDocWithBorder(path,"test2",fullPath);
            apachPoiWord.createWordDocWithTable(path,"test3");
            apachPoiWord.createDocWithStyle(path,"test4");
            apachPoiWord.fillReportDoc(templatePath,newPath,replacer);

        } else if(event.getSource() == btnJasper){
            System.out.println("Jasper");
        }
    }
}
