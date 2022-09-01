package dat.views;

import dat.controls.Control;
import dat.utils.MyColor;
import dat.utils.MyTable;
import dat.views.components.SearchBar;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SeeView extends View {
    protected final JTable table;
    protected final Control control;
    protected final SearchBar searchBar;
    protected JPanel top;

    public JTable getTable() {
        return table;
    }
    public SeeView(Control control) {
        this.control=control;
        var actionBackground = MyColor.primary;
        setLayout(new BorderLayout(0, 0));
        top = new JPanel(new BorderLayout());
        top.setBackground(actionBackground);
        searchBar = new SearchBar(top.getBackground());
        searchBar.getComboBox().setModel(new DefaultComboBoxModel<>(this.control.toComboBox()));
        searchBar.getButton().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                search();
            }
        });
        searchBar.getTextField().addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    search();
                }
            }
        });
        top.add(searchBar, BorderLayout.EAST);
        JScrollPane spTable = new JScrollPane();
        table = new MyTable(this.control.toTable());
        spTable.setViewportView(table);
        add(spTable, BorderLayout.CENTER);
        add(top, BorderLayout.NORTH);
    }
    protected void search(){
        int sl = searchBar.getComboBox().getSelectedIndex();
        table.setModel(control.toTable());
        String input=searchBar.getTextField().getText().toLowerCase();
        if (sl > 0) {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            int modelLengh = model.getRowCount();
            int count = 0;
            for (int i = 0; i < modelLengh; i++) {
                String info = model.getValueAt(i-count, sl -1 ).toString().toLowerCase();
                if (!info.contains(input)){
                    model.removeRow(i-count);
                    count++;
                }
                else System.out.println(i);
            }
        } else {
            table.setModel(control.toTable(control.searchAllAttributes(searchBar.getTextField().getText())));
        }
    }
    @Override
    public View newView() {
        return new SeeView(control);
    }
}
