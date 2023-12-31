package com.uitm.myattend.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class TestRepository extends GenericRepository{

    @Autowired
    private final JdbcTemplate db;

    @Autowired
    public TestRepository(JdbcTemplate db) {
        super(db);
        this.db = db;
    }

    public ArrayList getData() {
        String [] field = {
                "name",
                "email",
                "password"
        };

        String cond = "name = ? " +
                    "and email = ?";

        String [] condField = {
                "test2",
                "noorraihanabdrahim@gmail.com"};

        ArrayList<HashMap<String, String>> result = select("users", field, cond, condField);
        return result;
    }

    public HashMap<String, String> insertData() {
        String [] field = {
                "name",
                "description",
                "price",
                "uid"
        };

        String [] value = {
                "test product",
                "test product spring",
                "12.00",
                "2"
        };

        String [] type = {
                "varchar",
                "varchar",
                "decimal",
                "int"
        };
        int result = insert("products", field, value, type);

        if(result > 0) {
            return new HashMap<>(){{
                put("result", "success");
            }};
        }else{
            return new HashMap<>(){{
                put("result", "failed");
            }};
        }

    }

    public HashMap<String, String> updateData() {
        String [] field = {
                "name",
                "description"
        };

        String [] value = {
                "test product update",
                "test test update"
        };

        String [] datatype = {
                "varchar",
                "varchar"
        };

        String cond = "id = ?";
        String [] condfield = {"7"};
        String [] condtype = {"int"};

        int result = update("products", field, value, datatype, cond, condfield, condtype);
        if(result > 0) {
            return new HashMap<>(){{
                put("result", "success");
            }};
        }else{
            return new HashMap<>(){{
                put("result", "failed");
            }};
        }
    }

    public HashMap<String, String> deleteData() {
        String cond = "id = ?";
        String [] val = {"7"};
        String [] type = {"int"};

        int result = delete("products", cond, val, type);
        if(result > 0) {
            return new HashMap<>(){{
                put("result", "success");
            }};
        }else{
            return new HashMap<>(){{
                put("result", "failed");
            }};
        }
    }
}
