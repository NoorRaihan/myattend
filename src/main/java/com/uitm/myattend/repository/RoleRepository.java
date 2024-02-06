package com.uitm.myattend.repository;

import com.uitm.myattend.model.RoleModel;
import com.uitm.myattend.utility.FieldUtility;
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

    public boolean insert(RoleModel roleModel) {
        try {
            String currTms = FieldUtility.timestamp2Oracle(FieldUtility.getCurrentTimestamp());

            String [] field = {
                    "id",
                    "role_name",
                    "created_at",
                    "updated_at"
            };

            String [] fieldVal = {
                    roleModel.getId(),
                    roleModel.getRole_name(),
                    currTms,
                    currTms
            };

            String [] fieldType = {
                    "int",
                    "varchar",
                    "timestamp",
                    "timestamp"
            };

            int result = commDB.insert("ma_roles", field, fieldVal, fieldType);
            if(result <= 0) {
                throw new Exception("Failed to insert into ma_roles");
            }
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
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

    public boolean update(RoleModel roleModel, int roleId) {
        try {
            String currTms = FieldUtility.timestamp2Oracle(FieldUtility.getCurrentTimestamp());

            String [] field = {
                    "id",
                    "role_name",
                    "updated_at"
            };

            String [] fieldVal = {
                    roleModel.getId(),
                    roleModel.getRole_name(),
                    currTms
            };

            String [] fieldType = {
                    "int",
                    "varchar",
                    "timestamp",
            };

            String cond = "id = ?";
            String [] condval = {Integer.toString(roleId)};
            String [] condtype = {"varchar"};


            int result = commDB.update("ma_roles", field, fieldVal, fieldType, cond, condval, condtype);
            if(result <= 0) {
                throw new Exception("Failed to update role : " + roleId);
            }
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int roleId) {
        try {
            String cond = "id = ?";
            String [] condval = {Integer.toString(roleId)};
            String [] condtype = {"varchar"};


            int result = commDB.delete("ma_roles", cond, condval, condtype);
            if(result <= 0) {
                throw new Exception("Failed to delete role : " + roleId);
            }
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
