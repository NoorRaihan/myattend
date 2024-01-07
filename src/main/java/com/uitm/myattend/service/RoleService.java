package com.uitm.myattend.service;

import com.uitm.myattend.model.RoleModel;
import com.uitm.myattend.repository.RoleRepository;
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
}
