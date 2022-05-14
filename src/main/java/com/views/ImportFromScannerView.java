package com.views;

import com.controls.*;
import com.models.Model;
import com.utils.*;
import com.views.components.Action;
import com.views.components.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Random;
import java.util.Vector;

public class ImportFromScannerView extends View {
    private final JTable table;
    private final JLabel lbSupplier;
    private final JLabel lbSupplierId;
    private final JLabel lbProductId;
    private final JLabel lbProduct;
    private final JLabel lbTotal;
    private final IndividualProductControl individualProductControl = new IndividualProductControl();
    private final ImportControl importControl = new ImportControl();
    private final ImportDetailControl importDetailControl = new ImportDetailControl();
    private final RFID rfid = new RFID();
    private HashSet<String> list = new HashSet<>();
    private int price = 0;

    public ImportFromScannerView() {
        setLayout(new BorderLayout(0, 0));
        var actionBackground = MyColor.primary;
        var toolBackground = MyColor.dark_primary;
        ActionBar actionBar = new ActionBar(new Action[]{
                new Action("", GoogleMaterialDesignIcons.ARROW_BACK, new Color(104, 46, 206), back()),
                new Action("Nhà cung cấp", GoogleMaterialDesignIcons.PERSON, new Color(104, 46, 206), chooserSupplier()),
                new Action("Sản phẩm", GoogleMaterialDesignIcons.SHOPPING_BASKET, new Color(104, 46, 206), chooserProduct()),
                new Action("Quét", GoogleMaterialDesignIcons.SCANNER, new Color(104, 46, 206), scan()),
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
        lbProductId = new MyLabel("Mã sản phẩm", toolBackground, 200, 40);
        lbProduct = new MyLabel("Sản phẩm", toolBackground, 200, 40);
        lbTotal = new MyLabel("Thành tiền", toolBackground, 200, 40);
        bottom.setPreferredSize(new Dimension(200, 200));
        bottom.add(lbSupplierId);
        bottom.add(lbSupplier);
        bottom.add(lbProductId);
        bottom.add(lbProduct);
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
        return price * list.size();
    }

    private String totalToString() {
        String total = ToMoneyString.format(total());
        lbTotal.setText(total);
        return total;
    }

    @Override
    public View newView() {
        return new ImportFromScannerView();
    }

    private DefaultTableModel toTable() {
        Vector<Vector<String>> body = new Vector<>();
        for (var i : list) {
            Vector<String> row = new Vector<>();
            row.add(i);
            body.add(row);
        }
        Vector<String> header = new Vector<>();
        header.add("Mã thẻ");
        return new DefaultTableModel(body, header);
    }

    private HashSet<String> virtualData() {
        Random random = new Random();
        HashSet<String> ids = new HashSet<>();
        for (int i = 0; i < random.nextInt(10, 20); i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < 24; j++) {
                sb.append(random.nextInt(10));
            }
            ids.add(sb.toString());
        }
        if (ids.size() == 0) ids = null;
        return ids;
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

    private MouseAdapter chooserProduct() {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ChooserView chooserView = new ChooserView(new ProductControl());
                chooserView.getButton().addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        var info = chooserView.getInfo();
                        if (info == null) return;
                        lbProductId.setText(info[0]);
                        lbProduct.setText(info[1]);
                        price = Integer.parseInt(info[2]);
                        lbTotal.setText(totalToString());
                        MainFrame.windowChild.dispose();
                    }
                });
                MainFrame.openChild("Chọn sản phẩm", chooserView, 700, 500);
            }
        };
    }

    private MouseAdapter scan() {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                var ids = virtualData();
//                var ids = rfid.read();
                if (ids == null) {
                    JOptionPane.showMessageDialog(getRootPane(), "Chưa kết nối được với thiết bị");
                    return;
                }
                if (price == 0) {
                    JOptionPane.showMessageDialog(getRootPane(), "Chưa chọn sản phẩm");
                    return;
                }
                for (var id : ids) {
                    Model product = individualProductControl.getObject(id);
                    if (product != null) {
                        JOptionPane.showMessageDialog(getRootPane(), "Thông tin thẻ:" + id + " đã được đưa vào hệ thống trước đó");
                    } else {
                        list.add(id);
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
                } else JOptionPane.showMessageDialog(getRootPane(), "Vui lòng chọn dòng để xóa");
            }
        };
    }

    private MouseAdapter clear() {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                list = new HashSet<>();
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
                if (lbProductId.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(getRootPane(), "Chưa chọn dòng sản phẩm");
                }
                if (table.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(getRootPane(), "Không có sản phẩm trong danh sách nhập");
                    return;
                }
                int confirm = JOptionPane.showConfirmDialog(
                        getRootPane(),
                        "Xác nhận nhập",
                        "Xác nhận",
                        JOptionPane.YES_NO_OPTION
                );
                if (confirm != 0) return;
                importControl.add(new Model(new String[]{MainFrame.getEmployee_id(), lbSupplierId.getText(), TopBar.lbDate.getText(), TopBar.lbTime.getText(), total() + ""}));
                for (String tagID : list) {
                    individualProductControl.add(tagID, new Model(new String[]{lbProductId.getText(), "0"}));
                    importDetailControl.add(tagID, new Model(new String[]{importControl.getNewID() - 1 + "", price + ""}));
                }
                JOptionPane.showMessageDialog(getRootPane(), "Nhập hàng hoàn tất");
                list = new HashSet<>();
                table.setModel(toTable());
                totalToString();
                lbSupplier.setText("");
                lbSupplierId.setText("");
                lbProductId.setText("");
                lbProduct.setText("");
            }
        };
    }
}
