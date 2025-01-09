package com.uitm.myattend.repository;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class AssignmentRepository {
    
    private final DBRepository commDB;

    public AssignmentRepository(DBRepository commDB) {
        this.commDB = commDB;
    }

    public List<Map<String, String>> retrieve() {
        try {
            String sql = "SELECT ses.*, asg.* FROM ma_assignments asg " +
                    "JOIN ma_sessions_semester ses ON asg.session_id = ses.id " ;

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

    public List<Map<String, String>> retrieveByCourse(String cid, String sessionId) {
        try {
            String sql = "SELECT a.assignment_id, a.session_id, a.course_id, a.assignment_header, a.assignment_desc, a.disabled_flag, " +
             "a.bypass_time_flag, a.ori_filename, a.server_filename, a.file_path, a.started_at, " +
             "a.ended_at, a.created_at, a.updated_at, a.deleted_at, " +
             "b.* " + 
             "FROM ma_assignments a " +
             "INNER JOIN ma_courses b ON a.course_id = b.id " +
             "WHERE a.course_id = ? AND a.session_id = ?";

            String [] condVal = {
                    cid,
                    sessionId
            };

            String [] condType = {
                    "varchar",
                    "varchar"
            };

            int result = commDB.sqlQuery(sql, condVal, condType);
            if(result <= 0) {
                throw new Exception("Failed to retrieve attendances on course : " + cid);
            }
            return commDB.getResult();

        }catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public List<Map<String, String>> retrieveBySession(String sid) {
        try {
            String [] field = {
                    "assignment_id",
                    "session_id",
                    "course_id",
                    "assignment_header",
                    "assignment_desc",
                    "disabled_flag",
                    "bypass_time_flag",
                    "ori_filename",
                    "server_filename",
                    "file_path",
                    "started_at",
                    "ended_at",
                    "created_at",
                    "updated_at",
                    "deleted_at"
            };

            String cond = "session_id = ?";

            String [] condval = {
                    sid
            };

            String [] condtype = {
                    "varchar"
            };

            return commDB.select("ma_assignments", field, cond, condval, condtype);

        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Map<String, String>> retrieveDetail(String id) {
        try {
            String [] field = {
                    "assignment_id",
                    "session_id",
                    "course_id",
                    "assignment_header",
                    "assignment_desc",
                    "disabled_flag",
                    "bypass_time_flag",
                    "ori_filename",
                    "server_filename",
                    "file_path",
                    "started_at",
                    "ended_at",
                    "created_at",
                    "updated_at",
                    "deleted_at"
            };

            String cond = "assignment_id = ?";

            String [] condval = {
                    id
            };

            String [] condtype = {
                    "varchar"
            };

            return commDB.select("ma_assignments", field, cond, condval, condtype);

        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean delete(String id) {
        try {
            String cond = "assignment_id = ?";
            String [] val = {id};
            String [] type = {"varchar"};

            int result = commDB.delete("ma_assignments", cond, val, type);

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
