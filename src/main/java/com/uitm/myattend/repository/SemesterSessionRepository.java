package com.uitm.myattend.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.uitm.myattend.model.SemesterSessionModel;
import com.uitm.myattend.utility.FieldUtility;

@Repository
public class SemesterSessionRepository {

    private final DBRepository commDB;

    public SemesterSessionRepository(DBRepository commDB) {
        this.commDB = commDB;
    }

    public boolean insert(SemesterSessionModel sessionModel) {
        try {
            String currTms = FieldUtility.timestamp2Oracle(FieldUtility.getCurrentTimestamp());

            String [] field = {
                    "id",
                    "session_name",
                    "session_used",
                    "created_at",
                    "updated_at"
            };

            String [] fieldVal = {
                    sessionModel.getId(),
                    sessionModel.getSessionName(),
                    sessionModel.isUsed() ? "Y" : "N",
                    currTms,
                    currTms
            };

            String [] fieldType = {
                    "varchar",
                    "varchar",
                    "varchar",
                    "timestamp",
                    "timestamp"
            };

            int result = commDB.insert("ma_sessions_semester", field, fieldVal, fieldType);
            if(result <= 0) {
                throw new Exception("Failed to insert into ma_sessions_semester");
            }
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //retrieve role detail
    public List<Map<String, String>> retrieve(String sessionId) {
        try {
            String [] field = {
                    "id",
                    "session_name",
                    "session_used"
            };

            String cond = "id = ?";
            String [] condval = {sessionId};
            String [] condtype = {"varchar"};

            List<Map<String, String>> data = commDB.select("ma_sessions_semester", field, cond, condval, condtype);

            if(data == null) {
                throw new Exception("Failed to retrieve semester sessions");
            }

            return data;
        }catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    //retrieve role detail
    public List<Map<String, String>> retrieveByStatus(String sessionId, String status) {
        try {
            String [] field = {
                    "id",
                    "session_name",
                    "session_used"
            };

            String cond = "id = ? and session_used = ?";
            String [] condval = {sessionId, status};
            String [] condtype = {"varchar", "varchar"};

            List<Map<String, String>> data = commDB.select("ma_sessions_semester", field, cond, condval, condtype);

            if(data == null) {
                throw new Exception("Failed to retrieve semester sessions");
            }

            return data;
        }catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    //retrieve role detail
    public List<Map<String, String>> retrieveActive() {
        try {
            String [] field = {
                    "id",
                    "session_name",
                    "session_used"
            };

            String cond = "session_used = ?";
            String [] condval = {"Y"};
            String [] condtype = {"varchar"};

            List<Map<String, String>> data = commDB.select("ma_sessions_semester", field, cond, condval, condtype);

            if(data == null) {
                throw new Exception("Failed to retrieve active semester sessions");
            }

            return data;
        }catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    //retrieve all role
    public List<Map<String, String>> retrieveAll() {
        try {
            String [] field = {
                    "id",
                    "session_name",
                    "session_used"
            };

            List<Map<String, String>> data = commDB.select("ma_sessions_semester", field);

            if(data == null) {
                throw new Exception("Failed to retrieve semester sessions");
            }

            return data;
        }catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    //update role detail
    public boolean update(SemesterSessionModel sessionModel, String sessionId) {
        try {
            String currTms = FieldUtility.timestamp2Oracle(FieldUtility.getCurrentTimestamp());

            String [] field = {
                    "session_name",
                    "session_used",
                    "updated_at"
            };

            String [] fieldVal = {
                sessionModel.getSessionName(),
                sessionModel.isUsed() ? "Y" : "N",
                currTms
            };

            String [] fieldType = {
                    "varchar",
                    "varchar",
                    "timestamp",
            };

            String cond = "id = ?";
            String [] condval = {sessionId};
            String [] condtype = {"varchar"};


            int result = commDB.update("ma_sessions_semester", field, fieldVal, fieldType, cond, condval, condtype);
            if(result <= 0) {
                throw new Exception("Failed to update session : " + sessionId);
            }
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateStatus(String sessionId, boolean activate) {
        try {
            String currTms = FieldUtility.timestamp2Oracle(FieldUtility.getCurrentTimestamp());

            String [] field = {
                    "session_used",
                    "updated_at"
            };

            String [] fieldVal = {
                activate ? "Y" : "N",
                currTms
            };

            String [] fieldType = {
                    "varchar",
                    "timestamp",
            };

            String cond = "id = ?";
            String [] condval = {sessionId};
            String [] condtype = {"varchar"};


            int result = commDB.update("ma_sessions_semester", field, fieldVal, fieldType, cond, condval, condtype);
            if(result <= 0) {
                throw new Exception("Failed to update session : " + sessionId);
            }
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean disableAll() {
        try {
            String currTms = FieldUtility.timestamp2Oracle(FieldUtility.getCurrentTimestamp());

            String [] field = {
                    "session_used",
                    "updated_at"
            };

            String [] fieldVal = {
                "N",
                currTms
            };

            String [] fieldType = {
                    "varchar",
                    "timestamp",
            };

            String cond = "session_used = ?";
            String [] condval = {"Y"};
            String [] condtype = {"varchar"};


            int result = commDB.update("ma_sessions_semester", field, fieldVal, fieldType, cond, condval, condtype);
            if(result < 0) {
                throw new Exception("Failed to disable all sessions");
            }
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //delete role
    public boolean delete(String sessionId) {
        try {
            String cond = "id = ?";
            String [] condval = {sessionId};
            String [] condtype = {"varchar"};


            int result = commDB.delete("ma_sessions_semester", cond, condval, condtype);
            if(result <= 0) {
                throw new Exception("Failed to delete session : " + sessionId);
            }
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
