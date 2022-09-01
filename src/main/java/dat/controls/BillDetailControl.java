package dat.controls;

import dat.models.BillDetail;

import javax.swing.table.DefaultTableModel;

public class BillDetailControl extends Control {
    public BillDetailControl() {
        super( new BillDetail());
    }
    public DefaultTableModel toTableByBillID(String id) {
        return toTable(((BillDetail)model).readTableByBillID(id));
    }
}
