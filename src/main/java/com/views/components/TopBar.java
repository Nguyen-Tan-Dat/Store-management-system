package com.views.components;

import com.utils.GoogleMaterialDesignIcons;
import com.utils.MyColor;
import jiconfont.swing.IconFontSwing;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class TopBar extends JPanel {
    private final JLabel lbAccount;
    public static JLabel lbTime;
    public static JLabel lbDate;
    private final JLabel lbFunction;

    public TopBar() {
        var foreground = MyColor.dark_primary;
        var background = MyColor.light_gray;
        setLayout(new BorderLayout(10, 10));
        setBackground(background);
        setBorder(new MatteBorder(0, 0, 4, 0, new Color(background.getRed(), background.getGreen() + 10, background.getBlue() + 10)));
        IconFontSwing.register(GoogleMaterialDesignIcons.getIconFont());
        lbAccount = new JLabel(IconFontSwing.buildIcon(GoogleMaterialDesignIcons.ACCOUNT_BOX, 24, foreground));
        lbTime = new JLabel(IconFontSwing.buildIcon(GoogleMaterialDesignIcons.ACCESS_TIME, 24, foreground));
        lbDate = new JLabel(IconFontSwing.buildIcon(GoogleMaterialDesignIcons.DATE_RANGE, 24, foreground));
        lbAccount.setForeground(foreground);
        lbDate.setForeground(foreground);
        lbTime.setForeground(foreground);
        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        left.setBackground(background);
        left.add(lbAccount);
        right.setBackground(background);
        right.add(lbTime);
        right.add(lbDate);
        Font font = new Font("Roboto", Font.PLAIN, 16);
        lbFunction = new JLabel();
        lbFunction.setVerticalAlignment(0);
        lbFunction.setHorizontalAlignment(0);
        lbFunction.setForeground(foreground);
        lbFunction.setFont(font);
        lbAccount.setFont(font);
        lbDate.setFont(font);
        lbTime.setFont(font);
        add(lbFunction, BorderLayout.CENTER);
        add(left, BorderLayout.WEST);
        add(right, BorderLayout.EAST);
        new Thread(() -> {
            while (true) {
                lbDate.setText(String.valueOf(LocalDate.now()));
                lbTime.setText(String.valueOf(LocalTime.now()).substring(0, 8));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    public void setAccount(String name) {
        lbAccount.setText(name);
    }

    public void setFunction(String name) {
        lbFunction.setText(name);
    }
}
