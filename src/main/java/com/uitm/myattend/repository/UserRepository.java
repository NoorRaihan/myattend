package com.uitm.myattend.repository;

import com.uitm.myattend.model.UserModel;
import com.uitm.myattend.utility.FieldUtility;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class UserRepository {

    private final DBRepository commDB;

    public UserRepository(DBRepository commDB) {
        this.commDB = commDB;
    }

    public List<Map<String, String>> retrieveAllUser() {
        List<Map<String, String>> data = new ArrayList<>();
        try {
            String sql = "select a.*, b.role_name, c.id, c.login_time, c.valid " +
                    "from ma_users a " +
                    "inner join ma_roles b on a.role_id = b.id " +
                    "inner join ma_tokens c on c.user_id = a.id";

            int result = commDB.sqlQuery(sql);
            if(result < 0) {
                throw new Exception("Failed to execute query");
            }

            data = commDB.getResult();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public List<Map<String, String>> retrieveUserInfo(String uid) {
        List<Map<String, String>> data = new ArrayList<>();
        try {
            String sql = "select a.*, b.role_name, c.id, c.login_time, c.valid " +
                        "from ma_users a " +
                        "inner join ma_roles b on a.role_id = b.role_id " +
                        "inner join ma_tokens c on c.user_id = a.id " +
                        "where a.id = "+ uid;

            int result = commDB.sqlQuery(sql);
            if(result < 0) {
                throw new Exception("Failed to execute query");
            }

            data = commDB.getResult();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public List<Map<String, String>> retrieveUserById(String uid) {
        return retrieveUser("", uid);
    }

    public List<Map<String, String>> retrieveUserByEmail(String email) {
        return retrieveUser(email, "");
    }

    public List<Map<String, String>> retrieveUser(String email, String uid) {
        try {
            String [] field = {
                    "id",
                    "email",
                    "password",
                    "full_name",
                    "gender",
                    "birth_date",
                    "profile_pic",
                    "role_id"
            };

            String cond = "";
            List<String> condval = new ArrayList<>();
            List<String> condtype = new ArrayList<>();

            if(!uid.isEmpty()) {
                cond = "id = ?";
                condval.add(uid);
                condtype.add("varchar");
            }

            if(!email.isEmpty()) {
                cond = "email = ?";
                condval.add(email);
                condtype.add("varchar");
            }

            List<Map<String, String>> data = commDB.select("ma_users", field, cond, (String[])condval.toArray(), (String[])condtype.toArray());

            if(data == null) {
                throw new Exception("Failed to retrieve user");
            }

            return data;
        }catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<Map<String, String>> retrieveRole(String roleId) {
        try {
            String [] field = {
                    "id",
                    "role_name",
            };

            String cond = "id = ?";
            String [] condval = {roleId};
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

    public List<Map<String, String>> retrieveToken(String tokenId) {
        try {
            String [] field = {
                    "id",
                    "login_time",
                    "valid",
                    "user_id"
            };

            String cond = "id = ?";
            String [] condval = {tokenId};
            String [] condtype = {"varchar"};

            List<Map<String, String>> data = commDB.select("ma_tokens", field, cond, condval, condtype);

            if(data == null) {
                throw new Exception("Failed to retrieve role");
            }

            return data;
        }catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public boolean insert(UserModel user) {
        try {
            String [] field = {
                    "id",
                    "email",
                    "username",
                    "full_name",
                    "password",
                    "gender",
                    "birth_date",
                    "profile_pic",
                    "role_id",
                    "created_at",
                    "updated_at"
            };

            String [] fieldVal = {
                    Integer.toString(user.getId()),
                    user.getEmail(),
                    user.getUsername(),
                    user.getFullname(),
                    user.getPassword(),
                    user.getGender(),
                    FieldUtility.date2Oracle(user.getBirth_date()),
                    user.getProfile_pic(),
                    Integer.toString(user.getRole_id()),
                    FieldUtility.timestamp2Oracle(FieldUtility.getCurrentTimestamp()),
                    FieldUtility.timestamp2Oracle(FieldUtility.getCurrentTimestamp())
            };

            System.out.println(Arrays.toString(fieldVal));

            String [] fieldType = {
                    "int",
                    "varchar",
                    "varchar",
                    "varchar",
                    "varchar",
                    "varchar",
                    "varchar",
                    "varchar",
                    "int",
                    "varchar",
                    "varchar"
            };

            int row = commDB.insert("ma_users", field, fieldVal, fieldType);
            if(row <= 0) {
                throw new Exception("Failed to insert into ma_users");
            }
            return true;
        }catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}