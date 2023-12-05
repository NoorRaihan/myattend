package com.uitm.myattend.service;

import com.uitm.myattend.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class TestService {

    @Autowired
    private final TestRepository test;

    public TestService(TestRepository test) {
        this.test = test;
    }

    public ArrayList getAll() {
        return test.getData();
    }

    public Map insertData() {
        return test.insertData();
    }

    public Map updateData() { return test.updateData(); }

    public Map deleteData() { return test.deleteData(); }
}
