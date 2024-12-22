package com.uitm.myattend.service;
import com.uitm.myattend.mapper.MapperUtility;
import com.uitm.myattend.model.AssignmentModel;
import com.uitm.myattend.repository.AssignmentRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class AssignmentService {
    private final AssignmentRepository assignmentRepository;

    public AssignmentService(AssignmentRepository assignmentRepository) {
        this.assignmentRepository = assignmentRepository;
    }

    //retrieve all assignment
    public List<AssignmentModel> retrieveAll() {
        try {
            List<Map<String, String>> assingnmentList = assignmentRepository.retrieve();

            List<AssignmentModel> assingnmentModelList = new ArrayList<>();
            for(Map<String, String> assignment : assingnmentList) {
                assingnmentModelList.add((AssignmentModel) MapperUtility.mapModel(AssignmentModel.class, assignment));
            }
            return assingnmentModelList;
        }catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    //retrieve assignment by mark_by which lect



    //retrieve assignment by session_id



    //retrieve assignment by assignment_id



    //retrieve assignment by course_id



    //create assignment



    //update assignment - including all cases ( disable,bypass,etc .. )



    //delete assignment

    
}