package com.uitm.myattend.repository;

import com.uitm.myattend.model.LecturerModel;
import com.uitm.myattend.utility.FieldUtility;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class LecturerRepository {

    private final DBRepository commDB;

    public LecturerRepository(DBRepository commDB) {
        this.commDB = commDB;
    }

    public List<Map<String, String>> retrieve() {
        try {
            String sql = "SELECT a.* FROM ma_lecturers a " +
                    "RIGHT JOIN ma_users b ON a.user_id = b.id " +
                    "WHERE b.role_id = 2";

            int result = commDB.sqlQuery(sql);
            if(result == -1) {
                throw new Exception("Failed to execute query statement");
            }
            return commDB.getResult();
        }catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public List<Map<String, String>> retrieveDetail(int uid) {
        try {
            String sql = "SELECT a.* FROM ma_lecturers a " +
                    "RIGHT JOIN ma_users b ON a.user_id = b.id " +
                    "WHERE b.role_id = 2";

            int result = commDB.sqlQuery(sql);
            if(result == -1) {
                throw new Exception("Failed to execute query statement");
            }
            return commDB.getResult();
        }catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public boolean insert(LecturerModel lecturer) {
        try {
            String [] field = {
                    "user_id",
                    "lect_id",
                    "suervisor_id",
                    "start_date",
                    "qualification",
                    "salary",
                    "created_at",
                    "updated_at"
            };

            String [] fieldVal = {
                    Integer.toString(lecturer.getUser_id()),
                    Integer.toString(lecturer.getLect_id()),
                    lecturer.getStart_date(),
                    lecturer.getQualification(),
                    FieldUtility.timestamp2Oracle(FieldUtility.getCurrentTimestamp()),
                    FieldUtility.timestamp2Oracle(FieldUtility.getCurrentTimestamp())
            };

            String [] fieldType = {
                    "int",
                    "int",
                    "int",
                    "date",
                    "varchar",
                    "decimal",
                    "timestamp",
                    "timestamp"
            };

            if(lecturer.getSupervisor_id() != -1) {
                List<String> tempVal = new ArrayList<>(Arrays.asList(fieldVal));
                List<String> tempType = new ArrayList<>(Arrays.asList(fieldType));
                tempVal.add(Integer.toString(lecturer.getSupervisor_id()));
                tempType.add("int");

                fieldVal = (String[]) tempVal.toArray();
                fieldType = (String[]) tempType.toArray();
            }

            int row = commDB.insert("ma_lecturers", field, fieldVal, fieldType);
            if(row <= 0) {
                throw new Exception("Failed to insert into ma_lecturers");
            }
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(Integer uid, Integer lectId) {
        try {

            String cond = "";
            List<String> condVal = new ArrayList<>();
            List<String> condType = new ArrayList<>();
            if(uid != null) {
                cond += "user_id = ?";
                condVal.add(Integer.toString(uid));
                condType.add("int");
            }

            if(lectId != null) {
                cond += cond.isEmpty() ? "stud_id = ?" : " AND stud_id = ?";
                condVal.add(Integer.toString(lectId));
                condType.add("int");
            }

            int result = commDB.delete("ma_lecturers", cond, condVal.toArray(String[]::new), condType.toArray(String[]::new));
            if(result <= 0) {
                throw new Exception("Failed to execute delete query for lecturer");
            }

            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
