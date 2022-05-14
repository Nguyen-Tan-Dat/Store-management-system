package com.models;

import java.util.Vector;

public class Bill extends Model{
    public Bill() {
        super("Bills");
    }

    @Override
    public Vector<String> columnNames() {
        Vector<String> r=new Vector<>();
        r.add("SHĐ");
        r.add("MNV");
        r.add("MKH");
        r.add("MKM");
        r.add("Ngày lập");
        r.add("Giờ lập");
        r.add("Thành tiền");
        return  r;
    }
}
