package com.utils;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class Excel {
    private final JFileChooser fileChooser = new JFileChooser();

    public Excel() {
        FileNameExtensionFilter filter = new FileNameExtensionFilter("EXCEL 2016", "xlsx");
        fileChooser.addChoosableFileFilter(filter);
        fileChooser.setFileFilter(filter);
    }

    public HashMap<String, HashSet<String[]>> read(Component root) {
        fileChooser.showOpenDialog(root);
        HashMap<String, HashSet<String[]>> result = new HashMap<>();
        if (fileChooser.getSelectedFile() != null)
            try {
                File file = new File(fileChooser.getSelectedFile().getPath());
                XSSFWorkbook wb = new XSSFWorkbook(file);
                XSSFSheet sheet = wb.getSheetAt(0);
                Iterator<Row> itr = sheet.iterator();
                if (itr.hasNext()) {
                    itr.next();
                }
                while (itr.hasNext()) {
                    Row row = itr.next();
                    Iterator<Cell> cellIterator = row.cellIterator();   //iterating over each column
                    Cell pidCell = cellIterator.next();
                    Cell tagCell = cellIterator.next();
                    int id = (int) pidCell.getNumericCellValue();
                    String pid = id + "";
                    String tag = tagCell.getStringCellValue();
                    String price = (int)cellIterator.next().getNumericCellValue() + "";
                    result.computeIfAbsent(pid, k -> new HashSet<>());
                    result.get(pid).add(new String[]{tag, price});
                }
            } catch (IOException | InvalidFormatException e) {
                e.printStackTrace();
            }
        return result;
    }
}
