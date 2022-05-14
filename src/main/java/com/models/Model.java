package com.models;

import com.utils.Database;

import java.util.Collections;
import java.util.HashMap;
import java.util.Vector;

public class Model {
    protected String[] data;

    public Model(String[] data) {
        this.data = data;
    }

    public String[] getData() {
        return data;
    }

    public Model getDTO(String[] info) {
        return new Model(info);
    }

    public Vector<String> columnNames() {
        Vector<String> result = new Vector<>();
        for (var name : database.getFields()) {
            String temp = name.replace("_", " ");
            result.add((temp.charAt(0) + "").toUpperCase() + temp.substring(1));
        }
        return result;
    }

    public Vector<String> toTable() {
        Vector<String> row = new Vector<>();
        Collections.addAll(row, getData());
        return row;
    }

    public String toString() {
        StringBuilder temp = new StringBuilder();
        for (String s : data)
            temp.append("|").append(s);
        return temp.toString();
    }

    protected Database database;

    public Model(String tableName) {
        database = new Database(tableName, this);
        database.connect();
    }

    public HashMap<String, Model> read() {
        return database.readTable();
    }

    public HashMap<String, Model> read(String query) {
        return database.readTable(query);
    }

    public boolean add(String id, String[] info) {
        return database.insertData(id + "", info);
    }

    public void delete(String id) {
        database.delete(id);
    }

    public int getMaxId() {
        return database.getMaxId();
    }
}
