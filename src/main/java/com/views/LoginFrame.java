package com.views;

import com.controls.AuthorizationControl;
import com.models.Employee;
import com.models.Model;
import com.utils.GoogleMaterialDesignIcons;
import com.utils.HintTextField;
import com.utils.MyColor;
import jiconfont.swing.IconFontSwing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginFrame extends JFrame {
    private final JTextField phone;
    private final JPasswordField password;
    public LoginFrame(){
        int width=350;
        setTitle("Đăng nhập");
        setSize(new Dimension(width,300));
        setLocationRelativeTo(null);
        setLayout(new FlowLayout(FlowLayout.CENTER,10,10));
        IconFontSwing.register(GoogleMaterialDesignIcons.getIconFont());
        Icon icon = IconFontSwing.buildIcon(GoogleMaterialDesignIcons.ACCOUNT_BOX, 100, Color.white);
        JLabel lbIcon=new JLabel(icon);
        lbIcon.setPreferredSize(new Dimension(width,100));
        getContentPane().setBackground(MyColor.primary);
        add(lbIcon);
        JButton btOK=new JButton("Đăng nhập");
        btOK.setForeground(getContentPane().getBackground());
        setForeground(getContentPane().getForeground());
        btOK.setPreferredSize(new Dimension(100,40));
        phone=new HintTextField("Số điện thoại");
        password=new JPasswordField();
        phone.setPreferredSize(new Dimension(250,26));
        password.setPreferredSize(new Dimension(250,26));
        setBackground(getContentPane().getBackground());
        add(new JLabel(IconFontSwing.buildIcon(GoogleMaterialDesignIcons.PHONE, 30, Color.white)));
        add(phone);
        add(new JLabel(IconFontSwing.buildIcon(GoogleMaterialDesignIcons.VPN_KEY, 30, Color.white)));
        add(password);
        add(btOK);
        btOK.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(!login())JOptionPane.showMessageDialog(getRootPane(),"Thông tin đăng nhập không đúng!");
                else dispose();
            }
        });
    }
    private boolean login(){
        var employees=(new Employee()).login(phone.getText(),password.getText());
        if(employees.size()==0)return false;
        String id="";
        Model employee=null;
        for (var i: employees.keySet()){
            id=i;
            employee= employees.get(i);
        }
        MainFrame.setEmployee(employee);
        MainFrame.setEmployee_id(id);
        AuthorizationControl control=new AuthorizationControl();
        var authorization=control.getList().get(employee.getData()[5]);
        if(authorization==null){
            JOptionPane.showMessageDialog(getRootPane(),"Tài khoản không có quyền đăng nhập vào hệ thống!");
            System.exit(0);
        }
        String[] actives=authorization.getData()[1].split(", ");
        for(var a:actives)
            System.out.println(a);
        new MainFrame(actives).setVisible(true);
        return true;
    }

}
