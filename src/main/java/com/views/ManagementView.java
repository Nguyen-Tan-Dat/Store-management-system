package com.views;

import com.controls.Control;
import com.models.Model;
import com.utils.GoogleMaterialDesignIcons;
import com.views.components.Action;
import com.views.components.ActionBar;
import com.views.components.Field;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ManagementView extends SeeView {
    protected final ActionBar actionBar;

    public ManagementView(Control control) {
        super(control);
        actionBar = new ActionBar(new Action[]{
                new Action("Thêm", GoogleMaterialDesignIcons.ADD, new Color(104, 46, 206), add()),
                new Action("Cập nhật", GoogleMaterialDesignIcons.UPDATE, new Color(104, 46, 206), update()),
                new Action("Xóa", GoogleMaterialDesignIcons.DELETE, new Color(190, 45, 45), delete()),
        }, top.getBackground());
        top.add(actionBar, BorderLayout.WEST);
    }

    @Override
    public View newView() {
        return new ManagementView(control);
    }

    private MouseAdapter delete() {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int sl = table.getSelectedRow();
                if (sl == -1) {
                    JOptionPane.showMessageDialog(getRootPane(), "Chọn nhấp chọn dòng sau đó nhấn xóa");
                    return;
                }
                String id = table.getValueAt(sl, 0).toString();
                control.delete(id);
                MainFrame.setView(newView());
            }
        };
    }

    private MouseAdapter update() {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int sl = table.getSelectedRow();
                if (sl == -1) {
                    JOptionPane.showMessageDialog(getRootPane(), "Nhấn chọn dòng sau đó nhấn cập nhật");
                    return;
                }
                String id = table.getValueAt(sl, 0).toString();
                String[] infoOld = new String[table.getColumnCount() - 1];
                for (int i = 1; i < table.getColumnCount(); i++) {
                    infoOld[i-1] = table.getValueAt(sl, i ).toString();
                }
                String[] names = new String[table.getColumnCount() - 1];
                for (int i = 1; i < table.getColumnCount(); i++)
                    names[i - 1] = table.getColumnName(i);
                Field field = new Field(names, infoOld);
                field.setAction(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        var info = field.getInfo();
                        if (info != null) {
                            control.update(id, new Model(info));
                            MainFrame.windowChild.dispose();
                            MainFrame.setView(newView());
                        }
                    }
                });
                MainFrame.openChild("Thông tin cập nhật",field, 350, 500);
            }
        };
    }

    private MouseAdapter add() {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String[] names = new String[table.getColumnCount() - 1];
                for (int i = 1; i < table.getColumnCount(); i++)
                    names[i - 1] = table.getColumnName(i);
                Field field = new Field(names);
                field.setAction(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        var info = field.getInfo();
                        if (info != null) {
                            control.add(new Model(info));
                            MainFrame.windowChild.dispose();
                            MainFrame.setView(newView());
                        }
                    }
                });
                MainFrame.openChild("Thông tin",field, 350, 500);
            }
        };
    }
}
