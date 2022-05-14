package com.utils;

import com.models.Model;

import java.sql.*;
import java.util.HashMap;

public class Database {
    private Statement statement = null;
    private Connection connection = null;
    private final String nameTable;
    private final Model model;
    private String[] attributes = null;

    public Database(String nameTable, Model model) {
        this.nameTable = nameTable;
        this.model = model;
    }

    public String connect() {
        if (connection == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                return "Haven't connected the library for the driver";
            }
            var database = "Store_Management";
            var user = "root";
            var password = "";
            try {
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + database, user, password);
                statement = connection.createStatement();
            } catch (SQLException e) {
                e.printStackTrace();
                return "Could not connect to database with:" +
                        "\n+ User: " + user +
                        "\n+ Password: " +password+
                        "\n+ Database: "+database;

            }
        }
        return null;
    }

    public boolean execute(String query) {
        try {
            statement.execute(query);
        } catch (SQLException sqlException) {
            System.out.println("Error: " + query);
            return false;
        }
        return true;
    }

    public boolean insertData(String id, String[] infos) {
        StringBuilder query = new StringBuilder("insert into " + nameTable + " values('").append(id).append("'");
        for (String info : infos)
            query.append(",'").append(info).append("'");
        query.append(")");
        return execute(query.toString());
    }

    public HashMap<String, Model> readTable() {
        return readTable("select * from " + nameTable);
    }

    public HashMap<String, Model> readTable(String query) {
        HashMap<String, Model> list = new HashMap<>();
        try {
            if (statement != null) {
                ResultSet resultSet = statement.executeQuery(query);
                int numberOfAttributes = resultSet.getMetaData().getColumnCount() - 1;
                while (resultSet.next()) {
                    String[] temp = new String[numberOfAttributes];
                    for (int i = 0; i < numberOfAttributes; i++)
                        temp[i] = resultSet.getString(i + 2);
                    list.put(resultSet.getString(1), model.getDTO(temp));
                }
            }
        } catch (SQLException sqlException) {
            System.out.println("Error: read table " + nameTable);
            sqlException.printStackTrace();
        }
        return list;
    }

    public void update(String id, byte[] indexAttributes, String[] infos) {
        this.loadAttributes();
        StringBuilder query = new StringBuilder("update " + nameTable + " set ");
        for (byte i = 0; i < indexAttributes.length - 1; i++)
            query.append(this.attributes[indexAttributes[i] + 1]).append("='").append(infos[i]).append("',");
        query.append(this.attributes[indexAttributes[indexAttributes.length - 1] + 1]).append("='").append(infos[infos.length - 1])
                .append("' where ").append(this.attributes[0]).append("=").append(id);
        execute(query.toString());
    }

    public void delete(String id) {
        loadAttributes();
        execute("DELETE FROM " + nameTable + " where " + attributes[0] + "='" + id+"'");
    }

    public int getMaxId() {
        int result = 0;
        loadAttributes();
        try {
            if (statement != null) {
                ResultSet resultSet = statement.executeQuery("SELECT* FROM " + nameTable + " ORDER BY " + attributes[0] + " DESC LIMIT 0, 1");
                resultSet.next();
                try {
                    result = resultSet.getInt(1);
                } catch (Exception ignored) {
                }
            }
        } catch (SQLException sqlException) {
            System.out.println("Error: read table " + nameTable);
            sqlException.printStackTrace();
        }
        return result;
    }

    public void loadAttributes() {
        if (attributes == null) try {
            if (statement != null) {
                ResultSetMetaData rsMetaData = statement.executeQuery("SELECT * FROM " + nameTable).getMetaData();
                attributes = new String[rsMetaData.getColumnCount()];
                for (int i = 1; i <= rsMetaData.getColumnCount(); i++)
                    attributes[i - 1] = (rsMetaData.getColumnName(i));
                //System.out.println(JDBCType.valueOf(rsMetaData.getColumnType(i)).format());
            }
        } catch (SQLException e) {
            System.out.println("ERROR: Not newView attributes of table " + nameTable);
        }
    }
    public String[] getFields(){
        if(attributes==null)loadAttributes();
        return attributes;
    }
}