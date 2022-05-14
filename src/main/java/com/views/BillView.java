package com.views;

import com.controls.BillControl;
import com.controls.BillDetailControl;
import com.controls.Control;
import com.utils.MyColor;
import com.utils.MyTable;
import com.views.components.SearchBar;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

public class BillView extends View {
    private final JTable table;
    private final JTable tableDetail;
    private final Control billControl = new BillControl();
    private final BillDetailControl billDetailControl = new BillDetailControl();

    public BillView() {
        setLayout(new BorderLayout(0, 0));
        SearchBar searchBar = new SearchBar(new Color(41, 141, 217));
        add(searchBar, BorderLayout.NORTH);
        JPanel center = new JPanel();
        add(center, BorderLayout.CENTER);
        center.setLayout(new BorderLayout(0, 0));
        JScrollPane spTableDetail = new JScrollPane();
        JScrollPane spTableBills = new JScrollPane();
        JPanel centerLeft = new JPanel();
        centerLeft.setLayout(new BorderLayout(0, 0));
        centerLeft.setBorder(new TitledBorder(null, "Hóa đơn bán", TitledBorder.LEADING, TitledBorder.TOP, null, MyColor.text));
        center.add(centerLeft, BorderLayout.CENTER);
        centerLeft.add(spTableBills, BorderLayout.CENTER);
        JPanel centerRight = new JPanel();
        centerRight.setLayout(new BorderLayout(0, 0));
        centerRight.setBorder(new TitledBorder(null, "Chi tiết hóa đơn", TitledBorder.LEADING, TitledBorder.TOP, null, MyColor.text));
        center.add(centerRight, BorderLayout.EAST);
        centerRight.add(spTableDetail, BorderLayout.CENTER);
        table =new MyTable(billControl.toTable());
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String id = table.getValueAt(table.getSelectedRow(), 0).toString();
                tableDetail.setModel(billDetailControl.toTableByBillID(id));
            }
        });
        spTableBills.setViewportView(table);
        tableDetail = new MyTable(billDetailControl.toTable());
        spTableDetail.setViewportView(tableDetail);
        searchBar.getButton().addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                table.setModel(billControl.toTable(billControl.searchAllAttributes(searchBar.getTextField().getText())));
                tableDetail.setModel(billDetailControl.toTable(billDetailControl.searchAllAttributes(searchBar.getTextField().getText())));
            }
        });
        searchBar.getTextField().addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    table.setModel(billControl.toTable(billControl.searchAllAttributes(searchBar.getTextField().getText())));
                    tableDetail.setModel(billDetailControl.toTable(billDetailControl.searchAllAttributes(searchBar.getTextField().getText())));
                }
            }

        });
        Vector<String> list = new Vector<>();
        for (int i = 0; i < table.getColumnCount(); i++)
            list.add("Tìm kiếm theo " + table.getColumnName(i).toLowerCase());
        for (int i = 0; i < tableDetail.getColumnCount(); i++)
            list.add("Tìm kiếm theo" + tableDetail.getColumnName(i).toLowerCase());
        searchBar.getComboBox().setModel(new DefaultComboBoxModel<>(list));
    }

    @Override
    public View newView() {
        return new BillView();
    }
}
