package com.uitm.myattend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import com.uitm.myattend.mapper.MapperUtility;
import com.uitm.myattend.model.CourseModel;
import com.uitm.myattend.model.LecturerModel;
import com.uitm.myattend.model.SemesterSessionModel;
import com.uitm.myattend.repository.SemesterSessionRepository;
import com.uitm.myattend.utility.FieldUtility;

@Service
public class SemesterSessionService {

    private final SemesterSessionRepository semesterSessionRepository;

    public SemesterSessionService(SemesterSessionRepository semesterSessionRepository) {
        this.semesterSessionRepository = semesterSessionRepository;
    } 

    public List<SemesterSessionModel> retrieveAll() {
        try {
            List<Map<String, String>> sessionList = semesterSessionRepository.retrieveAll();
        
            List<SemesterSessionModel> sessionModelList = new ArrayList<>();
            for(Map<String, String> session : sessionList) {
                SemesterSessionModel sessionModel =  (SemesterSessionModel) MapperUtility.mapModel(SemesterSessionModel.class, session);
                sessionModelList.add(sessionModel);
            }
            return sessionModelList;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean insert(Map<String, Object> body) {
        try {
            SemesterSessionModel sessionModel = new SemesterSessionModel();

            sessionModel.setId(UUID.randomUUID().toString());
            sessionModel.setSessionName(FieldUtility.checkNull(body.get("session_name")));
            sessionModel.setUsed(FieldUtility.checkNull(body.get("session_used")).equals("Y"));

            if(!semesterSessionRepository.insert(sessionModel)) {
                throw new Exception("Failed to insert new session");
            }
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public SemesterSessionModel retrieveDetail(Map<String, Object> body) {
        try {
            List<Map<String, String>> sessionList = semesterSessionRepository.retrieve(FieldUtility.checkNull(body.get("session_id")));
            if(sessionList.size() != 1) {
                throw new Exception("Course data retrieve error occured! CourseList size: " + sessionList.size());
            }

            Map<String, String> sessionObj = sessionList.get(0);
            SemesterSessionModel sessionModel =  (SemesterSessionModel) MapperUtility.mapModel(SemesterSessionModel.class, sessionObj);
            return sessionModel;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean update(Map<String, Object> body) {
        try {
            SemesterSessionModel sessionModel = new SemesterSessionModel();

            sessionModel.setId(FieldUtility.checkNull(body.get("session_id")));
            sessionModel.setSessionName(FieldUtility.checkNull(body.get("session_name")));
            sessionModel.setUsed(FieldUtility.checkNull(body.get("session_used")).equals("Y"));

            if(!semesterSessionRepository.update(sessionModel, sessionModel.getId())) {
                throw new Exception("Failed to update new session");
            }
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean activateDisable(Map<String, Object> body, boolean activate) {
        try {
            SemesterSessionModel sessionModel = new SemesterSessionModel();
            sessionModel.setId(FieldUtility.checkNull(body.get("session_id")));

            if(activate) {
                if(!semesterSessionRepository.disableAll()) throw new Exception("Failed to disable all sessions");
            }

            if(!semesterSessionRepository.updateStatus(sessionModel.getId(), activate)) {
                throw new Exception("Failed to activate session");
            }
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean delete(Map<String, Object> body) {
        try {
            if(!semesterSessionRepository.delete(FieldUtility.checkNull(body.get("session_id")))) {
                throw new Exception("Failed to delete session");
            }
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public SemesterSessionModel getActiveSemester() {
        try {
            List<Map<String, String>> activeSemester = semesterSessionRepository.retrieveActive();
            if(activeSemester == null || activeSemester.isEmpty()) {
                throw new Exception("Failed to retrieve active semester session");
            }

            return (SemesterSessionModel) MapperUtility.mapModel(SemesterSessionModel.class, activeSemester.get(0));
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
