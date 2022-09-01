package dat.models;

import dat.utils.ToMoneyString;

import java.util.HashMap;
import java.util.Vector;

public class IndividualProduct extends Model {
    public IndividualProduct() {
        super("Individual_Products");
    }
    @Override
    public Vector<String> toTable() {
        Vector<String> row = super.toTable();
        row.set(1, ToMoneyString.format(Integer.parseInt(row.get(1))));
        return row;
    }

    @Override
    public Vector<String> columnNames() {
        Vector<String> h=new Vector<>();
        h.add("Mã thẻ");
        h.add("Sản phấm");
        h.add("Giá");
        h.add("Đã bán");
        return h;
    }

    public HashMap<String, Model> read() {
        return database.readTable("""
                SELECT i_p.id, p.name, p.price, i_p.is_purchased
                FROM Individual_Products i_p, Products p
                WHERE i_p.product_id = p.id
                """);
    }
    public void updatePurchase(String id, String is_purchased) {
        database.execute("UPDATE Individual_Products SET is_purchased='" + is_purchased + "'" +
                "WHERE id='" + id + "'");
    }

}
