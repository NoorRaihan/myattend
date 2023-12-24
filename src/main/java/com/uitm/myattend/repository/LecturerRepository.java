package com.uitm.myattend.repository;

import com.uitm.myattend.model.LecturerModel;
import com.uitm.myattend.utility.FieldUtility;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class LecturerRepository {

    private final DBRepository commDB;

    public LecturerRepository(DBRepository commDB) {
        this.commDB = commDB;
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
                    "varchar",
                    "varchar",
                    "decimal",
                    "varchar",
                    "varchar"
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
}
