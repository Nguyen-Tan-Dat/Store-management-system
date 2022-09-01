package dat.controls;

import com.itextpdf.text.Font;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;
import dat.models.Bill;
import dat.utils.WritePDF;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BillControl extends Control {
    public BillControl() {
        super(new Bill());
    }

    public static void printPDF(String id, String date, String time, String employee,String customer,String promotion, JTable table,String discount, String total) {
        Document document;
        File file = new File("bill_" + date + "_" + id + ".pdf");
        document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(file));
        } catch (DocumentException | FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Font fontData = null;
        Font fontTitle = null;
        try {
            fontData = new Font(BaseFont.createFont("C:\\Windows\\Fonts\\Arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED), 13, Font.NORMAL);
            fontTitle = new Font(BaseFont.createFont("C:\\Windows\\Fonts\\Arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED), 20, Font.NORMAL);
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ex) {
            Logger.getLogger(WritePDF.class.getName()).log(Level.SEVERE, null, ex);
        }
        document.open();
        try {
            Paragraph pdfTitle = new Paragraph(new Phrase("EStore", fontTitle));
            pdfTitle.setAlignment(Element.ALIGN_CENTER);
            document.add(pdfTitle);
            document.add(Chunk.NEWLINE);
            Chunk glue = new Chunk(new VerticalPositionMark());
            Paragraph paragraphID = new Paragraph(new Phrase("Bill: " + id, fontData));

            Paragraph paragraphLink = new Paragraph();
            paragraphLink.setFont(fontData);
            paragraphLink.setAlignment(Element.ALIGN_CENTER);
            paragraphLink.add("www.estore.com ");
            Paragraph adress = new Paragraph();
            adress.setFont(fontData);
            adress.setAlignment(Element.ALIGN_CENTER);
            adress.add("273 An Dương Vương, P.1, Q.5, TP.Hồ Chí Minh");
            Paragraph para1 = new Paragraph();
            para1.setFont(fontData);
            para1.add("Customer: " + customer);
            para1.add(glue);
            para1.add("Date: " + date);
            Paragraph para2 = new Paragraph();
            para2.setPaddingTop(30);
            para2.setFont(fontData);
            para2.add("Employee: " + employee);
            para2.add(glue);
            para2.add("Time: " + time);
            document.add(paragraphLink);
            document.add(adress);
            document.add(paragraphID);
            document.add(para1);
            document.add(para2);
            document.add(Chunk.NEWLINE);
            WritePDF.writeTable(table, document);
            document.add(Chunk.NEWLINE);
            Paragraph paragraphDiscount = new Paragraph(new Phrase("Discount: " + discount, fontData));
            paragraphDiscount.setAlignment(Element.ALIGN_CENTER);
            document.add(paragraphDiscount);
            Paragraph paragraphTotal = new Paragraph(new Phrase("Total: " + total, fontData));
            paragraphTotal.setAlignment(Element.ALIGN_CENTER);
            document.add(paragraphTotal);
            Paragraph note = new Paragraph();
            note.setFont(fontData);
            note.setAlignment(Element.ALIGN_CENTER);
            note.add("Lưu ý: Cửa hàng xuất đơn trong ngày quý khách vui lòng liên hệ thu ngân để được hỗ trợ.");
            document.add(note);
            Paragraph bye=new Paragraph();
            bye.setFont(fontData);
            bye.setAlignment(Element.ALIGN_CENTER);
            bye.add("Xin cảm ơn quý khách. Hẹn gặp lại!");
            document.add(bye);
            document.close();
        } catch (DocumentException ex) {
            ex.printStackTrace();
        }
        Desktop desktop = Desktop.getDesktop();
        if (file.exists()) {
            try {
                desktop.open(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
