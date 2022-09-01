package dat.views;

import dat.controls.ImportControl;
import dat.controls.ImportDetailControl;
import dat.controls.Control;
import dat.utils.MyColor;
import dat.utils.MyTable;
import dat.views.components.SearchBar;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

public class ImportView extends View {
    private final JTable table;
    private final JTable tableDetail;
    private final Control billControl = new ImportControl();
    private final ImportDetailControl billDetailControl = new ImportDetailControl();

    public ImportView() {
        setLayout(new BorderLayout(0, 0));
        SearchBar searchBar = new SearchBar(new Color(41, 141, 217));
        add(searchBar, BorderLayout.NORTH);
        JPanel center = new JPanel();
        add(center, BorderLayout.CENTER);
        center.setLayout(new BorderLayout(0, 0));
        JScrollPane spTableDetail = new JScrollPane();
        JScrollPane spTableImports = new JScrollPane();
        JPanel centerLeft = new JPanel();
        centerLeft.setLayout(new BorderLayout(0, 0));
        centerLeft.setBorder(new TitledBorder(null, "Hóa đơn nhập", TitledBorder.LEADING, TitledBorder.TOP, null, MyColor.text));
        center.add(centerLeft, BorderLayout.CENTER);
        centerLeft.add(spTableImports, BorderLayout.CENTER);
        JPanel centerRight = new JPanel();
        centerRight.setLayout(new BorderLayout(0, 0));
        centerRight.setBorder(new TitledBorder(null, "Chi tiết phiếu nhập", TitledBorder.LEADING, TitledBorder.TOP, null, MyColor.text));
        center.add(centerRight, BorderLayout.EAST);
        centerRight.add(spTableDetail, BorderLayout.CENTER);
        table =new MyTable(billControl.toTable());
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String id = table.getValueAt(table.getSelectedRow(), 0).toString();
                tableDetail.setModel(billDetailControl.toTableByImportID(id));
            }
        });
        spTableImports.setViewportView(table);
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
            list.add("Tìm kiếm theo " + tableDetail.getColumnName(i).toLowerCase());
        searchBar.getComboBox().setModel(new DefaultComboBoxModel<>(list));
    }

    @Override
    public View newView() {
        return new ImportView();
    }
}
