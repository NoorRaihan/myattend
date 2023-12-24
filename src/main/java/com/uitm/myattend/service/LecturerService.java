package com.uitm.myattend.service;

import com.uitm.myattend.model.LecturerModel;
import com.uitm.myattend.model.UserModel;
import com.uitm.myattend.repository.LecturerRepository;
import com.uitm.myattend.utility.FieldUtility;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class LecturerService {

    private final LecturerRepository lecturerRepository;

    public LecturerService(LecturerRepository lecturerRepository) {
        this.lecturerRepository = lecturerRepository;
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
                throw new Exception("Failed to register lecturer");
            }
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
