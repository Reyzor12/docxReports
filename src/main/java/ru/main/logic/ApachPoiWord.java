package ru.main.logic;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlCursor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
public class ApachPoiWord {


    public ApachPoiWord(){}

    public void createEmptyWordDoc(String path, String name){
        XWPFDocument document = new XWPFDocument();
        try {
            FileOutputStream out = new FileOutputStream(new File(path  + name + ".docx"));
            document.write(out);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Empty docx file are created");
    }

    public void createFillWordDoc(String path, String name, String text){
        XWPFDocument document = new XWPFDocument();
        try{
            FileOutputStream out = new FileOutputStream(new File(path + name + ".docx"));
            XWPFParagraph paragraph = document.createParagraph();
            XWPFRun run = paragraph.createRun();
            run.setText(text);
            document.write(out);
            out.close();
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Fill docx file are created");
    }

    public void createFillWordDocWithBorder(String path, String name, String text){

        XWPFDocument document = new XWPFDocument();
        try{
            FileOutputStream out = new FileOutputStream(new File(path + name + ".docx"));
            XWPFParagraph paragraph = document.createParagraph();
            paragraph.setBorderBottom(Borders.BASIC_BLACK_DASHES);
            XWPFRun run = paragraph.createRun();
            run.setText(text);
            document.write(out);
            out.close();
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Fill docx file with border are created");
    }

    public void createWordDocWithTable(String path, String name){
        XWPFDocument document = new XWPFDocument();
        try{
            FileOutputStream out = new FileOutputStream(new File(path + name + ".docx"));

            XWPFTable table = document.createTable();

            XWPFTableRow oneRow = table.getRow(0);

            oneRow.getCell(0).setText("Hello");
            oneRow.createCell().setText("World");
            oneRow.createCell().setText("!!!");

            XWPFTableRow twoRow = table.createRow();
            twoRow.createCell().setText("it wonderful");
            twoRow.createCell().setText("WORLD!");

            document.write(out);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("docx with table are created");
    }

    public void createDocWithStyle(String path, String name){
        XWPFDocument document = new XWPFDocument();
        try{
            FileOutputStream out = new FileOutputStream(new File(path + name + ".docx"));
            XWPFParagraph paragraph = document.createParagraph();
            XWPFRun paragraphRun = paragraph.createRun();
            paragraphRun.setBold(true);
            paragraphRun.setFontSize(18);
            paragraphRun.setText("Hello");
            paragraphRun.addBreak();
            paragraphRun.setText("World!!!");
            document.write(out);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("docx with style are created");
    }

    public void fillReportDoc(String filePath, String name,Map<String,String> replacer){
        XWPFDocument document = null;
        try {
            document = new XWPFDocument(OPCPackage.open(filePath));
            for(XWPFParagraph paragraph : document.getParagraphs()){
                for(XWPFRun run : paragraph.getRuns()){
                    String text = run.getText(0);
                    if(text != null){
                        for(String key : replacer.keySet()){
                            if(text.contains(key)){
                                text = text.replace(key,replacer.get(key));
                            }
                        }
                        run.setText(text,0);
                    }
                }
            }
            document.write(new FileOutputStream(name));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
        System.out.println("Document are filled");
    }

    public void insertParagraphIntoDoc(String filePath, String fileSave, List<String> repors){
        XWPFDocument document = null;
        try{
            document = new XWPFDocument(OPCPackage.open(filePath));
            for(XWPFParagraph paragraph : document.getParagraphs()){
                String text = paragraph.getRuns().get(0).getText(0);
                if(text.contains("$list")){
                    if(paragraph != null){
                        XWPFDocument doc = paragraph.getDocument();
                        XmlCursor cursor = paragraph.getCTP().newCursor();
                        for(int i = 0; i < repors.size();i++){
                            XWPFParagraph par = doc.createParagraph();
                            par.getCTP().setPPr(paragraph.getCTP().getPPr());
                            XWPFRun run1 = par.createRun();
                            run1.getCTR().setRPr(paragraph.getRuns().get(0).getCTR().getRPr());
                            run1.setText(repors.get(i));
                            XmlCursor cursor2 = par.getCTP().newCursor();
                            cursor2.moveXml(cursor2);
                            cursor2.dispose();
                        }
                        cursor.removeXml();
                        cursor.dispose();
                    }
                    break;
                }
            }
            document.write(new FileOutputStream(fileSave));
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Document are filled with new Paragraph");
    }

    public void insertParagrathInMiddle(String filePath, String fileSave, List<String> list){

        XWPFDocument document = null;
        try{
            document = new XWPFDocument(OPCPackage.open(filePath));
            for(XWPFParagraph paragraph : document.getParagraphs()){
                String text = paragraph.getText();
                System.out.println(text);
                if(text.contains("$list")){
                    XmlCursor cursor = paragraph.getCTP().newCursor();
                    XWPFParagraph newParagraph = document.insertNewParagraph(cursor);
                    newParagraph.createRun().setText("Hello World");
                    break;
                }
            }
            document.write(new FileOutputStream(fileSave));
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("insertParagrathInMiddle created");
    }

    public void fillParagraphsInMid(String filePath,String fileSave, List<String> list){
        XWPFDocument document = null;
        try{
            document = new XWPFDocument(OPCPackage.open(filePath));
            List<XWPFParagraph> paragraphs = document.getParagraphs();
            for(XWPFParagraph paragraph : paragraphs){
                String text = paragraph.getText();
                System.out.println(text);
                if(text.contains("$list")){
                    XmlCursor cursor = paragraph.getCTP().newCursor();
                    XWPFParagraph par = document.insertNewParagraph(cursor);
                    par.createRun().setText("Hello World");

                    cursor.dispose();
                    XmlCursor cursor1 = par.getCTP().newCursor();
//                    cursor1.moveXml(cursor1);
                    XWPFParagraph par1 = document.insertNewParagraph(cursor1);
                    par1.createRun().setText("Double Hello world");
                    cursor1.dispose();
                    XmlCursor cursor2 = paragraph.getCTP().newCursor();
                    cursor2.removeXml();
                    cursor2.dispose();



                    System.out.println(paragraphs.size());
                    break;
                }
            }
            document.write(new FileOutputStream(fileSave));
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("fillParagraphsInMid");
    }

    public void reportActual(String filePath,String fileSave,Map<String,String>replacer, List<String> list){
        XWPFDocument document = null;
        try{
            document = new XWPFDocument(OPCPackage.open(filePath));
            for(XWPFParagraph paragraph : document.getParagraphs()){
                for(String key : replacer.keySet()){
                    if(paragraph.getText().contains(key)){
                        paragraph.getRuns().get(0).setText(
                                paragraph.getRuns().get(0).getText(0).replace(key,replacer.get(key))
                                ,0);
                    }
                }
            }
            for(XWPFTable table : document.getTables()){
                for(XWPFTableRow row : table.getRows()){
                    for(XWPFTableCell cell : row.getTableCells()){
                        for(XWPFParagraph paragraph : cell.getParagraphs()){
                            for(String key : replacer.keySet()){
                                if(paragraph.getText().contains(key)){
                                    paragraph.getRuns().get(0).setText(
                                            paragraph.getRuns().get(0).getText(0).replace(key,replacer.get(key))
                                            ,0);
                                }
                            }
                        }
                    }
                }
            }
            for(XWPFParagraph paragraph: document.getParagraphs()){
                if(paragraph.getText().contains("$list")){
                    for(String item : list){
                        XmlCursor cursor = paragraph.getCTP().newCursor();
                        document.insertNewParagraph(cursor).createRun().setText(item);
                    }
                    paragraph.removeRun(0);
//                    paragraph.getRuns().get(0).setText("",0);
                    break;
                }
            }
            document.write(new FileOutputStream(fileSave));
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("reportActual");
    }
}
