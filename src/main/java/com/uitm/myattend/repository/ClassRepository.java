package com.uitm.myattend.repository;

import com.uitm.myattend.model.ClassModel;
import com.uitm.myattend.utility.FieldUtility;
import org.springframework.stereotype.Repository;

import java.util.Collections;
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

    public List<Map<String, String>> retrieveDetail(String id) {
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

            String cond = "id = ?";

            String [] condval = {
                    id
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

    public boolean insert(ClassModel classObj) {
        try {
            String currTms = FieldUtility.timestamp2Oracle(FieldUtility.getCurrentTimestamp());

            String [] field = {
                    "id",
                    "course_id",
                    "class_desc",
                    "class_date",
                    "start_time",
                    "end_time",
                    "venue",
                    "created_at",
                    "updated_at"
            };

            String [] fieldVal = {
                    classObj.getId(),
                    classObj.getCourse_id(),
                    classObj.getClass_desc(),
                    FieldUtility.date2Oracle(classObj.getClass_date()),
                    FieldUtility.timestamp2Oracle(classObj.getStart_time()),
                    FieldUtility.timestamp2Oracle(classObj.getEnd_time()),
                    classObj.getVenue(),
                    currTms,
                    currTms
            };

            String [] fieldType = {
                    "varchar",
                    "varchar",
                    "varchar",
                    "date",
                    "timestamp",
                    "timestamp",
                    "varchar",
                    "timestamp",
                    "timestamp"
            };

            int result = commDB.insert("ma_classes", field, fieldVal, fieldType);
            if(result <= 0) {
                throw new Exception("Failed to insert a class record");
            }
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(ClassModel classObj) {
        try {
            String currTms = FieldUtility.timestamp2Oracle(FieldUtility.getCurrentTimestamp());

            String [] field = {
                    "class_desc",
                    "class_date",
                    "start_time",
                    "end_time",
                    "venue",
                    "updated_at"
            };

            String [] fieldVal = {
                    classObj.getClass_desc(),
                    FieldUtility.date2Oracle(classObj.getClass_date()),
                    FieldUtility.timestamp2Oracle(classObj.getStart_time()),
                    FieldUtility.timestamp2Oracle(classObj.getEnd_time()),
                    classObj.getVenue(),
                    currTms
            };

            String [] fieldType = {
                    "varchar",
                    "date",
                    "timestamp",
                    "timestamp",
                    "varchar",
                    "timestamp"
            };

            String cond = "id = ?";

            String [] condval = {
                    classObj.getId()
            };

            String [] condtype = {
                    "varchar"
            };

            int result = commDB.update("ma_classes", field, fieldVal, fieldType, cond, condval, condtype);
            if(result <= 0) {
                throw new Exception("Failed to update a class record");
            }
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(String id) {
        try {
            String cond = "id = ?";
            String [] condval  = {id};
            String [] condtype  = {"varchar"};

            int result = commDB.delete("ma_classes", cond, condval, condtype);
            if(result <= 0) {
                throw new Exception("Failed to insert a class record");
            }
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Map<String, String>> retrieveActive(String currDate, String currTms) {
        return retrieveActive(currDate, currTms, null, null);
    }

    public List<Map<String, String>> retrieveActiveStudent(String currDate, String currTms, Integer uid) {
        return retrieveActive(currDate, currTms, null, uid);
    }

    public List<Map<String, String>> retrieveActiveLecturer(String currDate, String currTms, Integer uid) {
        return retrieveActive(currDate, currTms, uid, null);
    }

    public List<Map<String, String>> retrieveActive(String currDate, String currTms, Integer lect, Integer stud) {
        try {
            String sql = "SELECT a.*, b.* FROM ma_classes a " +
                    "INNER JOIN ma_courses b ON a.COURSE_ID = b.id " +
                    "WHERE TO_CHAR(a.START_TIME, 'yyyyMMddHH24MISSff3') <= ? AND ? <= TO_CHAR(a.END_TIME, 'yyyyMMddHH24MISSff3') " +
                    "AND TO_CHAR(a.CLASS_DATE, 'yyyyMMdd') = ? AND a.deleted_at IS NULL";

            if(lect != null) {
                sql += " AND b.user_id = " + lect;
            }else if(stud != null) {
                sql += " AND a.COURSE_ID IN (SELECT course_id FROM ma_courses_students WHERE stud_id = "+ stud +")";
            }

            String [] condVal = {
                    currTms,
                    currTms,
                    currDate
            };

            String [] condType = {
                    "varchar",
                    "varchar",
                    "varchar"
            };

            int result = commDB.sqlQuery(sql, condVal, condType);
            if(result <= 0) {
                throw new Exception("Failed to retrieve active class record");
            }
            return commDB.getResult();
        }catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public List<Map<String, String>> retrieveToday(String currDate) {
        return retrieveToday(currDate, null, null);
    }

    public List<Map<String, String>> retrieveTodayStudent(String currDate, int uid) {
        return retrieveToday(currDate, null, uid);
    }

    public List<Map<String, String>> retrieveTodayLecturer(String currDate, int uid) {
        return retrieveToday(currDate, uid, null);
    }

    public List<Map<String, String>> retrieveToday(String currDate, Integer lect, Integer stud) {
        try {
            String sql = "SELECT a.*, b.* FROM ma_classes a " +
                    "INNER JOIN ma_courses b ON a.COURSE_ID = b.id " +
                    "WHERE TO_CHAR(a.CLASS_DATE, 'yyyyMMdd') = ? AND a.deleted_at IS NULL";

            if(lect != null) {
                sql += " AND b.user_id = " + lect;
            }else if(stud != null) {
                sql += " AND a.COURSE_ID IN (SELECT course_id FROM ma_courses_students WHERE stud_id = "+ stud +")";
            }

            String [] condVal = {
                    currDate
            };

            String [] condType = {
                    "varchar"
            };

            int result = commDB.sqlQuery(sql, condVal, condType);
            if(result <= 0) {
                throw new Exception("Failed to retrieve today class record");
            }
            return commDB.getResult();
        }catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public List<Map<String, String>> retrieveAll(String currDate) {
        return retrieveAll(currDate, null, null);
    }

    public List<Map<String, String>> retrieveAllStudent(String currDate, int uid) {
        return retrieveAll(currDate, null, uid);
    }

    public List<Map<String, String>> retrieveAllLecturer(String currDate, int uid) {
        return retrieveAll(currDate, uid, null);
    }

    public List<Map<String, String>> retrieveAll(String currDate, Integer lect, Integer stud) {
        try {
            String sql = "SELECT a.*, b.* FROM ma_classes a " +
                    "INNER JOIN ma_courses b ON a.COURSE_ID = b.id " +
                    "WHERE a.deleted_at IS NULL";

            if(lect != null) {
                sql += " AND b.user_id = " + lect;
            }else if(stud != null) {
                sql += " AND a.COURSE_ID IN (SELECT course_id FROM ma_courses_students WHERE stud_id = "+ stud +")";
            }

            int result = commDB.sqlQuery(sql);
            if(result <= 0) {
                throw new Exception("Failed to retrieve today class record");
            }
            return commDB.getResult();
        }catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public boolean changeStatus(String courseid, boolean isDisable) {
        try {
            String currTms = FieldUtility.timestamp2Oracle(FieldUtility.getCurrentTimestamp());

            String [] field = {
                    "deleted_at",
                    "updated_at"
            };

            String [] fieldVal = {
                    isDisable ? currTms : null,
                    currTms
            };

            String [] fieldType = {
                    "timestamp",
                    "timestamp"
            };

            String cond = "course_id = ?";
            String [] condVal = {courseid};
            String [] condType = {"varchar"};
            int result = commDB.update("ma_classes", field, fieldVal, fieldType, cond, condVal, condType);
            if(result <= 0) {
                throw new Exception("Failed to change status class of " + courseid);
            }
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
