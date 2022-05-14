package com.utils;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

import java.text.SimpleDateFormat;
import java.util.Date;

public class InputDate extends JDatePickerImpl implements Input {
    public InputDate() {
        super(new JDatePanelImpl(new UtilDateModel()));
    }
    public String getText(){
        Date date = (Date) getModel().getValue();
        SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd");
        return dt1.format(date);
    }

    @Override
    public void setText(String date) {
        String[] data=date.split("-");
        if(data.length!=3)return;
        int y= Integer.parseInt(data[0]);
        int m=Integer.parseInt(data[1]);
        int d=Integer.parseInt(data[2]);
        getModel().setDate(y, m, d);
        getModel().setSelected(true);
    }
}
