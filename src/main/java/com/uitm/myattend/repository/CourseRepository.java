package com.uitm.myattend.repository;

import com.uitm.myattend.model.CourseModel;
import com.uitm.myattend.utility.FieldUtility;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
public class CourseRepository {

    private final DBRepository commDB;

    public CourseRepository(DBRepository commDB) {
        this.commDB = commDB;
    }

    public List<Map<String, String>> retrieve() {
        try {
            String sql = "SELECT b.*, a.*  FROM ma_courses a " +
                    "LEFT JOIN ma_users b ON a.user_id = b.id";

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

    public boolean insert(CourseModel courseModel) {
        try {
            String [] field = {
                    "id",
                    "course_code",
                    "user_id",
                    "course_name",
                    "credit_hour",
                    "color",
                    "created_at",
                    "updated_at"
            };

            String [] fieldval = {
                    courseModel.getId(),
                    courseModel.getCourse_code(),
                    Integer.toString(courseModel.getUser_id()),
                    courseModel.getCourse_name(),
                    Double.toString(courseModel.getCredit_hour()),
                    courseModel.getColor(),
                    FieldUtility.timestamp2Oracle(FieldUtility.getCurrentTimestamp()),
                    FieldUtility.timestamp2Oracle(FieldUtility.getCurrentTimestamp())
            };

            String [] fieldtype = {
                    "varchar",
                    "varchar",
                    "int",
                    "varchar",
                    "decimal",
                    "varchar",
                    "timestamp",
                    "timestamp"
            };

            int result = commDB.insert("ma_courses", field, fieldval, fieldtype);
            if(result <= 0) {
                throw new Exception("Failed to insert into ma_courses");
            }
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
