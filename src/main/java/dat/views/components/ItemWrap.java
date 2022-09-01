package dat.views.components;

import dat.utils.GoogleMaterialDesignIcons;
import jiconfont.IconCode;
import jiconfont.swing.IconFontSwing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ItemWrap extends JPanel {
    public ItemWrap(IconCode iconCode, String text, Color background,Color foreground,Color hoverBackground) {
        setBackground(background);
        setForeground(foreground);
        setPreferredSize(new Dimension(150, 80));
        setLayout(new BorderLayout(0, 0));
        IconFontSwing.register(GoogleMaterialDesignIcons.getIconFont());
        Icon icon = IconFontSwing.buildIcon(iconCode, getFont().getSize() * 3, getForeground());
        JLabel lbIcon = new JLabel(icon);
        lbIcon.setPreferredSize(new Dimension(100, 50));
        lbIcon.setHorizontalAlignment(SwingConstants.CENTER);
        lbIcon.setVerticalAlignment(SwingConstants.BOTTOM);
        add(lbIcon, BorderLayout.CENTER);
        JLabel lbText = new JLabel(text);
        lbText.setForeground(foreground);
        lbText.setPreferredSize(new Dimension(100, 30));
        lbText.setHorizontalAlignment(SwingConstants.CENTER);
        lbText.setVerticalAlignment(SwingConstants.TOP);
        add(lbText, BorderLayout.SOUTH);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                setBackground(background);
                setForeground(foreground);
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(hoverBackground);
                setForeground(foreground);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(background);
                setForeground(foreground);
            }
        });
    }
}
