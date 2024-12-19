package com.uitm.myattend.repository;

import com.uitm.myattend.model.AttendanceModel;
import com.uitm.myattend.utility.FieldUtility;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public class AttendanceRepository {

    private final DBRepository commDB;

    public AttendanceRepository(DBRepository commDB) {
        this.commDB = commDB;
    }

    //insert new attendance
    public boolean insert(AttendanceModel attendanceModel, String sessionId) {
        try {
            String currTms = FieldUtility.timestamp2Oracle(FieldUtility.getCurrentTimestamp());

            String []  field = {
                    "id",
                    "class_id",
                    "stud_id",
                    "status",
                    "attend_date",
                    "attend_time",
                    "session_id",
                    "created_at",
                    "updated_at"
            };

            String [] fieldVal = {
                    attendanceModel.getId(),
                    attendanceModel.getClass_id(),
                    Integer.toString(attendanceModel.getStudent().getUser_id()),
                    attendanceModel.getStatus(),
                    attendanceModel.getAttend_date(),
                    attendanceModel.getAttend_time(),
                    sessionId,
                    currTms,
                    currTms
            };

            String [] fieldType = {
                    "varchar",
                    "varchar",
                    "int",
                    "varchar",
                    "date",
                    "timestamp",
                    "varchar",
                    "timestamp",
                    "timestamp"
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
    public boolean update(AttendanceModel attendanceModel, String sessionId, boolean isUpSert) {
        try {
            String currTms = FieldUtility.timestamp2Oracle(FieldUtility.getCurrentTimestamp());

            String [] field = {
                    "status",
                    "attend_date",
                    "attend_time",
                    "updated_at"
            };

            String []  fieldVal = {
                    attendanceModel.getStatus(),
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

            String cond = "class_id = ? AND stud_id = ? AND session_id = ?";
            String [] condVal = {
                    attendanceModel.getClass_id(),
                    Integer.toString(attendanceModel.getStudent().getUser_id()),
                    sessionId
            };
            String [] condType = {
                    "varchar",
                    "int",
                    "varchar"
            };


            int result = commDB.update("ma_attendances", field, fieldVal, fieldType, cond, condVal, condType);
            if(result < 0) {
                throw new Exception("Failed to update ma_attendances : " + attendanceModel.getStudent().getUser_id());
            }else if(result == 0 && isUpSert) {
                attendanceModel.setId(UUID.randomUUID().toString()); //assume its a new attendance -> assign new attendance id to it
                if(!insert(attendanceModel, sessionId)) {
                    throw new Exception("Failed to update insert into ma_attendances");
                }
            }
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //to retrieve the attendance will all data by joining few tables together
    public List<Map<String, String>> retrieveAttendance(String classId, String sessionId) {
        try {
            String sql = "SELECT a.id, a.class_id, a.attend_date, a.attend_time, a.status, b.*, c.*, c.id AS \"UID\" FROM ma_attendances a " +
                    "INNER JOIN ma_students b ON a.stud_id = b.user_id " +
                    "INNER JOIN ma_users c ON b.user_id = c.id " +
                    "WHERE a.class_id = ? AND a.session_id = ?";

            String [] condVal = {classId, sessionId};
            String [] condType = {"varchar", "varchar"};

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
    public List<Map<String, String>> retrievePerformance(String courseId, String sessionId) {
        try {
            String sql = "SELECT " +
                    "ROUND(((SELECT COUNT(a.id) FROM ma_attendances a INNER JOIN ma_classes b ON a.class_id = b.id AND a.session_id = b.session_id WHERE b.course_id = ? AND status = 'C' AND a.session_id = ?) " +
                    "/ ((SELECT count(stud_id) FROM ma_courses_students WHERE course_id = ? AND session_id = ?) * (SELECT COUNT(id) FROM ma_classes WHERE course_id = ? AND session_id = ?))) " +
                    "* 100, 2) AS \"PERCENTAGE\" FROM DUAL";

            String [] condVal = {courseId,sessionId,courseId,sessionId,courseId,sessionId};
            String [] condType = {"varchar", "varchar", "varchar", "varchar", "varchar", "varchar"};

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
    public List<Map<String, String>> retrievePerformanceByStudent(String courseId, int uid, String sessionId) {
        try {
            String sql = "SELECT " +
                    "ROUND(((SELECT COUNT(a.id) FROM ma_attendances a INNER JOIN ma_classes b ON a.class_id = b.id AND a.session_id = b.session_id WHERE b.course_id = ? AND status = 'C' AND a.stud_id = ? AND a.session_id = ?) " +
                    "/ (SELECT COUNT(id) FROM ma_classes WHERE course_id = ? AND session_id = ?)) * 100, 2) AS \"PERCENTAGE\" FROM DUAL";

            String [] condVal = {courseId, Integer.toString(uid), sessionId, courseId, sessionId};
            String [] condType = {"varchar", "int", "varchar", "varchar", "varchar"};

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
