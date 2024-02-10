package com.uitm.myattend.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.annotation.RequestScope;

import java.sql.*;
import java.util.*;

// CORE CUSTOM FRAMEWORK HANDLING TO STANDARDIZE SECURITY MEASURE AND SQL HANDLING FOR ALL SQL

@Repository
@RequestScope //initiate new instance for new request
public class DBRepository {

    private final JdbcTemplate dbTemplate;
    private List<Map<String, String>> result;
    private List<String> columnLabel;

    public DBRepository(JdbcTemplate dbTemplate) {
        this.dbTemplate = dbTemplate;
    }

    //get result from custom query if have any list
    public List<Map<String, String>> getResult() {
        if(!result.isEmpty()) {
            return result;
        }else{
            return new ArrayList<>();
        }
    }

    public int getNumberOfRows() {
        return result.size();
    }

    public List<String> getColumnLabel() {
        return this.columnLabel;
    }

    public int sqlQuery(String query) {
        return sqlQuery(query, null, null);
    }

    //handle custom sql query to override standard method
    public int sqlQuery(String query, String [] condval, String [] condtype) {
        ArrayList<Map<String, String>> data = new ArrayList<Map<String, String>>();

        try {
            System.out.println("Sql: " + query);
            //check for select statement onlu
            if(query.split(" ")[0].equalsIgnoreCase("select")) {
                dbTemplate.query(query, prepareSQLStatement(null, null, condval, condtype), new ResultSetExtractor<HashMap<String, String>>() {
                    @Override
                    public HashMap<String, String> extractData(ResultSet rs) throws SQLException, DataAccessException {
                        HashMap<String, String> temp = null;
                        result = new ArrayList<Map<String, String>>();

                        setColumnLabel(rs);
                        //send and map the result to result variable
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
                return this.dbTemplate.update(query); //for custom INSERT, UPDATE, DELETE
            }

            return 1;
        }catch (Exception e) {
            e.printStackTrace();
            return -1;
        }

    }

    public List<Map<String, String>> select(String table, String [] field) {
        return select(table, field, null, null, null);
    }

    public List<Map<String, String>> select(String table, String [] field, String cond) {
        return select(table, field, cond, null, null);
    }

    //handle select statement by only pass field, condition and value and type of the value
    public List<Map<String, String>> select(String table, String [] field, String cond, String [] condval, String [] condtype) {
        List<Map<String, String>> data = new ArrayList<>();
        try {
            //build the select query
            String query = buildSelectQuery(table, field, cond);
            //handle select statement for select with condiion
            if(condval != null && condval.length > 0) {
                dbTemplate.query(query, prepareSQLStatement(null, null, condval, condtype), new ResultSetExtractor<HashMap<String, String>>() {
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
            }else{ //handle for select without condition to avoid any unnecessary trigger
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
            System.err.println("ERRORS: " + e.getMessage());
            return Collections.<Map<String, String>>emptyList();
        }
        return data;
    }

    public int insert(String table, String [] fields, String [] values, String [] datatype) {
        return insert(table, fields, values, datatype, Collections.emptyMap());
    }

    //handle data insertion into database
    public int insert(String table, String [] fields, String [] values, String [] datatype, Map<String, String> sqlFunction) {
        try {
            //build insert query
            String query = buildInsertQuery(table, fields, sqlFunction);

            return this.dbTemplate.update(query, prepareSQLStatement(fields, values, datatype));
        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
            return -1;
        }
    }

    public int update(String table, String [] fields, String [] values, String [] datatype, String cond, String [] condval, String [] condtype) {
        return update(table, fields, values, datatype, cond, condval, condtype, Collections.emptyMap());
    }

    //handle update sql statement
    public int update(String table, String [] fields, String [] values, String [] datatype, String cond, String [] condval, String [] condtype, Map<String, String> sqlFunction) {
        try {
            //build update query
            String query = buildUpdateQuery(table, fields, cond, sqlFunction);

            if(query == null) {
                throw new Exception("Invalid query");
            }
            return this.dbTemplate.update(query, prepareSQLStatement(values, datatype, condval, condtype));
        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
            return -1;
        }
    }

    //handle delete statement
    public int delete(String table, String cond, String [] values, String [] datatype) {
        try {
            //build delete query
            String query = buildDeleteQuery(table, cond);

            if(query == null) {
                throw new Exception("Invalid query");
            }

            //handle delete query
            return this.dbTemplate.update(query, prepareSQLStatement(null, null, values, datatype));
        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
            return -1;
        }
    }

    //build delete query
    private String buildDeleteQuery(String table, String cond) {
        try {
            if(cond == null) {
                throw new Exception("Delete operation need 'where' condition");
            }
            String query =  "delete from " + table + " where " + cond;
            System.out.println("Sql: " + query);
            return query;
        }catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
            return null;
        }
    }

    //build update query
    public String buildUpdateQuery(String table, String [] field, String cond, Map<String, String> sqlFunction) {
        try {
            String query = "update " + table + " set";

            for(int i=0; i<field.length; i++) {
                if(i != field.length-1) {
                    query += " " + field[i] + " = " + sqlFunction.getOrDefault(field[i], "?") + ",";
                }else{
                    query += " " + field[i] + " = " + sqlFunction.getOrDefault(field[i], "?");
                }
            }

            if(cond == null) {
                throw new Exception("Update operation need 'where' condition");
            }
            query += " where " + cond;
            System.out.println("Sql: " + query);
            return query;
        }catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
            return null;
        }

    }

    //build insert query
    private String buildInsertQuery(String table, String[] field, Map<String, String> sqlFunction) {
        try{
            String query = "insert into " + table + " $field values($value)";

            String ffield = "";
            String valField = "";
            for(int i=0; i<field.length; i++) {
                if(i != field.length-1) {
                    ffield += field[i] + ",";
                    valField += sqlFunction.getOrDefault(field[i], "?") + ",";
                }else {
                    ffield += field[i];
                    valField += sqlFunction.getOrDefault(field[i], "?");
                }
            }
            query = query.replace("$field", "("+ffield+")").replace("$value", valField);
            System.out.println("Sql: " + query);
            return query;
        }catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
            return null;
        }
    }

    //build the select query to its standard for prepare statement
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
            System.out.println("Sql: " + query);
            return query;
        }catch (Exception e){
            System.err.println("ERROR: " + e.getMessage());
            return null;
        }

    }


    private PreparedStatementSetter prepareSQLStatement(String [] fields, String [] values, String [] datatype) {
        return prepareSQLStatement(values, datatype, null, null);
    }

    //handle prepare statement custom configuration for generic usage
    private PreparedStatementSetter prepareSQLStatement(String [] values, String [] datatype, String [] condval, String [] condtype) {
        PreparedStatementSetter prepSetter =  (ps) -> {
            try {
                //set up the prepare statement for main field, mainly for update and insertion
                if(values != null && values.length > 0) {
                    System.out.println("Field Value: " + Arrays.toString(values));
                    for(int i=0; i<values.length; i++) {
                        //handle its own datatype to proper handling
                        switch (datatype[i].toUpperCase()) {
                            case "VARCHAR" -> ps.setString(i+1, values[i]);
                            case "DECIMAL" -> ps.setDouble(i+1, Double.parseDouble(values[i]));
                            case "INT" -> ps.setInt(i+1, Integer.parseInt(values[i]));
                            case "TIMESTAMP" -> ps.setTimestamp(i+1, values[i] == null ? null : Timestamp.valueOf(values[i]));
                            case "DATE" -> ps.setDate(i+1, values[i] == null ? null : java.sql.Date.valueOf(values[i]));
                            default -> throw new Exception("Invalid datatype on value " + values[i] + " with datatype :" + datatype[i]);
                        }

                    }
                }

                //set up the prepare statement for condition field to its datatype
                if(condval != null && condval.length > 0) {
                    System.out.println("Cond Value: " + Arrays.toString(condval));
                    int counter = values == null ? 0 : values.length;
                    for(int i=counter; i<counter + condval.length; i++) {
                        int y = i-counter;
                        //handle for conditiion datatype
                        switch (condtype[y].toUpperCase()) {
                            case "VARCHAR" -> ps.setString(i+1, condval[y]);
                            case "DECIMAL" -> ps.setDouble(i+1, Double.parseDouble(condval[y]));
                            case "INT" -> ps.setInt(i+1, Integer.parseInt(condval[y]));
                            default -> throw new Exception("Invalid datatype on value " + condval[y] + " with datatype :" + condval[y]);
                        }
                    }
                }

            }catch (SQLException e) {
                System.err.println("SQLException: " + e.getMessage());
                throw new SQLException(e);
            } catch (Exception e) {
                System.err.println("Prepare statement error: " + e.getMessage());
                e.printStackTrace();
                throw new RuntimeException();
            }
        };

        return prepSetter;
    }

    //set the retrieved column label for each sql retrieve process
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
