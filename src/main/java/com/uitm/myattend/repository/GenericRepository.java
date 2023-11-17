package com.uitm.myattend.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

@Repository
public class GenericRepository{

    private final JdbcTemplate db;

    public GenericRepository(JdbcTemplate db) {
        this.db = db;
    }
    public ArrayList<HashMap<String, String>> select(String table, String [] field) {
        return select(table, field, null, null);
    }

    public ArrayList<HashMap<String, String>> select(String table, String [] field, String cond, String [] condfield) {
       ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
        try {
            String query = buildSelectQuery(table, field, cond);

            System.out.println("Query: " + query);
            if(cond != null) {
                db.query(query, new PreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps) throws SQLException {
                        for(int i=0; i< condfield.length; i++) {
                            ps.setString(i+1, condfield[i]);
                        }
                    }
                }, new ResultSetExtractor<HashMap<String, String>>() {
                    @Override
                    public HashMap<String, String> extractData(ResultSet rs) throws SQLException, DataAccessException {
                        HashMap<String, String> temp = null;
                        while(rs.next()) {
                            temp = new HashMap<String, String>();
                            for(String key : field) {
                                temp.put(key, rs.getString(key));
                            }
                            data.add(temp);
                        }
                        return temp;
                    }
                });
            }else{
                db.query(query,
                new ResultSetExtractor<HashMap<String, String>>() {
                    @Override
                    public HashMap<String, String> extractData(ResultSet rs) throws SQLException, DataAccessException {
                        HashMap<String, String> temp = null;
                        while(rs.next()) {
                            temp = new HashMap<String, String>();
                            for(String key : field) {
                                temp.put(key, rs.getString(key));
                            }
                            data.add(temp);
                        }
                        return temp;
                    }
                });
            }
        }catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
            return null;
        }
        return data;
    }

    /**
     * varchar = char/timestamp
     * number = decimal/int
     **/
    public int insert(String table, String [] fields, String [] values, String [] datatype) {
        try {
            String query = buildInsertQuery(table, fields);
            System.out.println("Query: " + query);

            return this.db.update(query, new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    for(int i=0; i<fields.length; i++) {
                        if(datatype[i].equalsIgnoreCase("varchar")) {
                            ps.setString(i+1, values[i]);
                        }else if(datatype[i].equalsIgnoreCase("decimal")) {
                            ps.setDouble(i+1, Double.parseDouble(values[i]));
                        }else if(datatype[i].equalsIgnoreCase("int")) {
                            ps.setInt(i+1, Integer.parseInt(values[i]));
                        }
                    }
                }
            });
        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
            return -1;
        }
    }


    public String buildUpdateQuery(String table, String [] field, String cond) {
        String query = "update " + table + " set";

        for(int i=0; i<field.length; i++) {
            if(i != field.length-1) {
                query += " " + field[i] + " = ?,";
            }else{
                query += " " + field[i] + " = ?";
            }
        }

        if(cond != null) {
            query += " where " + cond;
        }
        return query;
    }

    public String buildInsertQuery(String table, String[] field) {
        String query = "insert into " + table + " $field values($value)";

        String ffield = "";
        String valField = "";
        for(int i=0; i<field.length; i++) {
            if(i != field.length-1) {
                ffield += field[i] + ",";
                valField += "?,";
            }else {
                ffield += field[i];
                valField += "?";
            }
        }
        query = query.replace("$field", "("+ffield+")").replace("$value", valField);
        return query;
    }

    public String buildSelectQuery(String table, String [] field, String cond) {
        String query = "select ";
        for(int i=0; i<field.length; i++) {
            if(i != field.length-1) {
                query += " " + field[i] + ", ";
            }else {
                query += " " + field[i] + " ";
            }
        }

        query += "from " + table;

        if(cond != null || "".equals(cond)) {
            query += " where " + cond;
        }
        return query;
    }
}
