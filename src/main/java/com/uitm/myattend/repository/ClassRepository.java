package com.uitm.myattend.repository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class ClassRepository {

    private final DBRepository commDB;

    public ClassRepository(DBRepository commDB) {
        this.commDB = commDB;
    }

    public List<Map<String, String>> retrieveByCourse(String cid) {
        try {
            String [] field = {
                    "id",
                    "course_id",
                    "class_desc",
                    "class_date",
                    "start_time",
                    "end_time",
                    "venue",
                    "deleted_at"
            };

            String cond = "course_id = ?";

            String [] condval = {
                    cid
            };

            String [] condtype = {
                    "varchar"
            };

            return commDB.select("ma_classes", field, cond, condval, condtype);

        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
