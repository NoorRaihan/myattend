package com.uitm.myattend.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Repository
public class DBRepository {

    private final JdbcTemplate dbTemplate;
    private ArrayList<Map<String, String>> result;
    private ArrayList<String> columnLabel;

    public DBRepository(JdbcTemplate dbTemplate) {
        this.dbTemplate = dbTemplate;
    }

    public void setAutoCommit(boolean commit) throws SQLException {
        dbTemplate
                .getDataSource()
                .getConnection()
                .setAutoCommit(commit);
    }

    public boolean getAutoCommit() throws SQLException {
       return dbTemplate
               .getDataSource()
               .getConnection()
               .getAutoCommit();
    }

    public void commit() throws SQLException{
        dbTemplate
                .getDataSource()
                .getConnection()
                .commit();
    }

    public void startTransaction() {
        try {
            if(getAutoCommit()) {
                setAutoCommit(false);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() throws SQLException {
        dbTemplate
                .getDataSource()
                .getConnection()
                .close();
    }

    public void endTransaction() {
        try {
            setAutoCommit(true);
            closeConnection();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Map<String, String>> getResult() {
        if(!result.isEmpty()) {
            return result;
        }else{
            return null;
        }
    }

    public int getNumberOfRows() {
        return result.size();
    }

    public ArrayList<String> getColumnLabel() {
        return this.columnLabel;
    }

    public int sqlQuery(String query) {
        ArrayList<Map<String, String>> data = new ArrayList<Map<String, String>>();

        try {

            if(query.split(" ")[0].equalsIgnoreCase("select")) {
                dbTemplate.query(query, new ResultSetExtractor<HashMap<String, String>>() {
                    @Override
                    public HashMap<String, String> extractData(ResultSet rs) throws SQLException, DataAccessException {
                        HashMap<String, String> temp = null;
                        result = new ArrayList<Map<String, String>>();

                        setColumnLabel(rs);

                        while(rs.next()) {
                            temp = new HashMap<String, String>();
                            for(String key : columnLabel) {
                                temp.put(key, rs.getString(key));
                            }
                            result.add(temp);
                        }
                        return temp;
                    }
                });
            }else{
                return this.dbTemplate.update(query);
            }

            return 1;
        }catch (Exception e) {
            e.printStackTrace();
            System.err.println("ERROR: " + e.getMessage());
            return -1;
        }

    }

    public ArrayList<Map<String, String>> select(String table, String [] field) {
        return select(table, field, null, null, null);
    }

    public ArrayList<Map<String, String>> select(String table, String [] field, String cond) {
        return select(table, field, cond, null, null);
    }

    public ArrayList<Map<String, String>> select(String table, String [] field, String cond, String [] condval, String [] condtype) {
        ArrayList<Map<String, String>> data = new ArrayList<Map<String, String>>();
        try {
            String query = buildSelectQuery(table, field, cond);

            System.out.println("Query: " + query);
            if(condval != null && condval.length > 0) {
                System.out.println("executed1");
                dbTemplate.query(query, prepareSQLStatement(field, null, null, condval, condtype), new ResultSetExtractor<HashMap<String, String>>() {
                    @Override
                    public HashMap<String, String> extractData(ResultSet rs) throws SQLException, DataAccessException {
                        HashMap<String, String> temp = null;

                        setColumnLabel(rs);
                        while(rs.next()) {
                            temp = new HashMap<String, String>();
                            for(String key : field) {
                                System.out.println(rs.getString(key));
                                temp.put(key, rs.getString(key));
                            }
                            data.add(temp);
                            System.out.println(temp);
                        }
                        return temp;
                    }
                });
            }else{
                System.out.println("executed2");
                dbTemplate.query(query,
                        new ResultSetExtractor<HashMap<String, String>>() {
                            @Override
                            public HashMap<String, String> extractData(ResultSet rs) throws SQLException, DataAccessException {
                                HashMap<String, String> temp = null;

                                setColumnLabel(rs);

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
            //writeLog("Select statement error on " + table + ": " + e.getMessage(), "ERROR");
            System.err.println("ERRORS: " + e.getMessage());
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

            return this.dbTemplate.update(query, prepareSQLStatement(fields, values, datatype));
        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
            return -1;
        }
    }

    public int update(String table, String [] fields, String [] values, String [] datatype, String cond, String [] condval, String [] condtype) {
        try {
            String query = buildUpdateQuery(table, fields, cond);
            System.out.println("Query: " + query);

            if(query == null) {
                throw new Exception("Invalid query");
            }
            return this.dbTemplate.update(query, prepareSQLStatement(fields, values, datatype, condval, condtype));
        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
            return -1;
        }
    }

    public int delete(String table, String cond, String [] values, String [] datatype) {
        try {
            String query = buildDeleteQuery(table, cond);

            if(query == null) {
                throw new Exception("Invalid query");
            }
            System.out.println("Query: " + query);

            return this.dbTemplate.update(query, new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    for(int i=0; i<values.length; i++) {
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

    private String buildDeleteQuery(String table, String cond) {
        try {
            if(cond == null) {
                throw new Exception("Delete operation need 'where' condition");
            }
            return "delete from " + table + " where " + cond;
        }catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
            return null;
        }
    }

    public String buildUpdateQuery(String table, String [] field, String cond) {
        try {
            String query = "update " + table + " set";

            for(int i=0; i<field.length; i++) {
                if(i != field.length-1) {
                    query += " " + field[i] + " = ?,";
                }else{
                    query += " " + field[i] + " = ?";
                }
            }

            if(cond == null) {
                throw new Exception("Update operation need 'where' condition");
            }
            query += " where " + cond;
            return query;
        }catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
            return null;
        }

    }

    private String buildInsertQuery(String table, String[] field) {
        try{
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
        }catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
            return null;
        }
    }

    private String buildSelectQuery(String table, String [] field, String cond) {
        try {
            String query = "select ";
            for(int i=0; i<field.length; i++) {
                if(i != field.length-1) {
                    query += " " + field[i] + ", ";
                }else {
                    query += " " + field[i] + " ";
                }
            }

            query += "from " + table;
            if(cond != null && !cond.isEmpty()) {
                query += " where " + cond;
            }
            return query;
        }catch (Exception e){
            System.err.println("ERROR: " + e.getMessage());
            return null;
        }

    }


    private PreparedStatementSetter prepareSQLStatement(String [] fields, String [] values, String [] datatype) {
        return prepareSQLStatement(fields, values, datatype, null, null);
    }

    private PreparedStatementSetter prepareSQLStatement(String [] fields, String [] values, String [] datatype, String [] condval, String [] condtype) {
        System.out.println("executed prepare");
        return (ps) -> {
            try {
                if(values != null && values.length > 0) {
                    for(int i=0; i<fields.length; i++) {
                        switch (datatype[i].toUpperCase()) {
                            case "VARCHAR":
                                ps.setString(i+1, values[i]);
                                break;
                            case "DECIMAL":
                                ps.setDouble(i+1, Double.parseDouble(values[i]));
                                break;
                            case "INT":
                                ps.setInt(i+1, Integer.parseInt(values[i]));
                                break;
                            default:
                                throw new Exception("Invalid datatype on value " + values[i] + " with datatype :" + datatype[i]);
                        }

                    }
                }

                if(condval != null && condval.length > 0) {

                    int counter = values == null ? 0 : values.length;
                    for(int i=counter; i<counter + condval.length; i++) {
                        int y = i-counter;

                        switch (condtype[y].toUpperCase()) {
                            case "VARCHAR":
                                ps.setString(i+1, condval[y]);
                                break;
                            case "DECIMAL":
                                ps.setDouble(i+1, Double.parseDouble(condval[y]));
                                break;
                            case "INT":
                                ps.setInt(i+1, Integer.parseInt(condval[y]));
                                break;
                            default:
                                throw new Exception("Invalid datatype on value " + condval[y] + " with datatype :" + condval[y]);
                        }
                    }
                }

            }catch (SQLException e) {
                System.err.println("SQLException: " + e.getMessage());
                throw new SQLException(e);
            } catch (Exception e) {
                System.err.println("Prepare statement error: " + e.getMessage());
                throw new RuntimeException();
            }
        };
    }

    private void setColumnLabel(ResultSet rs) {
        try {
            columnLabel = new ArrayList<String>();
            int columnCount = rs.getMetaData().getColumnCount();

            for(int i=0; i<columnCount; i++) {
                columnLabel.add(rs.getMetaData().getColumnLabel(i+1));
            }
        }catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
        }catch (Exception e) {
            System.err.println("Exception error: " + e.getMessage());
        }
    }
}