package com.uitm.myattend.repository;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

import com.uitm.myattend.model.AssignmentModel;
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

            System.out.println(id);

            return commDB.select("ma_submissions", field, cond, condval, condtype);

        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Map<String, String>> retrieveSubmissionDetail(int id) {
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

            String cond = "submission_id = ?";

            String [] condval = {
                Integer.toString(id)
            };

            String [] condtype = {
                "int"
            };

            System.out.println(id);

            return commDB.select("ma_submissions", field, cond, condval, condtype);

        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean insert(SubmissionModel submissionModel) {
        try {
            String [] field = {
                "submission_id",
                "student_id",
                "assignment_id",
                "status",
                "submission_text",
                "ori_filename",
                "server_filename",
                "file_path",
                "created_at",
                "updated_at"
            };

            String [] fieldval = {
                    Integer.toString(submissionModel.getSubmission_id()),
                    Integer.toString(submissionModel.getStudent_id()),
                    Integer.toString(submissionModel.getAssignment_id()),
                    submissionModel.getStatus(),
                    submissionModel.getSubmission_text(),
                    submissionModel.getOri_filename(),
                    submissionModel.getServer_filename(),
                    submissionModel.getFile_path(),
                    FieldUtility.timestamp2Oracle(FieldUtility.getCurrentTimestamp()),
                    FieldUtility.timestamp2Oracle(FieldUtility.getCurrentTimestamp()),
            };

            String [] fieldtype = {
                    "int",
                    "int",
                    "int",
                    "varchar",
                    "varchar",
                    "varchar",
                    "varchar",
                    "varchar",
                    "timestamp",
                    "timestamp",
            };

            int result = commDB.insert("ma_submissions", field, fieldval, fieldtype);
            if(result <= 0) {
                throw new Exception("Failed to insert into ma_submissions");
            }
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(String submissionId, String submissionFilename) {
        try {
            System.out.println("submission delete 3.2.1");
            String cond = "submission_id = ?";
            String [] val = {submissionId};
            String [] type = {"int"};

            int result = commDB.delete("ma_submissions", cond, val, type);

            if(result < 1) {
                System.out.println("submission delete 3.2.2");
                throw new Exception("Data does not existed to be deleted");
            }

            System.out.println("submission delete 3.2.3");
            return true;
        }catch (Exception e) {
            System.out.println("submission delete 3.2.4");
            e.printStackTrace();
            return false;
        }
    }
}
