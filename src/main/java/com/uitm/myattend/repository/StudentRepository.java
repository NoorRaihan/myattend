package com.uitm.myattend.repository;

import com.uitm.myattend.model.StudentModel;
import com.uitm.myattend.utility.FieldUtility;
import jakarta.annotation.Nullable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
public class StudentRepository {

    private final DBRepository commDB;

    public StudentRepository(DBRepository commDB) {
        this.commDB = commDB;
    }

    public List<Map<String, String>> retrieve() {
        try {
            String sql = "SELECT b.*, a.* FROM ma_students a " +
                    "RIGHT JOIN ma_users b ON a.user_id = b.id " +
                    "WHERE b.role_id = 3";

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
            String sql = "SELECT b.*, a.* FROM ma_students a " +
                    "RIGHT JOIN ma_users b ON a.user_id = b.id " +
                    "WHERE b.role_id = 3 and b.id = ?";

            String [] condVal = {
                    Integer.toString(uid)
            };

            String [] condType = {
                    "int"
            };

            int result = commDB.sqlQuery(sql, condVal, condType);
            if(result == -1) {
                throw new Exception("Failed to execute query statement");
            }
            return commDB.getResult();
        }catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public List<Map<String, String>> retrieveRaw(Integer uid, Integer studId) {
        try {
            String [] field = {
                    "user_id",
                    "stud_id",
                    "program",
                    "intake",
                    "semester"
            };

            String cond = "";
            List<String> condVal = new ArrayList<>();
            List<String> condType = new ArrayList<>();
            if(uid != null) {
                cond += "user_id = ?";
                condVal.add(Integer.toString(uid));
                condType.add("int");
            }

            if(studId != null) {
                cond += cond.isEmpty() ? "stud_id = ?" : " AND stud_id = ?";
                condVal.add(Integer.toString(studId));
                condType.add("int");
            }

            return commDB.select("ma_students", field, cond, condVal.toArray(String[]::new), condType.toArray(String[]::new));
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean insert(StudentModel student) {
        try {
            String [] field = {
                    "user_id",
                    "stud_id",
                    "program",
                    "intake",
                    "semester",
                    "created_at",
                    "updated_at"
            };

            String [] fieldVal = {
                    Integer.toString(student.getUser_id()),
                    Integer.toString(student.getStud_id()),
                    student.getProgram(),
                    student.getIntake(),
                    Integer.toString(student.getSemester()),
                    FieldUtility.timestamp2Oracle(FieldUtility.getCurrentTimestamp()),
                    FieldUtility.timestamp2Oracle(FieldUtility.getCurrentTimestamp())
            };

            String [] fieldType = {
                    "int",
                    "int",
                    "varchar",
                    "varchar",
                    "int",
                    "timestamp",
                    "timestamp"
            };

            int row = commDB.insert("ma_students", field, fieldVal, fieldType);
            if(row <= 0) {
                throw new Exception("Failed to insert into ma_students");
            }
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(StudentModel student) {
        try {
            String [] field = {
                    "user_id",
                    "stud_id",
                    "program",
                    "intake",
                    "semester",
                    "created_at",
                    "updated_at"
            };

            String [] fieldVal = {
                    Integer.toString(student.getUser_id()),
                    Integer.toString(student.getStud_id()),
                    student.getProgram(),
                    student.getIntake(),
                    Integer.toString(student.getSemester()),
                    FieldUtility.timestamp2Oracle(FieldUtility.getCurrentTimestamp()),
                    FieldUtility.timestamp2Oracle(FieldUtility.getCurrentTimestamp())
            };

            String [] fieldType = {
                    "int",
                    "int",
                    "varchar",
                    "varchar",
                    "int",
                    "timestamp",
                    "timestamp"
            };

            String cond = "user_id = ? AND stud_id = ?";

            String [] condVal = {
                    Integer.toString(student.getUser_id()),
                    Integer.toString(student.getStud_id()),
            };

            String [] condType = {
                    "int",
                    "int"
            };

            int row = commDB.update("ma_students", field, fieldVal, fieldType, cond, condVal, condType);
            if(row <= 0) {
                throw new Exception("Failed to update ma_students");
            }
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(Integer uid, Integer studId) {
        try {
            String cond = "";
            List<String> condVal = new ArrayList<>();
            List<String> condType = new ArrayList<>();

            if(uid != null) {
                cond += "user_id = ?";
                condVal.add(Integer.toString(uid));
                condType.add("int");
            }

            if(studId != null) {
                cond += cond.isEmpty() ? "stud_id = ?" : " AND stud_id = ?";
                condVal.add(Integer.toString(studId));
                condType.add("int");
            }

            int result = commDB.delete("ma_students", cond, condVal.toArray(String[]::new), condType.toArray(String[]::new));
            if(result <= 0) {
                throw new Exception("Failed to execute delete query for student");
            }

            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
