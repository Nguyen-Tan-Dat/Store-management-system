package com.models;

import java.util.HashMap;
import java.util.Vector;

public class BillDetail extends Model {
    public BillDetail() {
        super("Bill_Details");
    }

    @Override
    public Vector<String> columnNames() {
        Vector<String> names=new Vector<>();
        names.add("Mã thẻ");
        names.add("Sản phẩm");
        names.add("Giá bán");
        return names;
    }

    @Override
    public HashMap<String, Model> read() {
        return super.read("""
                select ip.id, p.name, bd.price
                from products p,
                     individual_products ip,
                     bill_details bd
                where ip.id = bd.individual_product_id
                  and p.id = ip.product_id""");
    }

    public HashMap<String, Model> readTableByBillID(String id) {
        return database.readTable("select ip.id, p.name, bd.price\n" +
                "from products p,\n" +
                "     individual_products ip,\n" +
                "     bill_details bd\n" +
                "where bd.bill_id = '"+id+"'\n" +
                "  and ip.id = bd.individual_product_id\n" +
                "  and p.id = ip.product_id");
    }
}
