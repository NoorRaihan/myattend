package com.uitm.myattend.repository;

import com.uitm.myattend.model.AttendanceModel;
import com.uitm.myattend.utility.FieldUtility;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
public class AttendanceRepository {

    private final DBRepository commDB;

    public AttendanceRepository(DBRepository commDB) {
        this.commDB = commDB;
    }

    //insert new attendance
    public boolean insert(AttendanceModel attendanceModel) {
        try {
            String currTms = FieldUtility.timestamp2Oracle(FieldUtility.getCurrentTimestamp());

            String []  field = {
                    "id",
                    "class_id",
                    "stud_id",
                    "status",
                    "attend_date",
                    "attend_time",
                    "created_at",
                    "updated_at"
            };

            String [] fieldVal = {
                    attendanceModel.getId(),
                    attendanceModel.getClass_id(),
                    Integer.toString(attendanceModel.getStud_id()),
                    attendanceModel.getAttend_date(),
                    attendanceModel.getAttend_time(),
                    currTms,
                    currTms
            };

            String [] fieldType = {
                    "varchar",
                    "varchar",
                    "int",
                    "varchar",
                    "varchar",
                    "varchar",
                    "varchar",
                    "varchar"
            };

            int result = commDB.insert("ma_attendances", field, fieldVal, fieldType);
            if(result <= 0) {
                throw new Exception("Failed to insert into ma_attendances");
            }
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //update the attendance mainly for status update
    public boolean update(String classId, int uid, String status) {
        try {
            String currTms = FieldUtility.timestamp2Oracle(FieldUtility.getCurrentTimestamp());

            String [] field = {
                    "status",
                    "attend_date",
                    "attend_time",
                    "updated_at"
            };

            String []  fieldVal = {
                    status,
                    FieldUtility.date2Oracle(FieldUtility.getCurrentDate()),
                    currTms,
                    currTms
            };

            String [] fieldType = {
                    "varchar",
                    "date",
                    "timestamp",
                    "timestamp"
            };

            String cond = "class_id = ? AND stud_id = ?";
            String [] condVal = {
                    classId,
                    Integer.toString(uid)
            };
            String [] condType = {
                    "varchar",
                    "int"
            };


            int result = commDB.update("ma_attendances", field, fieldVal, fieldType, cond, condVal, condType);
            if(result <= 0) {
                throw new Exception("Failed to update ma_attendances : " + uid);
            }
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //to retrieve the attendance will all data by joining few tables together
    public List<Map<String, String>> retrieveAttendance(String classId) {
        try {
            String sql = "SELECT a.id, a.class_id, a.attend_date, a.attend_time, a.status, b.*, c.*, c.id AS \"UID\" FROM ma_attendances a " +
                    "INNER JOIN ma_students b ON a.stud_id = b.user_id " +
                    "INNER JOIN ma_users c ON b.user_id = c.id " +
                    "WHERE a.class_id = ?";

            String [] condVal = {classId};
            String [] condType = {"varchar"};

            int result = commDB.sqlQuery(sql, condVal, condType);
            if(result <= 0) {
                throw new Exception("Failed to retrieve attendances on class : " + classId);
            }
            return commDB.getResult();
        }catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    //sql to calculate the percentage of attendance performance for each course
    public List<Map<String, String>> retrievePerformance(String courseId) {
        try {
            String sql = "SELECT " +
                    "ROUND(((SELECT COUNT(a.id) FROM ma_attendances a INNER JOIN ma_classes b ON a.class_id = b.id WHERE b.course_id = ? AND status = 'C') " +
                    "/ ((SELECT count(stud_id) FROM ma_courses_students WHERE course_id = ?) * (SELECT COUNT(id) FROM ma_classes WHERE course_id = ?))) " +
                    "* 100, 2) AS \"PERCENTAGE\" FROM DUAL";

            String [] condVal = {courseId,courseId,courseId};
            String [] condType = {"varchar", "varchar", "varchar"};

            int result = commDB.sqlQuery(sql, condVal, condType);
            if(result <= 0) {
                throw new Exception("Failed to retrieve attendances performance on course : " + courseId);
            }
            return commDB.getResult();
        }catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    //sql to calculate the percentage of attendance performance for each course specific to the student itself
    public List<Map<String, String>> retrievePerformanceByStudent(String courseId, int uid) {
        try {
            String sql = "SELECT " +
                    "ROUND(((SELECT COUNT(a.id) FROM ma_attendances a INNER JOIN ma_classes b ON a.class_id = b.id WHERE b.course_id = ? AND status = 'C' AND a.stud_id = ?) " +
                    "/ (SELECT COUNT(id) FROM ma_classes WHERE course_id = ?)) * 100, 2) AS \"PERCENTAGE\" FROM DUAL";

            String [] condVal = {courseId, Integer.toString(uid), courseId};
            String [] condType = {"varchar", "int", "varchar"};

            int result = commDB.sqlQuery(sql, condVal, condType);
            if(result <= 0) {
                throw new Exception("Failed to retrieve attendances performance on course : " + courseId);
            }
            return commDB.getResult();
        }catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
