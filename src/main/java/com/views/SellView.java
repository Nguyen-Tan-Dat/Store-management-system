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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Vector;

public class SellView extends View {
    private final JTable table;
    private final JLabel lbCustomer;
    private final JLabel lbCustomerId;
    private final JLabel lbPromotionID;
    private final JLabel lbPromotion;
    private final JLabel lbDiscount;
    private final JLabel lbTotal;
    private final IndividualProductControl individualProductControl = new IndividualProductControl();
    private final BillControl billControl = new BillControl();
    private final BillDetailControl billDetailControl = new BillDetailControl();
    private final RFID rfid = new RFID();
    private HashMap<String, HashMap<String, Model>> list = new HashMap<>();

    public SellView() {
        setLayout(new BorderLayout(0, 0));
        var actionBackground = MyColor.primary;
        var toolBackground = MyColor.dark_primary;
        ActionBar actionBar = new ActionBar(new Action[]{
                new Action("Khách hàng", GoogleMaterialDesignIcons.PERSON, new Color(104, 46, 206), chooserCustomer()),
                new Action("Khuyến mãi", GoogleMaterialDesignIcons.RECEIPT, new Color(104, 46, 206), chooserPromotion()),
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
        lbCustomerId = new MyLabel("Mã khách hàng", toolBackground, 200, 40);
        lbCustomer = new MyLabel("Khách hàng", toolBackground, 200, 40);
        lbPromotionID = new MyLabel("Mã khuyến mãi", toolBackground, 200, 40);
        lbPromotion = new MyLabel("Khuyến mãi", toolBackground, 200, 40);
        lbDiscount = new MyLabel("Được giảm", toolBackground, 200, 40);
        lbTotal = new MyLabel("Thành tiền", toolBackground, 200, 40);
        bottom.setPreferredSize(new Dimension(200, 200));
        bottom.add(lbCustomerId);
        bottom.add(lbCustomer);
        bottom.add(lbPromotionID);
        bottom.add(lbPromotion);
        bottom.add(lbDiscount);
        bottom.add(lbTotal);
        MyButton btPay = new MyButton("Thanh toán", GoogleMaterialDesignIcons.PAYMENT, 100, 40, actionBackground);
        bottom.add(btPay);
        btPay.setHoverForeground(new Color(0, 169, 23));
        btPay.addMouseListener(pay());
    }

    private double total() {
        double sum = 0;
        for (String name : list.keySet())
            for (var id : list.get(name).keySet())
                sum += Integer.parseInt(list.get(name).get(id).getData()[1]);
        double discount = percent * sum / 100;
        if (sum < min) {
            lbDiscount.setText(ToMoneyString.format(0));
            return sum;
        }
        if (discount > max) {
            lbDiscount.setText(ToMoneyString.format(max));
            return sum - max;
        } else {
            lbDiscount.setText(String.valueOf(ToMoneyString.format(discount)));
            return sum - discount;
        }
    }

    private int percent;
    private int min;
    private int max;

    private String totalToString() {
        String total = ToMoneyString.format(total());
        lbTotal.setText(total);
        return total;
    }

    @Override
    public View newView() {
        return new SellView();
    }

    private record Info(int price, int quantity) {

        public int getPrice() {
            return price;
        }

        public int getQuantity() {
            return quantity;
        }
    }

    private DefaultTableModel toTable() {
        Vector<Vector<String>> body = new Vector<>();
        HashMap<String, Info> data = new HashMap<>();
        for (String name : list.keySet()) {
            int price = 0;
            int quantity = 0;
            for (var id : list.get(name).keySet()) {
                price = Integer.parseInt(list.get(name).get(id).getData()[1]);
                quantity++;
            }
            data.put(name, new Info(price, quantity));
        }
        for (var i : data.keySet()) {
            Vector<String> temp = new Vector<>();
            temp.add(i);
            temp.add(data.get(i).getPrice() + "");
            temp.add(data.get(i).getQuantity() + "");
            temp.add(ToMoneyString.format(data.get(i).getPrice() * data.get(i).getQuantity()));
            body.add(temp);
        }
        Vector<String> header = new Vector<>();
        header.add("Tên sản phẩm");
        header.add("Giá");
        header.add("Số lượng");
        header.add("Tổng tiền");
        return new DefaultTableModel(body, header);
    }

    private HashSet<String> virtualData() {
        Random random = new Random();
        HashSet<String> ids = new HashSet<>();
        for (int i = 0; i < random.nextInt(1, 5); i++) {
            int number = random.nextInt(0, individualProductControl.getList().keySet().size());
            int index = 0;
            for (var k : individualProductControl.getList().keySet())
                if (number == index++) ids.add(k);
        }
        if (ids.size() == 0) ids = null;
        return ids;
    }

    private MouseAdapter chooserCustomer() {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ChooserView chooserView = new ChooserView(new CustomerControl());
                chooserView.getButton().addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        var info = chooserView.getInfo();
                        if (info == null) return;
                        lbCustomerId.setText(info[0]);
                        lbCustomer.setText(info[1]);
                        MainFrame.windowChild.dispose();
                    }
                });
                MainFrame.openChild("Chọn Khách Hàng", chooserView, 700, 500);
            }
        };
    }

    private MouseAdapter chooserPromotion() {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ChooserView chooserView = new ChooserView(new PromotionControl());
                chooserView.getButton().addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        var info = chooserView.getInfo();
                        if (info == null) return;
                        lbPromotionID.setText(info[0]);
                        lbPromotion.setText(info[1]);
                        percent = Integer.parseInt(info[2]);
                        min = Integer.parseInt(info[5]);
                        max = Integer.parseInt(info[6]);
                        MainFrame.windowChild.dispose();
                        totalToString();
                    }
                });
                MainFrame.openChild("Chọn Khuyến mãi", chooserView, 700, 500);

            }
        };
    }

    private MouseAdapter scan() {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                var ids = virtualData();
//                var ids=rfid.read();
                if (ids == null) JOptionPane.showMessageDialog(getRootPane(), "Không thể kết nối tới thiết bị quét");
                else {
                    for (var id : ids) {
                        Model product = individualProductControl.getObject(id);
                        if (product == null) {
                            JOptionPane.showMessageDialog(getRootPane(), "Dữ liệu của thẻ: " + id + " không tồn tại");
                        } else if (product.getData()[2].equals("1")) {
                            JOptionPane.showMessageDialog(getRootPane(), "Sản phẩm có thẻ: " + id + " đã bán rồi");
                        } else {
                            list.computeIfAbsent(product.getData()[0], k -> new HashMap<>());
                            list.get(product.getData()[0]).put(id, product);
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
                } else JOptionPane.showMessageDialog(getRootPane(), "Vui lòng chọn dòng để xóa");
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

    private MouseAdapter pay() {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (lbCustomerId.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(getRootPane(), "Chưa chọn khách hàng");
                    return;
                }
                if (table.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(getRootPane(), "Không có sản phẩm trong danh sách bán");
                    return;
                }
                if (lbCustomer.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(getRootPane(), "Vui lòng chọn Khách hàng!");
                    return;
                }
                if (lbPromotion.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(getRootPane(), "Vui lòng chọn Khuyến mãi!");
                    return;
                }
                int confirm = JOptionPane.showConfirmDialog(
                        getRootPane(),
                        "Xác nhận thực hiện thanh toán",
                        "Xác nhận",
                        JOptionPane.YES_NO_OPTION
                );
                if (confirm != 0) return;
                billControl.add(new Model(new String[]{MainFrame.getEmployee_id(), lbCustomerId.getText(), lbPromotionID.getText(), TopBar.lbDate.getText(), TopBar.lbTime.getText(), total() + ""}));
                for (String name : list.keySet()) {
                    for (var id : list.get(name).keySet()) {
                        billDetailControl.add(id + "", new Model(new String[]{
                                billControl.getNewID() - 1 + "",
                                list.get(name).get(id).getData()[1]
                        }));
                    }
                }
                for (String name : list.keySet())
                    for (var id : list.get(name).keySet()) {
                        individualProductControl.updatePurchase(id + "", "1");
                    }
                JOptionPane.showMessageDialog(getRootPane(), "Thanh toán hoàn tất");
                BillControl.printPDF(
                        billControl.getNewID() - 1 + "",
                        TopBar.lbDate.getText(),
                        TopBar.lbTime.getText(),
                        MainFrame.getEmployee().getData()[0],
                        lbCustomer.getText(),
                        lbPromotion.getText(),
                        table,
                        lbDiscount.getText(),
                        totalToString());
                list = new HashMap<>();
                table.setModel(toTable());
                totalToString();
                lbCustomer.setText("");
                lbCustomerId.setText("");
                lbPromotion.setText("");
                lbPromotionID.setText("");
            }
        };
    }
}
