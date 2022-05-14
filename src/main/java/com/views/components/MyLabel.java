package com.views.components;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class MyLabel extends JLabel {
    public MyLabel(String title,Color textColor,int width,int height){
        super("");
        setHorizontalAlignment(SwingConstants.LEFT);
        setVerticalAlignment(SwingConstants.CENTER);
        setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, textColor, textColor), title, TitledBorder.LEFT, TitledBorder.TOP, null, textColor));
        setPreferredSize(new Dimension(width,height));
        setForeground(textColor);
    }
}
