package com.uitm.myattend.repository;

import com.uitm.myattend.model.StudentModel;
import com.uitm.myattend.utility.FieldUtility;
import org.springframework.stereotype.Repository;

@Repository
public class StudentRepository {

    private final DBRepository commDB;

    public StudentRepository(DBRepository commDB) {
        this.commDB = commDB;
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
                    "varchar",
                    "varchar"
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

    public boolean delete(int uid, int studId) {
        try {
            String cond = "user_id = ? AND stud_id = ?";
            String [] val = {
                    Integer.toString(uid),
                    Integer.toString(studId)
            };

            String [] type = {
                    "int",
                    "int"
            };

            commDB.delete("ma_students", cond, val, type);
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
