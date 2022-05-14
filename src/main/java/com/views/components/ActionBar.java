package com.views.components;

import javax.swing.*;
import java.awt.*;

public class ActionBar extends JPanel {
    public ActionBar(Action[] actions,Color background){
        setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
        setBackground(background);
        for(var i:actions){
            var b=new MyButton(i.name(), i.iconCode(),120,30,getBackground());
            b.addMouseListener(i.action());
            b.setHoverForeground(i.hoverForeground());
            add(b);
        }
    }
}
