package com.uitm.myattend.service;

import com.uitm.myattend.mapper.MapperUtility;
import com.uitm.myattend.model.LecturerModel;
import com.uitm.myattend.model.UserModel;
import com.uitm.myattend.repository.LecturerRepository;
import com.uitm.myattend.utility.FieldUtility;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.List;

@Service
public class LecturerService {

    private final LecturerRepository lecturerRepository;

    public LecturerService(LecturerRepository lecturerRepository) {
        this.lecturerRepository = lecturerRepository;
    }

    public List<LecturerModel> retrieveAll() {
        try {
            List<Map<String, String>> lectList = lecturerRepository.retrieve();

            List<LecturerModel> lectModelList = new ArrayList<>();
            for(Map<String, String> lect : lectList) {
                lectModelList.add((LecturerModel) MapperUtility.mapModel(LecturerModel.class, lect));
            }
            return lectModelList;
        }catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public LecturerModel retrieveDetail(Map<String, Object> body) {
        try {
            int uid = Integer.parseInt((String) body.get("uid"));
            List<Map<String, String>> lectList = lecturerRepository.retrieveDetail(uid);

            if(lectList.size() != 1) {
                throw new Exception("Lecturer data retrieve error occured! LecturerList size: " + lectList.size());
            }

            Map<String, String> lect = lectList.get(0);
            LecturerModel lectObj = (LecturerModel) MapperUtility.mapModel(LecturerModel.class, lect);

            return lectObj;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean insert(UserModel user, Map<String, Object> body) {
        try {
            LecturerModel lecturer = new LecturerModel();
            String lectId = FieldUtility.generateUUID().substring(0,6) + FieldUtility.getCurrentDate().substring(0,4);

            lecturer.setUser_id(user.getId());
            lecturer.setLect_id(Integer.parseInt(lectId));
            lecturer.setSupervisor_id((int) body.get("supervisor"));
            lecturer.setStart_date(FieldUtility.getFormatted((String) body.get("start-date"), "yyyy-MM-dd", "yyyyMMdd"));
            lecturer.setQualification((String) body.get("qualification"));
            lecturer.setSalary((double) body.get("salary"));

            if(!lecturerRepository.insert(lecturer)) {
                throw new Exception("Failed to register lecturer information");
            }
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
