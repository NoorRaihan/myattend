package com.uitm.myattend.service;

import com.uitm.myattend.model.RoleModel;
import com.uitm.myattend.repository.RoleRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RoleService {

    private final RoleRepository roleRepo;

    public RoleService(RoleRepository roleRepo) {
        this.roleRepo = roleRepo;
    }

    public RoleModel retrieve(int roleId) {
        try {
            List<Map<String, String>> roleList = roleRepo.retrieve(roleId);

            if(roleList.size() != 1) {
                throw new Exception("Error retrieving role! RoleList size: " + roleList.size());
            }

            RoleModel roleObj = new RoleModel();
            Map<String, String> roleMap = roleList.get(0);
            roleObj.setId(roleMap.get("id"));
            roleObj.setRole_name(roleMap.get("role_name"));

            return roleObj;
        }catch (Exception e) {
            return null;
        }
    }

    public void insert(Map<String, Object> body) throws Exception{
        RoleModel roleModel = new RoleModel();
        roleModel.setId((String) body.get("id"));
        roleModel.setId((String) body.get("name"));

        if(!roleRepo.retrieve(Integer.parseInt(roleModel.getId())).isEmpty()) {
            throw new Exception("Role id already existed");
        }

        if(!roleRepo.insert(roleModel)) {
            throw new Exception("Failed to register new role");
        }
    }

    public void update(Map<String, Object> body) throws Exception{
        RoleModel roleModel = new RoleModel();
        roleModel.setId((String) body.get("id"));
        roleModel.setId((String) body.get("name"));

        if(!roleRepo.retrieve(Integer.parseInt(roleModel.getId())).isEmpty()) {
            throw new Exception("Role id already existed");
        }

        if(!roleRepo.update(roleModel, Integer.parseInt((String) body.get("oriid")))) {
            throw new Exception("Failed to update role");
        }
    }

    public boolean delete(Map<String, Object> body) {
        try {
            int roleId = Integer.parseInt((String) body.get("id"));

            if(!roleRepo.delete(roleId)) {
                throw new Exception("Failed to delete role data");
            }

            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
