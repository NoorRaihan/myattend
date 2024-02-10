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

    //retrieve all user
    public List<Map<String, String>> retrieveAllUser() {
        List<Map<String, String>> data = new ArrayList<>();
        try {
            String sql = "select a.*, b.role_name " +
                    "from ma_users a " +
                    "inner join ma_roles b on a.role_id = b.id";

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

    //retrieve user info with tokens and roles
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

    public List<Map<String, String>> retrieveUserByUsername(String username) {
        return retrieveUser(username, "", "");
    }

    public List<Map<String, String>> retrieveUserById(String uid) {
        return retrieveUser("", "", uid);
    }

    public List<Map<String, String>> retrieveUserByEmail(String email) {
        return retrieveUser("", email, "");
    }

    //retrieve user details only
    public List<Map<String, String>> retrieveUser(String username, String email, String uid) {
        try {
            String [] field = {
                    "id",
                    "username",
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
                cond += " id = ?";
                condval.add(uid);
                condtype.add("int");
            }

            if(!email.isEmpty()) {
                cond += cond.isEmpty() ? " email = ?" : " AND email = ?";
                condval.add(email);
                condtype.add("varchar");
            }

            if(!username.isEmpty()) {
                cond += cond.isEmpty() ? " username = ?" : " AND username = ?";
                condval.add(username);
                condtype.add("varchar");
            }

            List<Map<String, String>> data = commDB.select("ma_users", field, cond, condval.toArray(String[]::new), condtype.toArray(String[]::new));

            if(data == null) {
                throw new Exception("Failed to retrieve user");
            }

            return data;
        }catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    //retrieve user token info
    public List<Map<String, String>> retrieveToken(String tokenId) {
        try {
            String [] field = {
                    "id",
                    "login_time",
                    "valid",
                    "user_id",
                    "created_at",
                    "updated_at"
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

    //insert new token for logged in user
    public boolean insertToken(String token, int uid) {
        try {
            String [] field = {
                    "id",
                    "valid",
                    "user_id"
            };

            String [] fieldval = {
                    token,
                    "1",
                    Integer.toString(uid)
            };

            String [] fieldtype = {
                    "varchar",
                    "int",
                    "int"
            };

            int result = commDB.insert("ma_tokens", field, fieldval, fieldtype);

            if(result <= 0) {
                throw new Exception("Failed to insert new token");
            }
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //update token once logout
    public boolean updateToken(String token, int uid, int status) {
        try {
            String [] field = {
                    "valid",
                    "updated_at"
            };

            String cond = "valid = 1";
            List<String> condval = new ArrayList<>();
            List<String> condtype = new ArrayList<>();

            if(token != null && !token.isEmpty()) {
                cond += " AND id = ?";
                condval.add(token);
                condtype.add("varchar");
            }

            if(uid != -1) {
                cond += " AND user_id = ?";
                condval.add(Integer.toString(uid));
                condtype.add("int");
            }

            String [] fieldval = {
                    Integer.toString(status),
                    FieldUtility.timestamp2Oracle(FieldUtility.getCurrentTimestamp())
            };

            String [] fieldtype = {
                    "int",
                    "timestamp"
            };

            int result = commDB.update("ma_tokens", field, fieldval, fieldtype, cond, condval.toArray(String[]::new), condtype.toArray(String[]::new));

            if(result < 0) {
                throw new Exception("Failed to update token");
            }
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //insert new user data
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


            String [] fieldType = {
                    "varchar",
                    "varchar",
                    "varchar",
                    "varchar",
                    "varchar",
                    "date",
                    "varchar",
                    "int",
                    "timestamp",
                    "timestamp"
            };

            //send sql function to the framework
            Map<String, String> sqlFunction  = new HashMap<>();
            sqlFunction.put("id", "uidseq.NEXTVAL"); //send uidseq oracle sequencer to retrieve user id sequence

            int row = commDB.insert("ma_users", field, fieldVal, fieldType, sqlFunction);
            if(row <= 0) {
                throw new Exception("Failed to insert into ma_users");
            }
            return true;
        }catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //delete user data
    public boolean delete(int uid) {
        try {
            String cond = "id = ?";
            String [] val = {Integer.toString(uid)};
            String [] type = {"int"};

            int result = commDB.delete("ma_users", cond, val, type);

            if(result < 1) {
                throw new Exception("Data does not existed to be deleted");
            }

            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //update user data
    public boolean update(UserModel userModel) {
        try {
            String [] field = {
                    "email",
                    "username",
                    "full_name",
                    "gender",
                    "birth_date",
                    "role_id",
                    "updated_at"
            };

            String [] fieldval = {
                    userModel.getEmail(),
                    userModel.getUsername(),
                    userModel.getFullname(),
                    userModel.getGender(),
                    FieldUtility.date2Oracle(userModel.getBirth_date()),
                    Integer.toString(userModel.getRole_id()),
                    FieldUtility.timestamp2Oracle(FieldUtility.getCurrentTimestamp())
            };

            String [] fieldType = {
                    "varchar",
                    "varchar",
                    "varchar",
                    "varchar",
                    "date",
                    "int",
                    "timestamp",
            };

            List<String> fieldList = null;
            List<String> fieldValList = null;
            List<String> fieldTypeList = null;
            if((userModel.getProfile_pic() != null && !userModel.getProfile_pic().isEmpty() ||
                    (userModel.getPassword() != null && !userModel.getPassword().isEmpty()))) {

                fieldList = new ArrayList<>(Arrays.stream(field).toList());
                fieldValList = new ArrayList<>(Arrays.stream(fieldval).toList());
                fieldTypeList = new ArrayList<>(Arrays.stream(fieldType).toList());

                if(userModel.getProfile_pic() != null && !userModel.getProfile_pic().isEmpty()) {
                    fieldList.add("profile_pic");
                    fieldValList.add(userModel.getProfile_pic());
                    fieldTypeList.add("varchar");
                }

                if(userModel.getPassword() != null && !userModel.getPassword().isEmpty()) {
                    fieldList.add("password");
                    fieldValList.add(userModel.getPassword());
                    fieldTypeList.add("varchar");
                }

                field = fieldList.toArray(String[]::new);
                fieldval = fieldValList.toArray(String[]::new);
                fieldType = fieldTypeList.toArray(String[]::new);
            }


            String cond = "id = ?";
            String [] condval = {Integer.toString(userModel.getId())};
            String [] condtype = {"int"};

            int row = commDB.update("ma_users", field, fieldval, fieldType, cond, condval, condtype);
            if(row <= 0) {
                throw new Exception("Failed to update user " + userModel.getId());
            }
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
