package com.views;

import com.controls.ImportControl;
import com.controls.ImportDetailControl;
import com.controls.IndividualProductControl;
import com.controls.SupplierControl;
import com.models.Model;
import com.utils.*;
import com.views.components.*;
import com.views.components.Action;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Vector;

public class ImportFromExcelView extends View {
    private final JTable table;
    private final JLabel lbSupplier;
    private final JLabel lbSupplierId;
    private final JLabel lbTotal;
    private final IndividualProductControl individualProductControl = new IndividualProductControl();
    private final ImportControl importControl = new ImportControl();
    private final ImportDetailControl importDetailControl = new ImportDetailControl();
    private HashMap<String, HashSet<String[]>> list = new HashMap<>();
    private final Excel excel = new Excel();

    public ImportFromExcelView() {
        setLayout(new BorderLayout(0, 0));
        var actionBackground = MyColor.primary;
        var toolBackground = MyColor.dark_primary;
        ActionBar actionBar = new ActionBar(new Action[]{
                new Action("", GoogleMaterialDesignIcons.ARROW_BACK, new Color(104, 46, 206), back()),
                new Action("Nhà cung cấp", GoogleMaterialDesignIcons.PERSON, new Color(104, 46, 206), chooserSupplier()),
                new Action("Đọc Excel", GoogleMaterialDesignIcons.DESCRIPTION, new Color(104, 46, 206), read()),
                new Action("Xóa", GoogleMaterialDesignIcons.DELETE, new Color(190, 45, 45), delete()),
                new Action("Làm trống", GoogleMaterialDesignIcons.LAYERS_CLEAR, new Color(190, 45, 45), clear()),
        }, actionBackground);
        JScrollPane spTable = new JScrollPane();
        add(spTable, BorderLayout.CENTER);
        add(actionBar, BorderLayout.NORTH);
        table = new MyTable(toTable());
        spTable.setViewportView(table);
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER));
        add(bottom, BorderLayout.EAST);
        bottom.setBackground(MyColor.white);
        lbSupplierId = new MyLabel("Mã nhà cung cấp", toolBackground, 200, 40);
        lbSupplier = new MyLabel("Nhà cung cấp", toolBackground, 200, 40);
        lbTotal = new MyLabel("Thành tiền", toolBackground, 200, 40);
        bottom.setPreferredSize(new Dimension(200, 200));
        bottom.add(lbSupplierId);
        bottom.add(lbSupplier);
        bottom.add(lbTotal);
        MyButton btPay = new MyButton("Nhập hàng", GoogleMaterialDesignIcons.PAYMENT, 100, 40, actionBackground);
        bottom.add(btPay);
        btPay.setHoverForeground(new Color(0, 169, 23));
        btPay.addMouseListener(inport());
    }

    private MouseAdapter back() {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                MainFrame.setView(new ImportProductsView());
            }
        };
    }


    private int total() {
        int result = 0;
        for (var i : list.keySet()) {
            for (var j : list.get(i)) {
                result += Integer.parseInt(j[1]);
            }
        }
        return result;
    }

    private void totalToString() {
        lbSupplier.setText("");
        lbSupplierId.setText("");
        String total = ToMoneyString.format(total());
        lbTotal.setText(total);
    }

    @Override
    public View newView() {
        return new ImportFromScannerView();
    }

    private DefaultTableModel toTable() {
        Vector<Vector<String>> body = new Vector<>();
        for (var i : list.keySet()) {
            for (var j : list.get(i)) {
                Vector<String> row = new Vector<>();
                row.add(i);
                row.add(j[0]);
                row.add(j[1]);
                body.add(row);
            }
        }
        Vector<String> header = new Vector<>();
        header.add("Mã sản phẩm");
        header.add("Mã thẻ");
        header.add("Giá nhập");
        return new DefaultTableModel(body, header);
    }

    private MouseAdapter chooserSupplier() {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ChooserView chooserView = new ChooserView(new SupplierControl());
                chooserView.getButton().addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        var info = chooserView.getInfo();
                        if (info == null) return;
                        lbSupplier.setText(info[1]);
                        lbSupplierId.setText(info[0]);
                        MainFrame.windowChild.dispose();
                    }
                });
                MainFrame.openChild("Chọn nhà cung cấp", chooserView, 700, 500);
            }
        };
    }

    private MouseAdapter read() {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                var temp = excel.read(getRootPane());
                for (var i : temp.keySet()) {
                    for (var j : temp.get(i)) {
                        if (individualProductControl.getList().get(j[0]) == null){
                            list.computeIfAbsent(i, k -> new HashSet<>());
                            list.get(i).add(j);
                        }
                        else{
                            JOptionPane.showMessageDialog(getRootPane(),"Không thể nhập dữ liệu từ tệp đã nhập rồi");
                            list=new HashMap<>();
                            return;
                        }
                    }
                }
                table.setModel(toTable());
                totalToString();
            }
        };
    }

    private MouseAdapter delete() {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (table.getSelectedRow() != -1) {
                    String name = table.getValueAt(table.getSelectedRow(), 0).toString();
                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                    for (int i = 0; i < model.getRowCount(); i++)
                        if (model.getValueAt(i, 0).equals(name)) {
                            model.removeRow(i);
                            list.remove(name);
                            break;
                        }
                    totalToString();
                } else JOptionPane.showMessageDialog(getRootPane(), "Chọn dòng sau nhấn xóa");
            }
        };
    }

    private MouseAdapter clear() {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                list = new HashMap<>();
                table.setModel(toTable());
                totalToString();
            }
        };
    }

    private MouseAdapter inport() {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (lbSupplierId.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(getRootPane(), "Chưa chọn nhà cung cấp");
                    return;
                }
                if (table.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(getRootPane(), "Không có sản phẩm trong danh sách nhập");
                    return;
                }
                int confirm = JOptionPane.showConfirmDialog(
                        getRootPane(),
                        "Xác nhận nhập hàng",
                        "Xác nhập",
                        JOptionPane.YES_NO_OPTION
                );
                if (confirm != 0) return;
                importControl.add(new Model(new String[]{lbSupplierId.getText(),MainFrame.getEmployee_id(), TopBar.lbDate.getText(),TopBar.lbTime.getText(),total()+""}));
                for (var i : list.keySet()) {
                    for (var j : list.get(i)) {
                        if (individualProductControl.getList().get(j[0]) == null){
                            individualProductControl.add(j[0],new Model(new String[]{i,"0"}));
                            importDetailControl.add(j[0],new Model(new String[]{importControl.getNewID()-1+"",j[1]}));
                        }
                    }
                }
                list=new HashMap<>();
                totalToString();
                JOptionPane.showMessageDialog(getRootPane(), "Nhập hàng hoàn tất");
                table.setModel(toTable());
                totalToString();
            }
        };
    }
}
