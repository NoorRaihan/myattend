package com.uitm.myattend.repository;

import com.uitm.myattend.model.CourseModel;
import com.uitm.myattend.utility.FieldUtility;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class CourseRepository {

    private final DBRepository commDB;

    public CourseRepository(DBRepository commDB) {
        this.commDB = commDB;
    }

    public List<Map<String, String>> retrieve() {
        try {
            String sql = "SELECT b.*, a.id AS COURSE_ID, a.*  FROM ma_courses a " +
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

    public List<Map<String, String>> retrieveDetail(String id) {
        try {
            String sql = "SELECT b.*, a.id AS COURSE_ID, a.*  FROM ma_courses a " +
                    "INNER JOIN ma_users b ON a.user_id = b.id WHERE a.id = ?";

            String [] condval = {
                    id
            };

            String [] condtype = {
                    "varchar"
            };

            int result = commDB.sqlQuery(sql, condval, condtype);
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

    public boolean update(CourseModel courseModel) {
        try {
            String [] field = {
                    "course_code",
                    "user_id",
                    "course_name",
                    "credit_hour",
                    "color",
                    "updated_at",
            };

            String [] fieldval = {
                    courseModel.getCourse_code(),
                    Integer.toString(courseModel.getUser_id()),
                    courseModel.getCourse_name(),
                    Double.toString(courseModel.getCredit_hour()),
                    courseModel.getColor(),
                    FieldUtility.timestamp2Oracle(FieldUtility.getCurrentTimestamp())
            };

            String [] fieldtype = {
                    "varchar",
                    "int",
                    "varchar",
                    "decimal",
                    "varchar",
                    "timestamp",
            };

            if(!FieldUtility.checkNull(courseModel.getDeleted()).isEmpty()) {
                List<String> tempField = new ArrayList<>(Arrays.asList(field));
                List<String> tempVal = new ArrayList<>(Arrays.asList(fieldval));
                List<String> tempType = new ArrayList<>(Arrays.asList(fieldtype));
                tempField.add("deleted_at");
                tempVal.add(FieldUtility.timestamp2Oracle(courseModel.getDeleted()));
                tempType.add("timestamp");

                field = tempField.toArray(String[]::new);
                fieldval = tempVal.toArray(String[]::new);
                fieldtype = tempType.toArray(String[]::new);
            }

            String cond = "id = ?";

            String [] condval = {
                    courseModel.getId()
            };

            String [] condtype = {
                    "varchar"
            };

            int result = commDB.update("ma_courses", field, fieldval, fieldtype, cond, condval, condtype);
            if(result <= 0) {
                throw new Exception("Failed to update into ma_courses");
            }
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(String id) {
        try {
            String cond = "id = ?";
            String [] val = {id};
            String [] type = {"int"};

            int result = commDB.delete("ma_courses", cond, val, type);

            if(result < 1) {
                throw new Exception("Data does not existed to be deleted");
            }

            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
