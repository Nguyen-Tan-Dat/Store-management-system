package com.views.components;

import com.utils.GoogleMaterialDesignIcons;
import com.utils.MyColor;
import jiconfont.IconCode;
import jiconfont.swing.IconFontSwing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MyButton extends JPanel {
    private final IconCode iconCode;
    private final JLabel content;

    Color foreground = MyColor.white;
    Color background;
    Color hoverBackground = MyColor.light_primary;
    Color hoverForeground=foreground;
    Color onClickBackground = hoverBackground;

    public MyButton(String text, IconCode iconCode, int with, int height,Color background) {
        this.iconCode = iconCode;
        this.background=background;
        content = new JLabel();
        content.setHorizontalAlignment(SwingConstants.CENTER);
        content.setVerticalAlignment(SwingConstants.CENTER);
        IconFontSwing.register(GoogleMaterialDesignIcons.getIconFont());
        Icon icon = IconFontSwing.buildIcon(iconCode, getFont().getSize() * 2);
        content.setIcon(icon);
        content.setText(text);
        setBackground(background);
        setForeground(foreground);
        setPreferredSize(new Dimension(with, height));
        setLayout(new BorderLayout(0,0));
        add(content,BorderLayout.CENTER);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                setBackground(background);
                setForeground(foreground);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                setBackground(onClickBackground);
                setForeground(foreground);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(hoverBackground);
                setForeground(hoverForeground);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(background);
                setForeground(foreground);
            }
        });
    }

    @Override
    public void setForeground(Color fg) {
        if (iconCode == null) return;
        IconFontSwing.register(GoogleMaterialDesignIcons.getIconFont());
        Icon icon = IconFontSwing.buildIcon(iconCode, getFont().getSize() * 2,fg);
        content.setIcon(icon);
        content.setForeground(fg);
    }
    public void setHoverBackground(Color hoverBackground) {
        this.hoverBackground = hoverBackground;
    }

    public void setHoverForeground(Color hoverForeground) {
        this.hoverForeground = hoverForeground;
    }
}
