package dat.models;

import java.util.Vector;

public class Promotion extends Model{

    public Promotion() {
        super("Promotions");
    }

    @Override
    public Vector<String> columnNames() {
        Vector<String >r=new Vector<>();
        r.add("MKM");
        r.add("Khuyến mãi");
        r.add("Phần trăm");
        r.add("Ngày mở");
        r.add("Ngày đóng");
        r.add("Mức áp dụng");
        r.add("Mức tối đa");
        return r;
    }
}
