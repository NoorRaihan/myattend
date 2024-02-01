package com.uitm.myattend.repository;

import com.uitm.myattend.model.AttendanceModel;
import org.springframework.stereotype.Repository;

@Repository
public class AttendanceRepository {

    private final DBRepository commDB;

    public AttendanceRepository(DBRepository commDB) {
        this.commDB = commDB;
    }

    public boolean insert(AttendanceModel attendanceModel) {
        try {
            String []  field = {
                    "id",
                    "class_id",
                    "stud_id",
                    "class_date",
                    "start_time",
                    "end_time",
                    "venue"
            };

            String [] fieldVal = {
                    attendanceModel.
            };
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
