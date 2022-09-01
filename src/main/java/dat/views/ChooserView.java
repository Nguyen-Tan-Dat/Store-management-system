package dat.views;

import dat.controls.Control;
import dat.utils.GoogleMaterialDesignIcons;
import dat.utils.MyColor;
import dat.views.components.MyButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ChooserView extends SeeView{
    protected String[] info;
    protected MyButton button;

    public MyButton getButton() {
        return button;
    }

    public ChooserView(Control control){
        super(control);
        button=new MyButton("Chọn", GoogleMaterialDesignIcons.CHECK,70,30, MyColor.primary);
        JPanel panel=new JPanel(new FlowLayout(FlowLayout.RIGHT,20,5));
        panel.add(button);
        add(panel, BorderLayout.SOUTH);
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int is= getTable().getSelectedRow();
                if(is==-1){
                    JOptionPane.showMessageDialog(getRootPane(),"Nhấp vào dòng cần chọn và nhấn nút Chọn");
                    return;
                }
                info=new String[table.getColumnCount()];
                for (int i=0;i< table.getColumnCount();i++){
                    info[i]=table.getValueAt(is,i).toString();
                }
                MainFrame.windowChild.dispose();
            }
        });
    }
    public String[] getInfo(){
        return info;
    }
}
