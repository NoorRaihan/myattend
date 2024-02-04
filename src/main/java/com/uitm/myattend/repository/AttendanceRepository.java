package com.uitm.myattend.repository;

import com.uitm.myattend.model.AttendanceModel;
import com.uitm.myattend.utility.FieldUtility;
import org.springframework.stereotype.Repository;

@Repository
public class AttendanceRepository {

    private final DBRepository commDB;

    public AttendanceRepository(DBRepository commDB) {
        this.commDB = commDB;
    }

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
}
