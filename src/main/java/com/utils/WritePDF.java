package com.utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

import javax.swing.*;
import java.io.IOException;

public class WritePDF {
    private static final Font font = newFont();


    public static Font newFont() {
        Font font;
        try {
            font = new Font(BaseFont.createFont("C:\\Windows\\Fonts\\Arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED), 11, Font.NORMAL);

        } catch (DocumentException | IOException e) {
            throw new RuntimeException(e);
        }
        return font;
    }

    public static void writeTable(JTable table, Document document) {
        PdfPTable pdfTable = new PdfPTable(table.getColumnCount());
        for (int i = 0; i < table.getColumnCount(); i++) {
            PdfPCell cell = new PdfPCell();
            cell.setMinimumHeight(24);
            cell.setVerticalAlignment(Element.ALIGN_CENTER);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            Font font = newFont();
            assert font != null;
            font.setStyle(Font.BOLD);
            cell.setPhrase(new Phrase(table.getColumnName(i), font));
            pdfTable.addCell(cell);
        }
        for (int i = 0; i < table.getRowCount(); i++) {
            for (int j = 0; j < table.getColumnCount(); j++) {
                String value = (String) table.getValueAt(i, j);
                PdfPCell cell = new PdfPCell(new Phrase(value, font));
                cell.setMinimumHeight(20);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                pdfTable.addCell(cell);
            }
        }

        try {
            document.add(pdfTable);
        } catch (DocumentException ex) {
            ex.printStackTrace();
        }

    }
}
