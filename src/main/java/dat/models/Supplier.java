package dat.models;

import java.util.Vector;

public class Supplier extends Model{
    public Supplier() {
        super("Suppliers");
    }
    public Vector<String> columnNames() {
        Vector<String> result = new Vector<>();
        result.add("MNCC");
        result.add("Tên");
        result.add("Địa chỉ");
        result.add("Số điện thoại");
        return result;
    }
}
