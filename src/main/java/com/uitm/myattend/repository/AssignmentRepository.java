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

}
