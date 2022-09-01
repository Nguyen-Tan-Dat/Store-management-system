package dat.models;

import java.util.HashMap;
import java.util.Vector;

public class ImportDetail extends Model {
    public ImportDetail() {
        super("Import_Details");
    }

    @Override
    public Vector<String> columnNames() {
        Vector<String> names=new Vector<>();
        names.add("Mã thẻ");
        names.add("Sản phẩm");
        names.add("Giá nhập");
        return names;
    }

    @Override
    public HashMap<String, Model> read() {
        return super.read("select ip.id, p.name, bd.price " +
                "from products p, " +
                "     individual_products ip, " +
                "     import_details bd " +
                "where ip.id = bd.individual_product_id " +
                "  and p.id = ip.product_id");
    }

    public HashMap<String, Model> readTableByImportID(String id) {
        return database.readTable("select ip.id, p.name, bd.price\n" +
                "from products p,\n" +
                "     individual_products ip,\n" +
                "     import_details bd\n" +
                "where bd.import_id = '"+id+"'\n" +
                "  and ip.id = bd.individual_product_id\n" +
                "  and p.id = ip.product_id");
    }
}
