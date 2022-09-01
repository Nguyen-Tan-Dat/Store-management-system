package dat.utils;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;

public class CommonUI {
    public CommonUI() {
        UIManager.put("Table.font", new Font("Tahome", Font.PLAIN, 14));
        UIManager.put("Table.focus", Color.red);
        UIManager.put("Viewport.background", MyColor.panel);
        UIManager.put("TableHeader.foreground", MyColor.text);
        UIManager.put("TableHeader.background", MyColor.button);
        UIManager.put("TableHeader.font", new Font("Tahome", Font.PLAIN, 15));
        UIManager.put("ComboBox.foreground", MyColor.text);
        UIManager.put("ComboBox.background", MyColor.button);
        UIManager.put("Table.foreground", MyColor.text);
        UIManager.put("Table.background", MyColor.panel);
        UIManager.put("Label.foreground", MyColor.text);
        UIManager.put("TextField.background", MyColor.button);
        UIManager.put("TextField.foreground", MyColor.text);
        UIManager.put("Panel.background", MyColor.panel);
        UIManager.put("MyButton.background", MyColor.button);
        UIManager.put("MyButton.foreground", MyColor.text);
        UIDefaults defaults = UIManager.getLookAndFeelDefaults();
        defaults.put("MyButton.focus", new ColorUIResource(new Color(0, 0, 0, 0)));
        UIManager.put("OptionPane.background", MyColor.panel);
        UIManager.put("OptionPane.foreground", MyColor.text);
        UIManager.put("OptionPane.messageForeground", MyColor.text);
    }
}
