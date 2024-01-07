package com.uitm.myattend.repository;

import org.springframework.stereotype.Repository;

import javax.management.relation.Role;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class RoleRepository {

    private final DBRepository commDB;

    public RoleRepository(DBRepository commDB) {
        this.commDB = commDB;
    }

    public List<Map<String, String>> retrieve(int roleId) {
        try {
            String [] field = {
                    "id",
                    "role_name",
            };

            String cond = "id = ?";
            String [] condval = {Integer.toString(roleId)};
            String [] condtype = {"varchar"};

            List<Map<String, String>> data = commDB.select("ma_roles", field, cond, condval, condtype);

            if(data == null) {
                throw new Exception("Failed to retrieve role");
            }

            return data;
        }catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
