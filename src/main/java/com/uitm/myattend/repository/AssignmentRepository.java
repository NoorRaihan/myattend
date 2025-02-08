package com.uitm.myattend.repository;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

import com.uitm.myattend.model.AssignmentModel;
import com.uitm.myattend.utility.FieldUtility;

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

    public List<Map<String, String>> retrieveByCourseStudent(String cid, String sessionId, int studentId) {
        try {
            String sql = "SELECT a.assignment_id, a.session_id, a.course_id, a.assignment_header, a.assignment_desc, a.disabled_flag, " +
             "a.bypass_time_flag, a.ori_filename, a.server_filename, a.file_path, a.started_at, " +
             "a.ended_at, a.created_at, a.updated_at, a.deleted_at, " +
             "b.* , c.* " + 
             "FROM ma_assignments a " +
             "INNER JOIN ma_courses b ON a.course_id = b.id " +
             "LEFT JOIN ma_submissions c ON a.assignment_id = c.assignment_id AND c.student_id = ? " +
             "WHERE a.course_id = ? AND a.session_id = ?";

            String [] condVal = {
                    Integer.toString(studentId),
                    cid,
                    sessionId,
            };

            String [] condType = {
                    "int",
                    "varchar",
                    "varchar",
                    
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

    public boolean insert(AssignmentModel assignmentModel) {
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
                "updated_at"
            };

            String [] fieldval = {
                    Integer.toString(assignmentModel.getAssignment_id()),
                    assignmentModel.getSessionId(),
                    assignmentModel.getCourse_id(),
                    assignmentModel.getAssignment_header(),
                    assignmentModel.getAssignment_desc(),
                    Integer.toString(assignmentModel.isDisabled_flag()),
                    Integer.toString(assignmentModel.isBypass_time_flag()),
                    assignmentModel.getOri_filename(),
                    assignmentModel.getServer_filename(),
                    assignmentModel.getFile_path(),
                    assignmentModel.getStarted_at(),
                    assignmentModel.getEnded_at(),
                    FieldUtility.timestamp2Oracle(FieldUtility.getCurrentTimestamp()),
                    FieldUtility.timestamp2Oracle(FieldUtility.getCurrentTimestamp()),
            };

            String [] fieldtype = {
                    "int",
                    "varchar",
                    "varchar",
                    "varchar",
                    "varchar",
                    "int",
                    "int",
                    "varchar",
                    "varchar",
                    "varchar",
                    "timestamp",
                    "timestamp",
                    "timestamp",
                    "timestamp",
            };

            int result = commDB.insert("ma_assignments", field, fieldval, fieldtype);
            if(result <= 0) {
                throw new Exception("Failed to insert into ma_assignments");
            }
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(AssignmentModel assignmentModel) {
        try {
            String [] field = {
                // "assignment_id",
                // "session_id",
                // "course_id",
                "assignment_header",
                "assignment_desc",
                "disabled_flag",
                "bypass_time_flag",
                "ori_filename",
                "server_filename",
                "file_path",
                "started_at",
                "ended_at",
                "updated_at"
            };

            String [] fieldval = {
                // Integer.toString(assignmentModel.getAssignment_id()),
                // assignmentModel.getSessionId(),
                // assignmentModel.getCourse_id(),
                assignmentModel.getAssignment_header(),
                assignmentModel.getAssignment_desc(),
                Integer.toString(assignmentModel.isDisabled_flag()),
                Integer.toString(assignmentModel.isBypass_time_flag()),
                assignmentModel.getOri_filename(),
                assignmentModel.getServer_filename(),
                assignmentModel.getFile_path(),
                assignmentModel.getStarted_at(),
                assignmentModel.getEnded_at(),
                FieldUtility.timestamp2Oracle(FieldUtility.getCurrentTimestamp()),
            };

            String [] fieldtype = {
                    // "int",
                    // "varchar",
                    // "varchar",
                    "varchar",
                    "varchar",
                    "int",
                    "int",
                    "varchar",
                    "varchar",
                    "varchar",
                    "timestamp",
                    "timestamp",
                    "timestamp",
            };

            String cond = "assignment_id = ?";

            String [] condval = {
                Integer.toString(assignmentModel.getAssignment_id())
            };

            String [] condtype = {
                    "varchar"
            };

            int result = commDB.update("ma_assignments", field, fieldval, fieldtype, cond, condval, condtype);
            if(result <= 0) {
                throw new Exception("Failed to insert into ma_assignments");
            }
            return true;
        
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean delete(String assignmentId) {
        try {
            String cond = "assignment_id = ?";
            String [] val = {assignmentId};
            String [] type = {"int"};

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
