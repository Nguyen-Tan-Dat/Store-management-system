package dat.views.components;

import dat.utils.Input;
import dat.utils.InputText;
import dat.utils.InputDate;
import dat.utils.MyColor;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;

public class Field extends JPanel {
    private final ArrayList<Input> list = new ArrayList<>();

    public Field(String[] names) {
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        for (var i : names) {
            if (i.toLowerCase().contains("ngày")) {
                InputDate temp=new InputDate();
                temp.setBorder(new TitledBorder(null, i, TitledBorder.LEADING, TitledBorder.TOP, null, getForeground()));
                temp.setPreferredSize(new Dimension(300, 50));
                add(temp);
                list.add(temp);
            } else {
                InputText temp = new InputText();
                temp.setBorder(new TitledBorder(null, i, TitledBorder.LEADING, TitledBorder.TOP, null, getForeground()));
                temp.setPreferredSize(new Dimension(300, 50));
                add(temp);
                list.add(temp);
            }
        }
        ok = new JButton("OK");
        ok.setBackground(MyColor.primary);
        ok.setForeground(MyColor.white);
        ok.setPreferredSize(new Dimension(100, 40));
        add(ok);

    }

    public Field(String[] names, String[] info) {
        this(names);
        for (int i = 0; i < names.length; i++)
            this.list.get(i).setText(info[i]);
    }

    private final JButton ok;

    public String[] getInfo() {
        String[] info = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            info[i] = list.get(i).getText();
            if (info[i].isEmpty()) {
                JOptionPane.showMessageDialog(getRootPane(), "Vui lòng nhập đầy đủ thông tin");
                return null;
            }
        }
        return info;
    }

    public void setAction(MouseAdapter action) {
        ok.addMouseListener(action);
    }
}
