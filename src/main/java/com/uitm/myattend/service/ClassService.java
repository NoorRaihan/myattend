package com.uitm.myattend.service;

import com.uitm.myattend.repository.ClassRepository;
import org.springframework.stereotype.Service;

@Service
public class ClassService {

    private final ClassRepository classRepository;

    public ClassService(ClassRepository classRepository) {
        this.classRepository = classRepository;
    }

}
