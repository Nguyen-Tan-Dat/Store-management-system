package com.models;

import java.util.Vector;

public class Import extends Model{
    public Import() {
        super("Imports");
    }

    @Override
    public Vector<String> columnNames() {
        Vector<String>r=new Vector<>();
        r.add("SHĐ");
        r.add("MNV");
        r.add("MNCC");
        r.add("Ngày lập");
        r.add("Giờ lập");
        r.add("Thành tiền");
        return  r;
    }
}
