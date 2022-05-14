package com.models;

import com.utils.HashPass;

import java.util.HashMap;
import java.util.Vector;

public class Employee extends Model {
    public Employee() {
        super("Employees");
    }

    public HashMap<String, Model> login(String phone, String pass) {
        var list = database.readTable("select * from employees where phone_number='" + phone + "'");
        HashMap<String, Model> result = new HashMap<>();
        for (var i : list.keySet()) {
            String hash = list.get(i).getData()[4];
            if (HashPass.isPassHash(pass, hash)) {
                result.put(i, list.get(i));
            }
        }
        return result;
    }

    public Vector<String> columnNames() {
        Vector<String> result = new Vector<>();
        result.add("MNV");
        result.add("Họ và Tên");
        result.add("Địa chỉ");
        result.add("Ngày sinh");
        result.add("Số điện thoại");
        result.add("Mật khẩu");
        result.add("MQ");
        return result;
    }
}