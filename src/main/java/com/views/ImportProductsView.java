package com.views;

import com.utils.GoogleMaterialDesignIcons;
import com.utils.MyColor;
import com.views.components.ItemWrap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ImportProductsView extends View{
    @Override
    public View newView() {
        return new ImportProductsView();
    }
    public ImportProductsView(){
        JPanel menu = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        var foreground = MyColor.text;
        var background = MyColor.white;
        var hoverBackground = MyColor.light_primary;
        var fromScanner = new ItemWrap(GoogleMaterialDesignIcons.SCANNER, "Nhập thủ công", background, foreground, hoverBackground);
        var fromExcel = new ItemWrap(GoogleMaterialDesignIcons.DESCRIPTION, "Nhập từ tệp Excel", background, foreground, hoverBackground);
        menu.add(fromScanner);
        menu.add(fromExcel);
        fromScanner.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                MainFrame.setView(new ImportFromScannerView());
            }
        });
        fromExcel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                MainFrame.setView(new ImportFromExcelView());
            }
        });
        setLayout(new BorderLayout());
        add(menu, BorderLayout.CENTER);
    }
}
