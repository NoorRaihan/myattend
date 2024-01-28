package com.uitm.myattend.service;

import com.uitm.myattend.mapper.MapperUtility;
import com.uitm.myattend.model.ClassModel;
import com.uitm.myattend.repository.ClassRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ClassService {

    private final ClassRepository classRepository;

    public ClassService(ClassRepository classRepository) {
        this.classRepository = classRepository;
    }

    public List<ClassModel> retrieveByCourse(Map<String, Object> body) {
        try {

            String cid = (String) body.get("id");
            List<Map<String, String>> classList = classRepository.retrieveByCourse(cid);

            List<ClassModel> classModelList = new ArrayList<>();
            for(Map<String, String> classMap : classList) {
                classModelList.add((ClassModel) MapperUtility.mapModel(ClassModel.class, classMap));
            }
            return classModelList;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
