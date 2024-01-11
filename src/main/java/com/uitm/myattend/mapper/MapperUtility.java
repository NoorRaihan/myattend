package com.uitm.myattend.mapper;

import com.uitm.myattend.model.RoleModel;
import com.uitm.myattend.model.UserModel;
import org.antlr.v4.runtime.tree.Tree;

import java.time.temporal.Temporal;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class MapperUtility {

    public static Object mapModel(Class<?> modelClass, Map<String, String> data) throws Exception{
        String className = modelClass.getSimpleName();
        Object obj = null;

        //convert into case insensitive mapping
        TreeMap<String, String> tempMap = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        tempMap.putAll(data);

        switch(className.toUpperCase()) {
            case "USERMODEL" -> obj = userMapper(tempMap);
            default -> throw new Exception("Invalid class");
        }

        return obj;
    }

    private static UserModel userMapper(TreeMap<String, String> data) {
        System.out.println("DATA: " + data.get("ID"));
        UserModel userObj = new UserModel();
        userObj.setId(Integer.parseInt(data.get("ID")));
        userObj.setUsername(data.get("USERNAME"));
        userObj.setEmail(data.get("EMAIL"));
        userObj.setPassword(data.get("PASSWORD"));
        userObj.setFullname(data.get("FULL_NAME"));
        userObj.setGender(data.get("GENDER"));
        userObj.setBirth_date(data.get("BIRTH_DATE"));
        userObj.setProfile_pic(data.get("PROFILE_PIC"));
        userObj.setRole_id(Integer.parseInt(data.get("ROLE_ID")));

        TreeMap<String, String> roleMap = new TreeMap<>();
        roleMap.put("ID", data.get("ROLE_ID"));
        roleMap.put("ROLE_NAME", data.get("ROLE_NAME"));
        RoleModel role = roleMapper(roleMap);
        userObj.setRole(role);
        return userObj;
    }

    private static RoleModel roleMapper(TreeMap<String, String> data) {
        RoleModel roleObj = new RoleModel();
        roleObj.setId(data.get("ID"));
        roleObj.setRole_name(data.get("ROLE_NAME"));
        return roleObj;
    }
}
