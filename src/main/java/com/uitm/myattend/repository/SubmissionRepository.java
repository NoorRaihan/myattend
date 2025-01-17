package com.uitm.myattend.repository;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

import com.uitm.myattend.model.SubmissionModel;
import com.uitm.myattend.utility.FieldUtility;

@Repository
public class SubmissionRepository {
    
    private final DBRepository commDB;

    public SubmissionRepository(DBRepository commDB) {
        this.commDB = commDB;
    }

    public List<Map<String, String>> retrieve() {
        try {
            String sql = "SELECT * FROM ma_submissions a " +
                    "JOIN ma_assignments b ON a.assignment_id = b.assignment_id " ;

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

    public List<Map<String, String>> retrieveDetail(int id) {
        try {
            String [] field = {
                "submission_id",
                "student_id",
                "assignment_id",
                "status",
                "submission_text",
                "submission_mark",
                "ori_filename",
                "server_filename",
                "file_path",
                "created_at",
                "updated_at",
                "mark_by"
            };

            String cond = "assignment_id = ?";

            String [] condval = {
                    Integer.toString(id)
            };

            String [] condtype = {
                    "int"
            };

            return commDB.select("ma_submissions", field, cond, condval, condtype);

        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
