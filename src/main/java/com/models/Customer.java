package com.models;

import java.util.Vector;

public class Customer extends Model{
    public Customer() {
        super("Customers");
    }
    public Vector<String> columnNames() {
        Vector<String> result = new Vector<>();
        result.add("MKH");
        result.add("Họ và Tên");
        result.add("Địa chỉ");
        result.add("Ngày sinh");
        result.add("Số điện thoại");
        return result;
    }
}
