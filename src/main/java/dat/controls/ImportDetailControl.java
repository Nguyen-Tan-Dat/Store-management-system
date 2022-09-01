package dat.controls;

import dat.models.ImportDetail;

import javax.swing.table.DefaultTableModel;

public class ImportDetailControl extends Control {
    public ImportDetailControl() {
        super( new ImportDetail());
    }
    public DefaultTableModel toTableByImportID(String id) {
        return toTable(((ImportDetail)model).readTableByImportID(id));
    }
}
