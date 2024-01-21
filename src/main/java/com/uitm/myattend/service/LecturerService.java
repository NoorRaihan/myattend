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

    public boolean insert(Map<String, Object> body) {
        try {
            LecturerModel lecturer = new LecturerModel();
            String lectId = "11" + FieldUtility.generateUUID().substring(0,8);

            lecturer.setUser_id(Integer.parseInt((String)body.get("uid")));
            lecturer.setLect_id(Integer.parseInt(lectId));
            lecturer.setSupervisor_id(body.get("sv").toString().isEmpty() || body.get("sv") == null ?  -1 : Integer.parseInt((String)body.get("sv")));
            lecturer.setStart_date(FieldUtility.getFormatted((String) body.get("startDate"), "yyyy-MM-dd", "yyyyMMdd"));
            lecturer.setQualification((String) body.get("qualify"));
            lecturer.setSalary(Double.parseDouble((String)body.get("salary")));

            if(!lecturerRepository.insert(lecturer)) {
                throw new Exception("Failed to register lecturer information");
            }
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(Map<String, Object> body) {
        try {
            LecturerModel lecturer = new LecturerModel();

            lecturer.setUser_id(Integer.parseInt((String)body.get("uid")));
            lecturer.setLect_id(Integer.parseInt((String)body.get("lect_id")));
            lecturer.setSupervisor_id(body.get("sv") == null || body.get("sv").toString().isEmpty() ?  -1 : Integer.parseInt((String)body.get("sv")));
            lecturer.setStart_date(FieldUtility.getFormatted((String) body.get("startDate"), "yyyy-MM-dd", "yyyyMMdd"));
            lecturer.setQualification((String) body.get("qualify"));
            lecturer.setSalary(Double.parseDouble((String)body.get("salary")));

            if(!lecturerRepository.update(lecturer)) {
                throw new Exception("Failed to update lecturer information");
            }
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean editLecturer(Map<String, Object> body) {
        try {
            int uid = Integer.parseInt((String) body.get("uid"));
            List<Map<String, String>> lecturerList = lecturerRepository.retrieveRaw(uid, null);

            if(lecturerList == null) {
                throw new Exception("Retrieve data error on lecturer!");
            }

            if(lecturerList.size() > 1 ) {
                throw new Exception("Data error multiple rows on lecturer!");
            }

            boolean flag = false;
            if(lecturerList.isEmpty()) {
                flag = insert(body);
            }else {
                flag = update(body);
            }

            if(!flag) {
                throw new Exception("Failed to process lecturer data");
            }

            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(Map<String, Object> body) {
        try {
            int uid = Integer.parseInt((String) body.get("uid"));

            if(lecturerRepository.delete(uid, null)) {
                throw new Exception("Failed to delete lecturer info");
            }

            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
