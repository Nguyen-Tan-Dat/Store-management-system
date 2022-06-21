import java.awt.Component;

import javax.swing.*;
import javax.swing.border.*;

import java.awt.event.*;

public class JCheckBoxList extends JList<JCheckBox> {
    protected static Border noFocusBorder = new EmptyBorder(1, 1, 1, 1);

    public JCheckBoxList(ListModel<JCheckBox> model) {
        setCellRenderer(new CellRenderer());
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                int index = locationToIndex(e.getPoint());
                if (index != -1) {
                    JCheckBox checkbox = getModel().getElementAt(index);
                    checkbox.setSelected(!checkbox.isSelected());
                    repaint();
                }
            }
        });
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setModel(model);
    }

    protected class CellRenderer implements ListCellRenderer<JCheckBox> {
        public Component getListCellRendererComponent(JList<? extends JCheckBox> list, JCheckBox value, int index, boolean isSelected, boolean cellHasFocus) {
            value.setBackground(isSelected ? getSelectionBackground() : getBackground());
            value.setForeground(isSelected ? getSelectionForeground() : getForeground());
            value.setEnabled(isEnabled());
            value.setFont(getFont());
            value.setFocusPainted(false);
            value.setBorderPainted(true);
            value.setBorder(isSelected ? UIManager.getBorder("List.focusCellHighlightBorder") : noFocusBorder);
            return value;
        }
    }

    public static void main(String[] args) {
        DefaultListModel<JCheckBox> model = new DefaultListModel<>();
        JCheckBoxList checkBoxList = new JCheckBoxList(model);
        model.addElement(new JCheckBox("Checkbox1"));
        model.addElement(new JCheckBox("Checkbox2"));
        model.addElement(new JCheckBox("Checkbox3"));
        JFrame frame = new JFrame();
        frame.add(checkBoxList);
        frame.setSize(100, 100);
        frame.setVisible(true);
    }
}