package dat.controls;

import dat.models.Model;

import javax.swing.table.DefaultTableModel;
import java.util.HashMap;
import java.util.Vector;


public class Control {
    protected final Model model;
    protected HashMap<String,Model> list;

    public void setList(HashMap<String, Model> list) {
        this.list = list;
    }

    protected int newID;

    public Control(Model model) {
        this.model = model;
        readTable();
        newID = model.getMaxId() + 1;
    }
    public Model getObject(String key) {
        return getList().get(key);
    }

    public void readTable() {
        setList(model.read());
    }

    public void add(Model dto) {
        if (model.add(newID + "", dto.getData())) {
            getList().put("" + newID++, model.getDTO(dto.getData()));
        }
    }

    public HashMap<String, Model> getList() {
        return list;
    }

    public int getNewID() {
        return newID;
    }

    public void add(String id, Model info) {
        if (model.add(id, info.getData())) {
            getList().put(id, model.getDTO(info.getData()));
        }
    }
    public  void update(String id,Model info){
        delete(id);
        add(id,info );
    }

    public void delete(String id) {
        model.delete(id);
        getList().remove(id);
    }

    public HashMap<String, Model> searchAllAttributes(String info, HashMap<String, Model> list) {
        HashMap<String, Model> temp = new HashMap<>();
        for (var i : getList().keySet())
            if (list.get(i).toString().contains(info) || (i + "").contains(info))
                temp.put(i, getList().get(i));
        return temp;
    }

    public HashMap<String, Model> searchAllAttributes(String info) {
        return searchAllAttributes(info, getList());
    }

    @Override
    public String toString() {
        if (getList().isEmpty()) return "";
        StringBuilder temp = new StringBuilder();
        for (String i : getList().keySet()) {
            temp.append(i).append(getList().get(i).toString()).append("\n");
        }
        return temp.toString();
    }

    public DefaultTableModel toTable() {
        return toTable(getList());
    }

    public DefaultTableModel toTable(HashMap<String, Model> list) {
        return toTable(list, model.columnNames());
    }

    public DefaultTableModel toTable(HashMap<String, Model> list, Vector<String> header) {
        Vector<Vector<String>> body = new Vector<>();
        for (var id : list.keySet()) {
            Vector<String> row = getList().get(id).toTable();
            row.add(0, id + "");
            body.add(row);
        }
        return new DefaultTableModel(body, header);
    }
    public Vector<String> toComboBox(){
        var names=model.columnNames();
        var result=new Vector<String>();
        names.add(0,"tất cả thông tin");
        for(var i:names){
            result.add("Tìm theo "+i);
        }
        return result;
    }
}
