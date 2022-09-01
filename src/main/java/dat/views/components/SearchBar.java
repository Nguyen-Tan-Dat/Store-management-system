package dat.views.components;

import dat.utils.GoogleMaterialDesignIcons;
import dat.utils.HintTextField;

import javax.swing.*;
import java.awt.*;

public class SearchBar extends JPanel {
    private final JComboBox<String> comboBox;
    private final JTextField textField;
    private final MyButton button;

    public SearchBar(Color background) {
        setBackground(background);
        setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        textField = new HintTextField(" Nhập để tìm kiếm");
        comboBox = new JComboBox<>(new String[]{});
        comboBox.setBackground(getBackground());
        comboBox.setForeground(Color.white);
        add(comboBox);
        add(textField);
        textField.setPreferredSize(new Dimension(200, 26));
        button = new MyButton("", GoogleMaterialDesignIcons.SEARCH, 30, 30, getBackground());
        add(button);
    }

    public JComboBox<String> getComboBox() {
        return comboBox;
    }

    public JTextField getTextField() {
        return textField;
    }

    public MyButton getButton() {
        return button;
    }
}
