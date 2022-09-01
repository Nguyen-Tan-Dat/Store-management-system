package dat.models;

import java.util.Vector;

public class Authorization extends Model{
    public Authorization() {
        super("Authorizations");
    }

    @Override
    public Vector<String> columnNames() {
        Vector<String> r=new Vector<>();
        r.add("MQ");
        r.add("Quyền");
        r.add("Chi tiết quyền");
        return r;
    }
}
