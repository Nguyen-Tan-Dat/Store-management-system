package dat.views;

import dat.controls.AuthorizationControl;
import dat.models.Employee;
import dat.models.Model;
import dat.utils.GoogleMaterialDesignIcons;
import dat.utils.HintTextField;
import dat.utils.MyColor;
import jiconfont.swing.IconFontSwing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

public class LoginFrame extends JFrame {
    private final JTextField phone;
    private final JPasswordField password;
    private JCheckBox checkNhoMatKhau;

    public LoginFrame() {
        int width = 400;
        setTitle("Đăng nhập");
        setSize(new Dimension(width, 400));
        setLocationRelativeTo(null);
        getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        IconFontSwing.register(GoogleMaterialDesignIcons.getIconFont());
        Icon icon = IconFontSwing.buildIcon(GoogleMaterialDesignIcons.ACCOUNT_BOX, 100, Color.white);
        JLabel lbIcon = new JLabel(icon);
        lbIcon.setPreferredSize(new Dimension(width, 100));
        getContentPane().setBackground(MyColor.primary);
        getContentPane().add(lbIcon);
        JButton btOK = new JButton("Đăng nhập");
        btOK.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btOK.setForeground(getContentPane().getBackground());
        btOK.setFocusPainted(false);
        setForeground(getContentPane().getForeground());
        btOK.setPreferredSize(new Dimension(100, 40));
        setBackground(getContentPane().getBackground());
        btOK.setBorder(new EmptyBorder(0, 0, 0, 0));
        phone = new HintTextField("Số điện thoại");
        phone.setBackground(getBackground());
        phone.setForeground(Color.WHITE);
        phone.setBorder(new MatteBorder(0, 0, 1, 0, (Color) Color.WHITE));
        password = new JPasswordField();
        phone.setPreferredSize(new Dimension(300, 26));
        password.setPreferredSize(new Dimension(300, 26));
        password.setBackground(getBackground());
        password.setForeground(Color.WHITE);
        password.setBorder(new MatteBorder(0, 0, 1, 0, (Color) Color.WHITE));
        getContentPane().add(new JLabel(IconFontSwing.buildIcon(GoogleMaterialDesignIcons.PHONE, 30, Color.white)));
        getContentPane().add(phone);
        getContentPane().add(new JLabel(IconFontSwing.buildIcon(GoogleMaterialDesignIcons.VPN_KEY, 30, Color.white)));
        getContentPane().add(password);
        
        checkNhoMatKhau = new JCheckBox("Nhớ thông tin đăng nhập");
        checkNhoMatKhau.setFocusPainted(false);
        checkNhoMatKhau.setBackground(btOK.getForeground());
        checkNhoMatKhau.setForeground(btOK.getBackground());
        checkNhoMatKhau.setPreferredSize(new Dimension(300,30));
        getContentPane().add(checkNhoMatKhau);
        getContentPane().add(btOK);
        phone.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    login();
                }
            }
        });
        password.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    login();
                }
            }
        });
        btOK.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!login()) JOptionPane.showMessageDialog(getRootPane(), "Thông tin đăng nhập không đúng!");
                else dispose();
            }
        });
    }

    private boolean login() {
        if(phone.getText().isEmpty()||password.getText().isEmpty()){
            JOptionPane.showMessageDialog(getRootPane(), "Vui lòng nhập đầy đủ thông tin đăng nhập!");
            return false;
        }
        var employees = (new Employee()).login(phone.getText(), password.getText());
        if (employees.size() == 0) return false;
        String id = "";
        Model employee = null;
        for (var i : employees.keySet()) {
            id = i;
            employee = employees.get(i);
        }
        MainFrame.setEmployee(employee);
        MainFrame.setEmployee_id(id);
        AuthorizationControl control = new AuthorizationControl();
        if (employee.getData()[5].equals("1")) {
            new MainFrame().setVisible(true);
            return true;
        }
        var authorization = control.getList().get(employee.getData()[5]);
        if (authorization == null) {
            JOptionPane.showMessageDialog(getRootPane(), "Tài khoản không có quyền đăng nhập vào hệ thống!");
            System.exit(0);
        }
        String[] actives = authorization.getData()[1].split(", ");
        new MainFrame(actives).setVisible(true);
        return true;
    }

}
