package dat.views.components;

import dat.utils.GoogleMaterialDesignIcons;
import dat.utils.MyColor;
import dat.views.MainFrame;
import jiconfont.IconCode;
import jiconfont.swing.IconFontSwing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Item extends JPanel {
    public boolean active = false;
    private final IconCode iconCode;
    private final JLabel content;

    public Item(String text, IconCode iconCode, int with, int height) {
        this.iconCode = iconCode;
        content = new JLabel();
        content.setText(text);
        content.setHorizontalAlignment(SwingConstants.LEFT);
        var foreground = MyColor.text;
        var background = MyColor.white;
        var hoverBackground = MyColor.light_primary;
        var activeBackground = MyColor.primary;
        var activeForeground = MyColor.white;
        var onClickBackground = activeBackground;
        setBackground(background);
        setForeground(foreground);
        setPreferredSize(new Dimension(with, height));
        setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));
        add(content);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                for (var i : FunctionBar.items.keySet()) {
                    if (FunctionBar.items.get(i).active) {
                        FunctionBar.items.get(i).active = false;
                        FunctionBar.items.get(i).setBackground(background);
                        FunctionBar.items.get(i).setForeground(foreground);
                        break;
                    }
                }
                active = true;
                setBackground(activeBackground);
                setForeground(background);
                try {
                    MainFrame.setView(text);
                } catch (Exception ignored) {
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                setBackground(activeBackground);
                setForeground(background);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                setBackground(onClickBackground);
                setForeground(foreground);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(hoverBackground);
                setForeground(foreground);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (!active) {
                    setBackground(background);
                    setForeground(foreground);
                } else {
                    setBackground(activeBackground);
                    setForeground(activeForeground);
                }
            }
        });
    }

    @Override
    public void setForeground(Color fg) {
        if (iconCode == null) return;
        IconFontSwing.register(GoogleMaterialDesignIcons.getIconFont());
        Icon icon = IconFontSwing.buildIcon(iconCode, getFont().getSize() * 2, fg);
        content.setIcon(icon);
        content.setForeground(fg);
    }
}
