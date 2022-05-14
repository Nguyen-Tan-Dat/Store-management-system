package com.models;

import java.util.Vector;

public class Product extends Model{

    public Product() {
        super("Products");
    }

    @Override
    public Vector<String> columnNames() {
        Vector<String> r=new Vector<>();
        r.add("MSP");
        r.add("Tên sản phẩm");
        r.add("Giá");
        return r;
    }
}
